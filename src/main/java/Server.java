import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    final int port = 80;

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

                // check if there is username provided
                if (h.getRequestURI().toString().split("/").length == 2) {
                    user = h.getRequestURI().toString().split("/")[1];
                }

                // build page html code
                String response = "<head><link rel=\"icon\" href=\"data:,\"></head><body><p>"
                        + "<h2>Welcome to my server app for handling github api requests!</h2><br />"
                        + "Available commands (to be typed in browsers address bar)<br />"
                        + "localhost/username - get user's list of repos and total sum of stars<br />"
                        + "localhost/shutdown - shutdown the server<br /></p>";

                // append response from github api if requested
                if (user != null && !user.equals("shutdown")) {
                    Sender sender = new Sender();
                    Parser parser = new Parser();
                    response += parser.parseJsonArray(sender.sendGETRequest(user));
                }

                // shutdown app if requested
                if (user != null && user.equals("shutdown")) {
                    threadPoolExecutor.submit(() -> {
                        finalServer.stop(0);
                        System.out.println("HTTP server stopped");
                        threadPoolExecutor.shutdown();
                    });
                    response += "server shutdown!<br />";
                }

                response += "</body>";
                h.sendResponseHeaders(200, response.length());
                OutputStream os = h.getResponseBody();
                os.write(response.getBytes());
                os.close();
            });

            // start the server
            server.setExecutor(threadPoolExecutor);
            server.start();
            System.out.println("HTTP server started");

        } catch (IOException e) {
            System.out.println("error creating server...");
        }

    }
}
