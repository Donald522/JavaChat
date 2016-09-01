package Server;

import java.io.*;
import java.net.Socket;

public class ClientSession implements Runnable {
    private volatile boolean shutdown;
    private Socket client;
    private BufferedReader br;
    private PrintWriter bw;
    private MessageSender sender;

    public ClientSession(Socket client, MessageSender sender) {
        this.client = client;
        this.sender = sender;
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
                if (line != null) {
                    if(line.equals("/hist"))
                    {
                        sender.sendHistory(this);
                    }
                    if(line.startsWith("/snd"))
                    {
                        sender.sendMessage(line);
                    }
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