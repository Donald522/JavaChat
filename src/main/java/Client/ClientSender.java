package Client;


import java.io.*;

public class ClientSender implements  Runnable {

    private volatile Connector connection;
    private volatile Object objectMonitor;
    private volatile boolean trueFlag = true;

    ClientSender(Connector connection, Object objectMonitor) {
        this.objectMonitor = objectMonitor;
        this.connection = connection;
    }

    private void sendMessage() {
        if (connection.isConnected()) {
            System.out.println("run");
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

    @Override
    public void run() {
        while(trueFlag) {
            synchronized (connection) {
                sendMessage();
            }
        }
    }
}
