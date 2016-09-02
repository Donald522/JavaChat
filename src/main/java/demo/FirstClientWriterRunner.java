package demo;

import client.ClientWriter;

/**
 * Created by anton on 01.09.16.
 */
public class FirstClientWriterRunner {
    public static void main(String[] args) {
        ClientWriter clientWriter = new ClientWriter("localhost", 1112);
        clientWriter.run();
    }
}
