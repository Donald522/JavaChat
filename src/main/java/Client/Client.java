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
    private ClientReceiver clientReceiver;
    private ClientSender clientSender;

    public Client(String serverName) {
        connection = new Connector(serverName);
    }

    public void openSession() throws PrinterAppendException {
        try {
            connection.connect();
            clientSender = new ClientSender(connection);
            clientSender.run();
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
        new ClientSender(connection).run();
    }

    public void closeSession() throws PrinterAppendException {
        try {
            connection.closeConnection();
        } catch (IOException e) {
            throw new PrinterAppendException("can't connect", e);
        }
    }
}
