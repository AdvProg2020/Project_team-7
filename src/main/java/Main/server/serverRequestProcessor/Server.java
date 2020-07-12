package Main.server.serverRequestProcessor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    /**
     * check what the request starts with, guid to relevant request processor
     */
    private HashMap<String, TokenInfo> tokens = new HashMap<>();
    private int TOKEN_EXPIRATION_MINUTES = 15;
    private ServerSocket serverSocket;
    private static Server serverInstance;

    private Server() {
        try {
            serverSocket = new ServerSocket(0);
            System.out.println(serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverInstance = this;
    }

    public static Server getServer() {
        if (serverInstance == null) {
            new Server();
        }
        return serverInstance;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        new requestHandler(clientSocket).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private class requestHandler extends Thread {
        private Socket clientSocket;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;

        public requestHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                this.dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void start() {
            String request = null;
            try {
                request = dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] splitRequest = request.split("/");

            if (splitRequest[1].equals("login")) {

            } else if (splitRequest[1].equals("signUp")) {

            } else if (splitRequest[1].equalsIgnoreCase("data")) {

            }
            /**
             * some if-else statements
             */
        }
    }

}
