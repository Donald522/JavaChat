package client;

import commands.CheckCommands;
import exceptions.ClientException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import static commands.CheckCommands.*;


public class ClientReader {

    Thread listenerToWriterThread;
    Thread receiverFromServerThread;

    private String address;
    private int port;
    private int writerPort;
    private Socket socketToServer;

    Logger log = Logger.getLogger(ClientReader.class.getName());

    public ClientReader(int writerPort) throws ClientException {
        this.address = "localhost";
        this.port = 27015;
        this.writerPort = writerPort;
        initClientReader();
    }

    public ClientReader(int writerPort, String address, int port) throws ClientException {
        this.address = address;
        this.port = port;
        this.writerPort = writerPort;
        initClientReader();
    }

    private void initClientReader() throws ClientException {
        try {
            this.socketToServer = new Socket(address, port);
        } catch (IOException e) {
            throw new ClientException("Can't connect to the server");
        }
    }

    public void run() {
        receiverFromServerThread = new Thread(new ReceiverFromServer());
        listenerToWriterThread = new Thread(new ListenerToWriter(writerPort, receiverFromServerThread));
        listenerToWriterThread.start();
        receiverFromServerThread.start();
        try {
            listenerToWriterThread.join();
        } catch (InterruptedException e) {
            log.info("InterruptedException");
        }
        try {
            receiverFromServerThread.join();
        } catch (InterruptedException e) {
            log.info("InterruptedException");
        }
    }

    private class ListenerToWriter implements Runnable {

        private ServerSocket serverSocket;
        private Socket client;
        private BufferedReader in;
        private final int writerPort;
        private Thread receiverFromServerThread;

        public ListenerToWriter(int writerPort, Thread receiverFromServerThread) {
            this.receiverFromServerThread = receiverFromServerThread;
            this.writerPort = writerPort;
            try {
                serverSocket = new ServerSocket(writerPort);
            } catch (IOException e) {
                log.info("Can't connect to the server");
            }
        }
        @Override
        public void run() {
            try {
                client = serverSocket.accept();
                in = new BufferedReader(
                        new InputStreamReader(
                                new BufferedInputStream(
                                        client.getInputStream()
                                ), "UTF-8"
                        )
                );
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(
                                new BufferedOutputStream(
                                        socketToServer.getOutputStream()
                                ), "UTF-8"
                        ), true
                );
                while(isConnected()) {
                    try {
                        String line = in.readLine();
                            if (checkExitCommand(line)) {
                                System.out.println(line);
                                out.println(line);
                                close();
                                return;
                            }
                            out.println(line);
                    } catch (IOException e) {
                        log.info("Connection is closed");
                        System.out.println("SocketToServer is closed");
                        close();
                        return;
                    }
                }
            } catch (IOException e) {
                log.info("Can't connect to the server");
            }
        }

        private void close() {
            try {
                if (receiverFromServerThread != null && receiverFromServerThread.isAlive()) {
                    receiverFromServerThread.interrupt();
                }
                serverSocket.close();
                client.close();
                in.close();
                System.out.println("Session end");
            } catch (IOException e) {
                log.info("Error when closing resources");
            }
        }

        private boolean isConnected(){
            try {
                client.getOutputStream().write(0);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    private class ReceiverFromServer implements Runnable {

        private BufferedReader in;

        @Override
        public void run() {
            String message;
            try {
                in = new BufferedReader(
                                        new InputStreamReader(
                                                socketToServer.getInputStream()));
                while(socketToServer.isConnected() & (!Thread.currentThread().isInterrupted())) {
                    try {
                        message = in.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        close();
                        return;
                    }
                }
            } catch (IOException e) {
                log.info("Can't send message to server");
            } finally {
                System.out.println("Connection to server lost");
            }
        }

        private void close() {
            try {
                if(!socketToServer.isClosed()) {
                    socketToServer.close();
                }
                in.close();
            } catch (IOException e) {
                log.info("Close failed");
            }
        }
    }
}
