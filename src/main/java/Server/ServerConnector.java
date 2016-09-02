package Server;

import Exceptions.ClientSessionException;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

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
    }

    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(5000);

        Queue<Message> messages = new LinkedBlockingQueue<Message>();
        try {
            this.sender = new MessageSender(clientList,messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sender.start();
        while (working) {
            try {
                ClientSession clientSession = new ClientSession(this.server.accept(), messages);
                pool.execute(clientSession);
                synchronized (clientList) {
                    clientList.add(clientSession);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClientSessionException e) {
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