public class App {
    public static void main(String[] args) {
        Server server = new Server();
        System.out.println("server will run on localhost, port " + server.getPort());
        server.start();
    }
}