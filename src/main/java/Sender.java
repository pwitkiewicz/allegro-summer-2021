import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

 class Sender {
    protected JsonArray sendGETRequest(String user) {
        // create apiPath
        String apiPath = "https://api.github.com/users/" + user + "/repos";

        // create HttpClient instance
        HttpClient client = HttpClient.newBuilder().build();

        // create GET request
        HttpRequest httpRequest;
        try {
            httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiPath))
                    .GET()
                    .build();
        } catch (Exception e) {
            System.out.println("Error creating http request");
            return null;
        }

        // catch response
        HttpResponse<String> response;
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Error on http communicaton with github api server");
            return null;
        }

        // parse and return JSON array or null if no user found
        if(JsonParser.parseString(response.body()).isJsonObject()) {
            return null;
        }

        return JsonParser.parseString(response.body()).getAsJsonArray();
    }
}
