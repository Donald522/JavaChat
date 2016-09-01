package Client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Java_9 on 01.09.2016.
 */
public class ClientReceiver {
    private Socket socket;
    private BufferedReader br;
    public ClientReceiver(Socket socketServer) {
        this.socket = socketServer;
        try {
            this.br = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    this.socket.getInputStream()
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage() {
        while (socket.isConnected()) {
            try {
                socket.getOutputStream().write(0);
            } catch (IOException e) {
                break;
            }
            try {
                String line = br.readLine();
                if (line != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
