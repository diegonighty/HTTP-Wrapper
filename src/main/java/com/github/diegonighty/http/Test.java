package com.github.diegonighty.http;

import com.github.diegonighty.http.HttpConnection.HttpMethod;
import com.github.diegonighty.http.HttpConnection.RequestField;
import com.github.diegonighty.http.exception.FailedConnectionException;
import java.util.Objects;
import java.util.UUID;

public class Test {

  public static void main(String[] args) {
    try {
      test();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void test() throws Exception {

    try (CloseableConnection<MojangProfile> connection = new HttpCloseableConnection<MojangProfile>("https://api.mojang.com/users/profiles/minecraft/diegonighty").open()) {

      connection.setType(MojangProfile.class);
      connection.setRequestMethod(HttpMethod.GET);
      connection.addRequestField(RequestField.User_Agent, "Mozilla/5.0");

      try {
        HttpResponse<MojangProfile> response = connection.execute();

        MojangProfile profile = Objects.requireNonNull(response).result();

        System.out.println("User search: " + profile.getName() + " with id: " + profile.getUuid());

      } catch (FailedConnectionException exception) {
        //code != 200
        exception.printStackTrace();
      }

    }

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
