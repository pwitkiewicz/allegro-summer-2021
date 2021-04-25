import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    final private int port = 8080;
    final private String htmlTopPart = "<head><link rel=\"icon\" href=\"data:,\"></head><body><p>"
            + "<h2>Welcome to my server app for handling github api requests!</h2><br />"
            + "Available commands (to be typed in browsers address bar)<br />"
            + "localhost/username - get user's list of repos and total sum of stars<br />"
            + "localhost/stars/username - get user's sum of all stars<br />"
            + "localhost/shutdown - shutdown the server<br /></p>";

    public int getPort() {
        return port;
    }

    private String prepareOutput(Result r) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String key : r.getRepoList().keySet()) {
            sb.append(i).append(". ").append(key);
            sb.append(" stars: ").append(r.getRepoList().get(key));
            sb.append("<br />");
            i++;
        }
        return sb.toString();
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
                response.append(htmlTopPart);

                // append response from github api if requested
                if (user != null && !user.equals("shutdown")) {
                    Sender sender = new Sender();
                    Parser parser = new Parser();

                    Result r = parser.parseJsonArray(sender.sendGETRequest(user));
                    if(r.getStarsSum() == -1) {
                        response.append("User not found!");
                    } else if(!onlyStars) {
                        response.append(user).append("'s repo list:<br />")
                            .append(prepareOutput(r));
                    } else {
                        response.append(user).append("'s total sum of stars: ").append(r.getStarsSum());
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
