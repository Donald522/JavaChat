package demo;

import client.ClientReader;
import exceptions.ClientException;

/**
 * Created by anton on 01.09.16.
 */
public class FirstClientReaderRunner {
    public static void main(String[] args) throws ClientException {
        ClientReader clientReader = null;
        try {
            clientReader = new ClientReader(1112);
        } catch (ClientException e) {
            throw new ClientException("Can't connect to the server", e);
        }
        clientReader.run();
    }
}
