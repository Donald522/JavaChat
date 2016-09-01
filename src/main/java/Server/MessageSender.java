package Server;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MessageSender extends Thread {
    private Collection<ClientSession> clientList;
    private Queue<String> messages = new LinkedList<String>();
    private MessageHistory history = new MessageHistory("history.txt");

    public MessageSender(Collection<ClientSession> clientList) {
        this.clientList = clientList;
        try {
            history.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String line) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        line=line.substring(4);
        if(line.length()>1 && line.length()<151) {
            synchronized (messages) {
                messages.add(dateFormat.format(date) + ":" + line + System.lineSeparator());

            }
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (messages) {

                if (messages.size() > 0) {
                    System.out.print(messages.size());

                    String messageToSend = messages.remove();
                    System.out.print(messageToSend);
                    try {
                        history.print(messageToSend);
                        history.closeSession();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (ClientSession client : clientList) {

                        synchronized (client) {
                            if (client.isConnected()) {
                                client.write(messageToSend);
                            } else {
                                synchronized (clientList) {
                                    clientList.remove(client);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void sendHistory(ClientSession clientSession) {

        List<String> stringList = null;
        stringList = history.readLines();
        synchronized (clientSession) {
                for(String str : stringList) {
                    System.out.println(str);
                    clientSession.write(str + System.lineSeparator());
                }
        }
    }
}
