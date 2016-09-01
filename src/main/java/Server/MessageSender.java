package Server;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MessageSender extends Thread {
    private List<ClientSession> clientList;
    private Queue<String> messages = new LinkedList<String>();

    public MessageSender(List<ClientSession> clientList) {
        this.clientList = clientList;
    }

    public void sendMessage(String line) {
        //TODO: decoration date
        synchronized (messages) {
            messages.add(line);
        }
    }

    @Override
    public void run() {
        synchronized (messages) {
            while (true) {
                if (messages.size() > 0) {
                    String messageToSend = messages.remove();

                    for (ClientSession client : clientList) {
                        if(client != null) {
                            client.write(messageToSend);
                        }
                    }
                }
            }
        }
    }
}
