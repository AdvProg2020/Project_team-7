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
                        new requestHandler(clientSocket).handle();
                    } catch (Exception e) {
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

        public void handle() throws Exception {
            String request = null;
            String response = null;
            String[] splitRequest = new String[0];

            request = dataInputStream.readUTF();

            splitRequest = request.split("/");
            if (splitRequest.length < 2) {
                response = "invalidRequest";
            } else {
                if (splitRequest[1].equals("login")) {
                    response = GeneralRequestProcessor.loginRequestProcessor(splitRequest);
                } else if (splitRequest[1].equals("signUp")) {
                    response = GeneralRequestProcessor.signUpRequestProcessor(splitRequest);
                } else if (splitRequest[1].equalsIgnoreCase("data")) {

                }
                /**
                 * some if-else statements
                 */
            }

            dataOutputStream.writeUTF(response);
            dataOutputStream.flush();

            if (response != "disconnected") {
                handle();
            }
        }
    }

}
