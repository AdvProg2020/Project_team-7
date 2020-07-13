package Main.server.serverRequestProcessor;

import Main.server.model.accounts.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    /**
     * check what the request starts with, guid to relevant request processor
     */
    private HashMap<String, TokenInfo> tokens = new HashMap<>();
    public static int TOKEN_EXPIRATION_MINUTES = 15;
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
            } else if (splitRequest[1].equals("login")) {
                response = GeneralRequestProcessor.loginRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("signUp")) {
                response = GeneralRequestProcessor.signUpRequestProcessor(splitRequest);
            } else if (splitRequest[1].equalsIgnoreCase("signUpSeller")) {
                response = GeneralRequestProcessor.signUpSellerRequestProcessor(splitRequest);
            } else if (splitRequest[1].equalsIgnoreCase("signUPBuyer")) {
                response = GeneralRequestProcessor.signUpBuyerRequestProcessor(splitRequest);
            } else if (splitRequest[1].equalsIgnoreCase("data")) {

            } else if (splitRequest[1].equals("disconnect")) {
                response = "disconnected";
            }

            dataOutputStream.writeUTF(response);
            dataOutputStream.flush();

            if (response != "disconnected") {
                handle();
            } else {
                removeToken(splitRequest[0]);
                clientSocket.close();
                dataOutputStream.close();
                dataInputStream.close();
            }
        }
    }

    public TokenInfo getTokenInfo(String token) {
        TokenInfo tokenInfo = null;
        for (String tokenString : tokens.keySet()) {
            if (tokenString.equals(token)) {
                tokenInfo = tokens.get(tokenString);
                break;
            }
        }
        if (tokenInfo == null) {
            return null;
        }
        if (tokenInfo.hasTokenExpired()) {
            removeToken(token);
            return null;
        } else {
            return tokenInfo;
        }
    }

    public void removeToken(String token) {
        if (tokens.containsKey(token)) {
            Account account = tokens.get(token).getUser();
            account.removeToken();
            tokens.remove(token);
        }
    }

    public void addToken(Account account) {
        String token = getRandomString(5);
        account.setToken(token);
        TokenInfo tokenInfo = new TokenInfo(account);
        tokens.put(token, tokenInfo);
    }

    private String getRandomString(int stringLength) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; i++) {

            int index = (int) (AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
        }

        return stringBuilder.toString();
    }
}
