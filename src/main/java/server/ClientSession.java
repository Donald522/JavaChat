package server;

import commands.CheckCommands;
import exceptions.ClientSessionException;

import java.io.*;
import java.net.Socket;
import java.util.Queue;
import java.util.logging.Logger;



public class ClientSession implements Runnable {

    private Socket client;
    private BufferedReader readerFromSocket;
    private PrintWriter writerToSocket;
    private Queue<Message> messages;
    private Logger log = Logger.getLogger(ClientSession.class.getName());
    private String userName = null;

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
        while (isConnected()) {
            try {

                if(!isConnected()){
                    break;
                }
                String line = readerFromSocket.readLine();
                if (line != null && line.length()>0) {
                    if(CheckCommands.checkExitCommand(line)) {
                        this.close();
                        break;
                    }
                    Message message = new Message(line, this, userName);
                    synchronized (message) {
                        messages.add(message);
                    }

                }
            } catch (IOException e) {
                log.info(e.toString());
            }
        }
    }

    public boolean isConnected(){
        try {
            client.getOutputStream().write(0);
            client.getOutputStream().flush();
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
            log.info(e.toString());
        }
    }

    public Object getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }
}