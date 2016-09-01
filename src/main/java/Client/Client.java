package Client;

import Exceptions.PrinterAppendException;

import java.io.IOException;

/**
 * Created by Java_10 on 01.09.2016.
 */
public class Client {

    Connector connection;


    public Client(String serverName) {
        connection = new Connector(serverName);
    }

    public void print(String message) throws PrinterAppendException {
        if (!connection.isConnected()) {
            try {
                connection.connect();
            } catch (IOException e) {
                throw new PrinterAppendException("can't connect", e);
            }
        }
        try {
            System.out.print(message + System.lineSeparator());
            connection.getOutputStream().write(message + System.lineSeparator());
        } catch (IOException e) {
            throw new PrinterAppendException("can't connect", e);
        }
    }

    public void openSession() throws PrinterAppendException {
        try {
            connection.connect();
        } catch (IOException e) {
            throw new PrinterAppendException("can't connect", e);
        }
    }

    public void closeSession() throws PrinterAppendException {
        try {
            connection.closeConnection();
        } catch (IOException e) {
            throw new PrinterAppendException("can't connect", e);
        }
    }
}
