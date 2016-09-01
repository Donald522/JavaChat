package Server;

import java.io.*;
import java.net.Socket;

public class ClientSession implements Runnable {
    private Socket client;
    private BufferedReader br;
    private OutputStreamWriter bw;
    private MessageSender sender;

    public ClientSession(Socket client, MessageSender sender) {
        this.client = client;
        this.sender = sender;
        try {
            this.br = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    this.client.getInputStream()
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            bw = new OutputStreamWriter(
//                    new BufferedOutputStream(
//                            this.client.getOutputStream()
//                    )
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void run() {
        System.out.println("clientSession run");
        while (client.isConnected()) {
            try {
                client.getOutputStream().write(1);
            } catch (IOException e) {
                System.out.println("break");
                break;
            }
            try {
                System.out.println("clientSession receive mess");
                String line = br.readLine();
                System.out.println("readLine End");
                System.out.println(line);
                if (line != null) {
                    System.out.println("readed ");
                    sender.sendMessage(line);
                    System.out.print(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("clientSession end");
    }

    public void write(String line) {
        try {
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}