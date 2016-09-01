package Client;

import Exceptions.PrinterAppendException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Java_10 on 01.09.2016.
 */
public class Client {

    private volatile Connector connection;
    private ExecutorService pool;
    private ClientReceiver clientReceiver;
    private ClientSender clientSender;
    private volatile Object objectMonitor = new Object();

    public Client(String serverName) {
        connection = new Connector(serverName);
        pool = Executors.newSingleThreadExecutor();
    }

    public void openSession() throws PrinterAppendException {
        try {
            connection.connect();
            clientSender = new ClientSender(connection, objectMonitor);
            pool.execute(clientSender);
//            clientSender = new ClientSender(connection, objectMonitor);
//            clientSender.run();
//            clientReceiver = new ClientReceiver(connection.getSocket());
//            clientReceiver.receiveMessage(objectMonitor);
        } catch (IOException e) {
            throw new PrinterAppendException("Can't connect", e);
        }
    }

    public void send() {
        //pool.execute(new ClientSender(connection, objectMonitor));
//        try {
//            connection.getOutputStream().write("Message");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        new ClientSender(connection, objectMonitor).run();
    }

    public void closeSession() throws PrinterAppendException {
        try {
            connection.closeConnection();
            pool.shutdown();
        } catch (IOException e) {
            throw new PrinterAppendException("can't connect", e);
        }
    }
}
