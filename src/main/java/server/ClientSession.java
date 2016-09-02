package Server;

import exceptions.ClientSessionException;

import java.io.*;
import java.net.Socket;
import java.util.Queue;

public class ClientSession implements Runnable {
    private Socket client;
    private BufferedReader readerFromSocket;
    private PrintWriter writerToSocket;
    private Queue<Message> messages;

    public ClientSession(Socket client, Queue<Message> messages) throws ClientSessionException {
        this.client = client;
        this.messages = messages;
        try {
            this.readerFromSocket = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    this.client.getInputStream()
                            ), "UTF-8"
                    )
            );

            this.writerToSocket = new PrintWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    this.client.getOutputStream()
                            ), "UTF-8"
                    )
            );

        } catch (IOException e) {
            throw new ClientSessionException("Can't open Streams",e);
        }
    }

    @Override
    public void run() {
        while (client.isConnected()) {
            try {
                String line = readerFromSocket.readLine();
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
        if(writerToSocket !=null) {
            writerToSocket.write(line);
            writerToSocket.flush();
        }
    }
    public void close(){
        try {
            writerToSocket.close();
            readerFromSocket.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}