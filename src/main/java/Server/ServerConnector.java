package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServerConnector extends Thread{
    private ServerSocket server;

    public ServerConnector(int port){
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        while (true){
            try {
                ClientSession client = new ClientSession(this.server.accept());
                pool.execute(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}