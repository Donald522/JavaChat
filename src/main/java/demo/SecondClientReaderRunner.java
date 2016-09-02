package demo;

import client.ClientReader;
import exceptions.ClientException;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Created by anton on 01.09.16.
 */
public class SecondClientReaderRunner {
    public static void main(String[] args) {
        ClientReader clientReader = null;
        try {
            clientReader = new ClientReader(1113);
            clientReader.run();
        } catch (ClientException e) {
            LOGGER.info("Can't connect to the server");
        }
    }
}
