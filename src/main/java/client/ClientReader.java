package client;

import commands.Commands;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientReader {

    private ExecutorService thread1 = Executors.newSingleThreadExecutor();
    private ExecutorService thread2 = Executors.newSingleThreadExecutor();

    Thread listenerToWriterThread;
    Thread receiverFromServerThread;

    private String address;
    private int port;
    private int writerPort;
    private Socket socketToServer;

    public ClientReader(int writerPort) {
        this.address = "localhost";
        this.port = 27015;
        this.writerPort = writerPort;
        initClientReader();
    }

    public ClientReader(int writerPort, String address, int port) {
        this.address = address;
        this.port = port;
        this.writerPort = writerPort;
        initClientReader();
    }

    private void initClientReader() {
        try {
            this.socketToServer = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        listenerToWriterThread = new Thread(new ListenerToWriter(writerPort));
        listenerToWriterThread.start();
        receiverFromServerThread = new Thread(new ReceiverFromServer());
        receiverFromServerThread.setDaemon(true);
        receiverFromServerThread.start();
//        thread1.execute(new ListenerToWriter(writerPort));
//        thread2.execute(new ReceiverFromServer());
//        thread1.shutdown();
//        thread2.shutdown();
        try {
            listenerToWriterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            receiverFromServerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ListenerToWriter implements Runnable {

        private ServerSocket serverSocket;
        private Socket client;
        private BufferedReader in;
        private final int writerPort;

        public ListenerToWriter(int writerPort) {
            this.writerPort = writerPort;
            try {
                serverSocket = new ServerSocket(writerPort);
            } catch (IOException e) {
                e.printStackTrace();
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
                        if (Commands.checkExitCommand(line)) {
                            System.out.println(line);
                            out.println(line);
                            return;
                        }
                        out.println(line);
                    } catch (IOException e) {
                        System.out.println("SocketToServer is closed");
                        close();
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void close() {
            try {
                serverSocket.close();
                client.close();
                in.close();
                System.out.println("Session end");
            } catch (IOException e) {
                e.printStackTrace();
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
                while(socketToServer.isConnected()) {
                    try {
                        message = in.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        close();
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Connection to server lost");
            }
        }

        private void close() {
            try {
                socketToServer.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
