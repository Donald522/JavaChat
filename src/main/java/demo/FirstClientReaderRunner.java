package demo;

import client.ClientReader;

/**
 * Created by anton on 01.09.16.
 */
public class FirstClientReaderRunner {
    public static void main(String[] args) {
        ClientReader clientReader = new ClientReader(1112);
        clientReader.run();
    }
}
