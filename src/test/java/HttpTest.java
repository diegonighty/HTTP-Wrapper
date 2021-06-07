package com.github.diegonighty.http.test;

import com.github.diegonighty.http.CloseableConnection;
import com.github.diegonighty.http.HttpConnection.RequestField;
import com.github.diegonighty.http.exception.FailedConnectionException;
import com.github.diegonighty.http.response.HttpResponse;
import com.github.diegonighty.http.serialization.common.DefaultResponseDeserializer;
import com.github.diegonighty.http.util.Connections;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpTest {

  @Test
  @DisplayName("test_new_get")
  void test_new_get() {
    String[] params = { "17074", "stats" };
    String url = String.format("https://api.jikan.moe/v3/anime/%s/%s", params[0], params[1]);

    try (CloseableConnection<Anime> connection = Connections.of(url)) {

      HttpResponse<Anime> response = connection.open()
          .addRequestField(RequestField.USER_AGENT, "Mozilla/5.0")
          .createGetRequest()
          .setResponseDeserializer(new DefaultResponseDeserializer<>(Anime.class))
          .execute();

      Anime episodes = response.result();

      Assertions.assertTrue(episodes.getWatching() > 1000);

    } catch (FailedConnectionException e) {
      e.printStackTrace();
    }

  }

  public static class Anime {

    final int watching;

    public Anime(int watching) {
      this.watching = watching;
    }

    public int getWatching() {
      return watching;
    }
  }

  @Test
  @DisplayName("test_new_post")
  void test_new_post() {
    String url = "https://jsonplaceholder.typicode.com/posts";

    try (CloseableConnection<Post> connection = Connections.of(url)) {

      HttpResponse<Integer> response = connection.open()
          .addRequestField(RequestField.USER_AGENT, "Mozilla/5.0")
          .createPostRequest()
          .setObject(new Post("sisas", "no", 2))
          .setSerializer(object -> new Gson().toJson(object))
          .execute();

      int result = response.result();

      Assertions.assertEquals(201, result);

    } catch (FailedConnectionException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("test_new_delete")
  void test_new_delete() {
    String url = "https://jsonplaceholder.typicode.com/posts/1";

    try (CloseableConnection<Void> connection = Connections.of(url)) {

      HttpResponse<Integer> response = connection.open()
          .addRequestField(RequestField.USER_AGENT, "Mozilla/5.0")
          .createDeleteRequest()
          .execute();

      int result = response.result();

      Assertions.assertEquals(200, result);

    } catch (FailedConnectionException e) {
      e.printStackTrace();
    }
  }

  public static class Post {

    final String title;
    final String body;
    final int userId;

    public Post(String title, String body, int userId) {
      this.title = title;
      this.body = body;
      this.userId = userId;
    }
  }


}
