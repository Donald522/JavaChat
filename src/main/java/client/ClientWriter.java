package client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import static commands.CheckCommands.*;

public class ClientWriter {

    private final int port;
    private final String address;

    Logger log = Logger.getLogger(ClientWriter.class.getName());

    public ClientWriter() {
        this.address = "localhost";
        this.port = 1412;
    }

    public ClientWriter(int port) {
        this.address = "localhost";
        this.port = port;
    }

    public ClientWriter(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        String message;

        try (
            Socket socket = new Socket(address, port);
            PrintWriter out = new PrintWriter(
                                    new OutputStreamWriter(
                                        new BufferedOutputStream(
                                            socket.getOutputStream()
                                        ), "UTF-8"
                                    ), true
                                )
            ) {

                while(socket.isConnected()) {
                    message = scan.nextLine().trim();
                    if (checkHistCommand(message)) {
                        out.println(message.substring(0, 5));
                    } else if (checkSndCommand(message) && message.length() > 4) {
                        String nocommandMessage = message.substring(4);
                        if (!checkLength(nocommandMessage)) {
                            System.out.println("Message iss too long. It's cropped to 150 symbol.");
                        }
                        out.println(message.substring(0, (154 < message.length() ? 154 : message.length())));
                    } else if (checkNameCommand(message)) {
                        out.println(message);
                    } else if (checkExitCommand(message)) {
                        out.println( message.substring(0, 5));
                        return;
                    }
                }
        } catch (IOException e) {
            log.info("Can't send message to the server");
        }
    }

}
