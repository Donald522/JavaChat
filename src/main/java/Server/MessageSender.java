package Server;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageSender extends Thread {
    private Collection<ClientSession> clientList;
    private Queue<Message> messages = null;
    private MessageHistory history = new MessageHistory("history.txt");

    public MessageSender(Collection<ClientSession> clientList, Queue<Message> messages) throws IOException {
        this.messages = messages;
        this.clientList = clientList;
        try {
            history.openSession();
        } catch (IOException e) {
            throw new IOException("can't read history",e);
        }
    }

    public void sendMessage(String line, String userName) {

        Date date = new Date();
        line=line.substring(4);
        if(line.length()>1 && line.length()<151) {
          //      messages.add(dateFormat.format(date) + ":" + line + System.lineSeparator());
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (messages.size() > 0) {

                        Message messageToSend = messages.remove();
                        if(messageToSend.isPublic()) {
                            sendPublicMessage(messageToSend.decoratedMessage());
                        }
                        if(messageToSend.isHistorical()) {
                            saveMessageToHistory(messageToSend.decoratedMessage());
                        }
                        if(messageToSend.isHistoryReqest()) {

                            try {
                                sendHistory(messageToSend.getClient());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        if(messageToSend.isErrorMessage()){
                            sendErrorMessage(messageToSend.getClient(),messageToSend.getTextLine());
                        }
                        System.out.print(messageToSend);
                    }
                }

        try {
            history.closeSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendHistory(ClientSession client) throws IOException {

        List<String> stringList = null;
        stringList = history.readLines();
        synchronized (client) {
            for(String str : stringList) {
                client.write(str + System.lineSeparator());
            }
        }
    }
    public void sendPublicMessage(String decoratedMessage) {

        for (ClientSession client : clientList) {
            synchronized (client) {
                if (client.isConnected()) {
                    client.write(decoratedMessage);
                    System.out.print(decoratedMessage);
                } else {
                    clientList.remove(client);
                    client.close();

                }
            }
        }
    }


    public void sendErrorMessage(ClientSession client,String message) {
        client.write("=============================" + System.lineSeparator() + System.lineSeparator());
        client.write(message + System.lineSeparator());
        client.write(System.lineSeparator() + System.lineSeparator() + "=============================");
    }

    public void saveMessageToHistory(String decoratedMessage) {
        try {
            history.print(decoratedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
