package demo;

import client.ClientReader;
import exceptions.ClientException;

import java.util.logging.Logger;

public class FirstClientReaderRunner {
    public static void main(String[] args) {

        Logger log = Logger.getLogger(FirstClientReaderRunner.class.getName());

        ClientReader clientReader = null;
        try {
            clientReader = new ClientReader(1112);
            clientReader.run();
        } catch (ClientException e) {
            log.info("Can't connect to the server");
        }
    }
}
