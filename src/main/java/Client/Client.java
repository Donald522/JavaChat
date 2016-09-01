package Client;

import Exceptions.PrinterAppendException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Java_10 on 01.09.2016.
 */
public class Client {

    private Connector connection;
    private ExecutorService pool;
    private ClientReceiver clientReceiver;
    private Object objectMonitor;

    public Client(String serverName) {
        connection = new Connector(serverName);
        pool = Executors.newSingleThreadExecutor();
    }

    public void send() {
        pool.execute(new ClientSender(connection, objectMonitor));
    }

    public void openSession() throws PrinterAppendException {
        try {
            connection.connect();
            send();
            clientReceiver = new ClientReceiver(connection.getSocket());
            clientReceiver.receiveMessage(objectMonitor);
        } catch (IOException e) {
            throw new PrinterAppendException("can't connect", e);
        }
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
