import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    final int port = 8080;

    public int getPort() {
        return port;
    }

    public void start() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        HttpServer server = null;

        try {
            server = HttpServer.create(new InetSocketAddress("localhost", port), 3);
            HttpServer finalServer = server;

            server.createContext("/", (HttpExchange h) -> {
                String user = null;
                boolean onlyStars = false;

                // check if there is username provided
                if (h.getRequestURI().toString().split("/").length == 2) {
                    user = h.getRequestURI().toString().split("/")[1];
                }

                // check if only stars requested
                if (h.getRequestURI().toString().split("/").length == 3 && h.getRequestURI().toString().split("/")[1].equals("stars")) {
                    user = h.getRequestURI().toString().split("/")[2];
                    onlyStars = true;
                }

                // build page html code
                StringBuilder response = new StringBuilder();
                response.append("<head><link rel=\"icon\" href=\"data:,\"></head><body><p>")
                        .append("<h2>Welcome to my server app for handling github api requests!</h2><br />")
                        .append("Available commands (to be typed in browsers address bar)<br />")
                        .append("localhost/username - get user's list of repos and total sum of stars<br />")
                        .append("localhost/shutdown - shutdown the server<br /></p>");

                // append response from github api if requested
                if (user != null && !user.equals("shutdown")) {
                    Sender sender = new Sender();
                    Parser parser = new Parser();

                    Result r = parser.parseJsonArray(sender.sendGETRequest(user));
                    if(r.getStarsSum() == -1) {
                        response.append("User not found!");
                    } else if(!onlyStars) {
                        response.append(r.getRepoList());
                    } else {
                        response.append("Total sum of stars: ").append(r.getStarsSum());
                    }
                }

                // shutdown app if requested
                if (user != null && user.equals("shutdown")) {
                    threadPoolExecutor.submit(() -> {
                        finalServer.stop(0);
                        System.out.println("HTTP server stopped");
                        threadPoolExecutor.shutdown();
                    });
                    response.append("server shutdown!<br />");
                }

                response.append("</body>");
                h.sendResponseHeaders(200, response.length());
                OutputStream os = h.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();
            });

            // start the server
            server.setExecutor(threadPoolExecutor);
            server.start();
            System.out.println("HTTP server started");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error creating server...");
        }
    }
}
