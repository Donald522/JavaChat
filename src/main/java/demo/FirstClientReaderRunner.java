package demo;

import client.ClientReader;
import exceptions.ClientException;

import java.util.logging.Logger;

public class FirstClientReaderRunner {

    private FirstClientReaderRunner() {}

    public static void main(String[] args) {

        Logger log = Logger.getLogger(FirstClientReaderRunner.class.getName());
        Integer port = 1115;
        ClientReader clientReader = null;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        System.out.println(port);
        try {
            clientReader = new ClientReader(port);
            clientReader.run();
        } catch (ClientException e) {
            log.info("Can't connect to the server");
        }
    }
}
