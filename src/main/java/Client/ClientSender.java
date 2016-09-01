package Client;


import java.io.Console;
import java.io.IOException;

public class ClientSender implements  Runnable {

    Connector connection;
    Object objectMonitor;

    ClientSender(Connector connectionm, Object objectMonitor) {
        this.objectMonitor = objectMonitor;
        this.connection = connection;
    }

    @Override
    public void run() {
        Console console = System.console();
        while(true) {
            synchronized (objectMonitor) {
                String message = console.readLine();
                try {
                    connection.getOutputStream().write(message + System.lineSeparator());
                } catch (IOException e) {
                    try {
                        connection.closeConnection();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
