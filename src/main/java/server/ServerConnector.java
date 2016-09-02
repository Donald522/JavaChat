package server;

import exceptions.ClientSessionException;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

class ServerConnector {

    private ServerSocket server;
    private Collection<ClientSession> clientList = new LinkedList<ClientSession>();
    private MessageSender sender;
    boolean working = true;
    private Logger LOGGER = Logger.getLogger("InfoLogging");

    public ServerConnector(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            LOGGER.info(e.toString());
        }
    }

    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(5000);

        Queue<Message> messages = new LinkedBlockingQueue<Message>();
        try {
            this.sender = new MessageSender(clientList,messages);
        } catch (IOException e) {
            LOGGER.info(e.toString());
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
                LOGGER.info(e.toString());
            } catch (ClientSessionException e) {
                LOGGER.info(e.toString());
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
            LOGGER.info(e.toString());
        }
    }
}