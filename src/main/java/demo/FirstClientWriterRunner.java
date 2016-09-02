package demo;

import client.ClientReader;
import client.ClientWriter;

/**
 * Created by anton on 01.09.16.
 */
public class FirstClientWriterRunner {
    public static void main(String[] args) {
        Integer port = 1115;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        ClientWriter clientWriter = new ClientWriter("localhost", port);
        clientWriter.run();
    }
}
