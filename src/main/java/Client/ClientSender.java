package Client;


import java.io.*;

public class ClientSender implements  Runnable {

    private volatile Connector connection;
    private volatile boolean trueFlag = true;

    ClientSender(Connector connection) {
        this.connection = connection;
    }

    private void sendMessage() {
        if (connection.isConnected()) {
            System.out.println("write");
            try {
                String message = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                System.out.println("nextSend " + message);
                connection.getOutputStream().println(message + System.lineSeparator());
                connection.getOutputStream().flush();
                System.out.println("sended");
            } catch (IOException e) {
                System.out.println("ioerror");
                try {
                    connection.closeConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private boolean isTrue() {
        return true;
    }

    private void receiveMessage() {
        if (connection.isConnected()) {
            System.out.println("read");
            try {
                String line = connection.getInputStream().readLine();
                System.out.println(line);
                System.out.println("received");
            } catch (IOException e) {
                System.out.println("ioerror");
                try {
                    connection.closeConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        while(trueFlag) {
            synchronized (connection) {
                sendMessage();
            }
            synchronized (connection) {
                receiveMessage();
            }
        }
    }
}
