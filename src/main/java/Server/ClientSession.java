package Server;

import java.io.*;
import java.net.Socket;
import java.util.Queue;

public class ClientSession implements Runnable {
    private volatile boolean shutdown;
    private Socket client;
    private BufferedReader br;
    private PrintWriter bw;
    private Queue<Message> messages;

    public ClientSession(Socket client, Queue<Message> messages) {
        this.client = client;
        this.messages = messages;
        try {
            this.br = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    this.client.getInputStream()
                            ), "UTF-8"
                    )
            );

            this.bw = new PrintWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    this.client.getOutputStream()
                            ), "UTF-8"
                    )
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (client.isConnected()) {
            try {
                String line = br.readLine();
                System.out.println(line);
                if (line != null && line.length()>0) {
                    Message message = new Message(line,this);
                    messages.add(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!isConnected()){
                break;
            }
        }
    }

    public boolean isConnected(){
        try {
            client.getOutputStream().write(1);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void write(String line) {
        if(bw!=null) {
            bw.write(line);
            bw.flush();
        }
    }
    public void close(){
        try {
            bw.close();
            br.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}