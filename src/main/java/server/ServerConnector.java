package server;

import exceptions.ClientSessionException;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;


class ServerConnector {

    private ServerSocket server;
    private Collection<ClientSession> clientList = new LinkedList<ClientSession>();
    private MessageSender sender;
    boolean working = true;
    private Logger log = Logger.getLogger(ServerConnector.class.getName());

    public ServerConnector(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            log.info(e.toString());
        }
    }

    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(5000);

        Queue<Message> messages = new LinkedBlockingQueue<Message>();
        try {
            this.sender = new MessageSender(clientList,messages);
        } catch (IOException e) {
            log.info(e.toString());
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
                log.info(e.toString());
            } catch (ClientSessionException e) {
                log.info(e.toString());
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
            log.info(e.toString());
        }
    }
}