import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

 class Sender {
    protected JsonArray sendGETRequest(String apiPath) throws IOException, InterruptedException {
        // create HttpClient instance
        HttpClient client = HttpClient.newBuilder().build();

        // create GET request
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiPath))
                .GET()
                .build();

        // catch response
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        // parse and return JSON array
        return JsonParser.parseString(response.body()).getAsJsonArray();
    }
}
