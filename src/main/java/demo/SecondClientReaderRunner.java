package demo;

import client.ClientReader;
import exceptions.ClientException;

import java.util.logging.Logger;

public class SecondClientReaderRunner {

    private SecondClientReaderRunner() {}

    public static void main(String[] args) {

        Logger log = Logger.getLogger(SecondClientReaderRunner.class.getName());

        ClientReader clientReader = null;
        try {
            clientReader = new ClientReader(1113);
            clientReader.run();
        } catch (ClientException e) {
            log.info("Can't connect to the server");
        }
    }
}
