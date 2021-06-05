import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.diegonighty.http.CloseableConnection;
import com.github.diegonighty.http.HttpCloseableConnection;
import com.github.diegonighty.http.HttpConnection.HttpMethod;
import com.github.diegonighty.http.HttpConnection.RequestField;
import com.github.diegonighty.http.HttpResponse;
import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.serialization.ResponseDeserializer;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HTTPTest {

  @Test
  @DisplayName("Test of get requests in main thread")
  void test_get_request() {

    String user = "DiegoNighty";
    String url = String.format("https://api.mojang.com/users/profiles/minecraft/%s", user);

    try (CloseableConnection<MojangProfile> connection = new HttpCloseableConnection<MojangProfile>(url).open()) {

      connection.setType(MojangProfile.class);
      connection.setRequestMethod(HttpMethod.GET);
      connection.addRequestField(RequestField.USER_AGENT, "Mozilla/5.0");

      try {
        HttpResponse<MojangProfile> response = connection.execute();

        MojangProfile profile = Objects.requireNonNull(response).result();

        Assertions.assertEquals("DiegoNighty", profile.getName());
        Assertions.assertEquals("b2375a2593a048698cb09a67994e55a4", profile.getUuid());

      } catch (FailedConnectionException exception) {
        //code != 200
        exception.printStackTrace();
      }

    }
  }

  @Test
  @DisplayName("Test of get request with custom deserializer")
  void test_custom_deserializer() {
    String serverIp = "mc.hypixel.net";
    String url = String.format("https://api.namemc.com/server/%s/likes", serverIp);

    try (CloseableConnection<NameMCServer> connection = new HttpCloseableConnection<NameMCServer>(url).open()) {

      connection.setResponseDeserializer(new NameMCServerDeserializer());
      connection.setRequestMethod(HttpMethod.GET);
      connection.addRequestField(RequestField.USER_AGENT, "Mozilla/5.0");

      try {
        HttpResponse<NameMCServer> response = connection.execute();

        NameMCServer server = Objects.requireNonNull(response).result();

        Assertions.assertTrue(server.getLikeList().size() > 1000); //is true because hypixel have more than 5,000 likes!
        Assertions.assertTrue(server.isLikedBy(UUID.fromString("3c4ba543-4611-4cbd-b802-1610ff5397b6"))); //is the first person in the list lol

      } catch (FailedConnectionException exception) {
        //code != 200
        exception.printStackTrace();
      }

    }
  }

  public static class NameMCServerDeserializer implements ResponseDeserializer<NameMCServer> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public NameMCServer deserialize(String json) {
      try {
        final List<UUID> list = MAPPER.readValue(json, new TypeReference<List<UUID>>() {});

        return new NameMCServer(list);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return null;
    }

  }

  public static class NameMCServer {

    private final List<UUID> likeList;

    public NameMCServer(List<UUID> likeList) {
      this.likeList = likeList;
    }

    public boolean isLikedBy(UUID id) {
      return likeList.contains(id);
    }

    public List<UUID> getLikeList() {
      return likeList;
    }
  }

  @Test
  @DisplayName("Test of get request in two threads")
  void test_get_async() {

    CompletableFuture<MojangProfile> futureProfile = CompletableFuture.supplyAsync(() -> {

      String user = "DiegoNighty";
      String url = String.format("https://api.mojang.com/users/profiles/minecraft/%s", user);

      try (CloseableConnection<MojangProfile> connection = new HttpCloseableConnection<MojangProfile>(
          url).open()) {

        connection.setType(MojangProfile.class);
        connection.setRequestMethod(HttpMethod.GET);
        connection.addRequestField(RequestField.USER_AGENT, "Mozilla/5.0");

        try {
          HttpResponse<MojangProfile> response = connection.execute();

          return Objects.requireNonNull(response).result();
        } catch (FailedConnectionException exception) {
          //code != 200
          exception.printStackTrace();
        }
      }

      return null;
    });

    futureProfile.whenComplete((profile, throwable) -> {
      if (throwable != null) {
        return;
      }

      Assertions.assertEquals("DiegoNighty", profile.getName());
      Assertions.assertEquals("b2375a2593a048698cb09a67994e55a4", profile.getUuid());
    });
  }

  public static class MojangProfile {

    private final String name;
    private final String id;

    public MojangProfile(String name, String id) {
      this.name = name;
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public String getUuid() {
      return id;
    }
  }
}
