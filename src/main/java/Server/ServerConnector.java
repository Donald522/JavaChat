package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServerConnector {

    private ServerSocket server;
    private Collection<ClientSession> clientList = new LinkedList<ClientSession>();
    private MessageSender sender;
    boolean working = true;
    public ServerConnector(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sender = new MessageSender(clientList);
        sender.start();
    }

    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(5000);

        while (working) {
            try {
                ClientSession clientSession = new ClientSession(this.server.accept(), sender);
                pool.execute(clientSession);
                synchronized (clientList) {
                    clientList.add(clientSession);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void close(){
        try {
            for(ClientSession session: clientList){
                session.close();

            }
            server.close();
            sender.interrupt();
            working = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}