package Client;


import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientSender implements  Runnable {

    Connector connection;
    Object objectMonitor;

    ClientSender(Connector connection, Object objectMonitor) {
        this.objectMonitor = objectMonitor;
        this.connection = connection;
    }

    @Override
    public void run() {
        Console console = System.console();

        while(true) {
//            synchronized (objectMonitor) {
                System.out.println("run");

                try {
                    String message = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                    System.out.println("nextSend");
                    connection.getOutputStream().write(message + System.lineSeparator());
                    System.out.println("sended");
                } catch (IOException e) {
                    try {
                        connection.closeConnection();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
//            }
        }
    }
}
