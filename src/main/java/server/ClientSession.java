package server;

import commands.Commands;
import exceptions.ClientSessionException;

import java.io.*;
import java.net.Socket;
import java.util.Queue;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


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
        while (isConnected()) {
            try {

                if(!isConnected()){
                    break;
                }
                String line = readerFromSocket.readLine();
                System.out.println(line);
                if (line != null && line.length()>0) {
                    if(Commands.checkExitCommand(line)) {
                        this.close();
                        break;
                    }
                    Message message = new Message(line,this);
                    messages.add(message);
                }
            } catch (IOException e) {
                LOGGER.info(e.toString());
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
            LOGGER.info(e.toString());
        }
    }
}