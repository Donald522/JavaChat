package Client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by Java_10 on 01.09.2016.
 */
public class Connector {
    private OutputStreamWriter outStream;
    private String serverName = "localhost";
    private Socket socket;
    int port = 27015;

    public Connector(String serverName) {
        this.serverName = serverName;
    }

    public Connector(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
    }

    public boolean isConnected() {
        return outStream != null;
    }

    public void connect() throws IOException {
        socket = new Socket(serverName, port);
        outStream = new OutputStreamWriter(
                new BufferedOutputStream(
                        socket.getOutputStream()
                )
        );
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeConnection() throws IOException {
        outStream.close();

    }

    public OutputStreamWriter getOutputStream() {
        return outStream;
    }
}
