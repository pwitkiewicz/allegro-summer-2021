import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int port = 6666;

    public int getPort() {
        return port;
    }

    public void init() {
        // todo handling multiple clients
        // todo server shutdown on timeout
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            handleConnection(socket);
            serverSocket.close();
        }
        catch (IOException e) {
            System.out.println("Error creating server");
        }
    }

    private void handleConnection(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        PrintWriter writer = new PrintWriter(output, true);

        writer.println("""
                Welcome to my server app for handling github api requests!\n\r
                Available commands\n\r
                <username> - get user's list of repos and total sum of stars\n\r
                <shutdown> - shutdown server\n\r
                """);

        String line = reader.readLine();

        Sender sender = new Sender();
        Parser parser = new Parser();

        while(!line.startsWith("shutdown")) {
            String result = parser.parseJsonArray(sender.sendGETRequest(line));
            writer.println(result);

            line = reader.readLine().replaceAll("[^A-Za-z0-9]+", "");
            writer.println("input: " + line);
        }

        socket.close();
    }
}
