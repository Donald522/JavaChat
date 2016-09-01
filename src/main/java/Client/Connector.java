package Client;

import java.io.*;
import java.net.Socket;

/**
 * Created by Java_10 on 01.09.2016.
 */
public class Connector {
    private PrintWriter outStream;
    private BufferedReader inputStream;
    private String serverName = "localhost";
    private volatile Socket socket;
    private int port = 27015;

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
        outStream = new PrintWriter(
                new OutputStreamWriter(
                        new BufferedOutputStream(
                                socket.getOutputStream()
                        ), "UTF-8"));
        inputStream = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                            socket.getInputStream()
                        ), "UTF-8"));
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeConnection() throws IOException {
        outStream.close();

    }

    public PrintWriter getOutputStream() {
        return outStream;
    }

    public BufferedReader getInputStream() {
        return inputStream;
    }
}
