package Server;

public class Server {

    public static void main(String[] args) {
        ServerConnector connector = new ServerConnector(27015);
        connector.run();
    }


}