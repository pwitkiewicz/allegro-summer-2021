import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int port = 6666;

    public int getPort() {
        return port;
    }

    public void init() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        handleConnection(socket);
        serverSocket.close();
    }

    private void handleConnection(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        PrintWriter writer = new PrintWriter(output, true);

        writer.println("Welcome to my server app for handling github api requests!\n\r" +
                "Available commands\n\r" +
                "<username> - get user's list of repos and total sum of stars\n\r" +
                "<shutdown> - shutdown server\n\r");

        String line = reader.readLine();

        while(!line.startsWith("shutdown")) {

        }

        socket.close();
    }
}
