package Server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSession implements Runnable {
    private Socket client;
    private BufferedReader br;

    public ClientSession(Socket client){
        this.client=client;
        try {
            this.br = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    this.client.getInputStream()
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while (client.isConnected()){
            try {
                String line = br.readLine();
                if (line != null) {
                    System.out.print(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //eugene krivosheyev  told to do it so
            try {
                client.getOutputStream().write(1);
            } catch (IOException e) {
                break;
            }
        }
    }
}