package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServerConnector{
    private ServerSocket server;
    private List<ClientSession> clientList = new ArrayList<ClientSession>();
    private MessageSender sender;
    public ServerConnector(int port){
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sender = new MessageSender(clientList);
    }

    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        while (true){
            try {
                ClientSession client = new ClientSession(this.server.accept(),sender);
                pool.execute(client);
                clientList.add(client);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}