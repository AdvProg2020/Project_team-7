package Main.bankServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

    private ServerSocket serverSocket;
    private static BankServer bankServerInstance;
    private Bank bank = Bank.getInstance();

    private BankServer() {
        try {
            serverSocket = new ServerSocket(0);
            System.out.println(serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bankServerInstance = this;
    }

    public static BankServer getServer() {
        if (bankServerInstance == null) {
            new BankServer();
        }
        return bankServerInstance;
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    new RequestHandler(clientSocket).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private class RequestHandler extends Thread {
        private Socket clientSocket;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;

        public RequestHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                this.dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                handle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handle() throws Exception {
            String request = dataInputStream.readUTF();
            System.out.println("bank read:" + request);
            String response = null;
            String[] splitRequest = request.split(" ");
            if (splitRequest[0].equals("create_account")) {
                response = bank.createAccount(splitRequest[1], splitRequest[2], splitRequest[3], splitRequest[4], splitRequest[5]);
            } else if (splitRequest[0].equals("get_token")) {
                response = bank.getToken(splitRequest[1], splitRequest[2]);
            } else if (splitRequest[0].equals("create_receipt")) {
                if (splitRequest.length == 7) {
                    response = bank.createReceipt(splitRequest[1], splitRequest[2], splitRequest[3], splitRequest[4], splitRequest[5], splitRequest[6]);
                } else if (splitRequest.length == 6) {
                    response = bank.createReceipt(splitRequest[1], splitRequest[2], splitRequest[3], splitRequest[4], splitRequest[5], "");
                }
            } else if (splitRequest[0].equals("get_transactions")) {
                response = bank.getTransactions(splitRequest[1], splitRequest[2]);
            } else if (splitRequest[0].equals("pay")) {
                response = bank.payReceipt(splitRequest[1]);
            } else if (splitRequest[0].equals("get_balance")) {
                response = bank.getBalance(splitRequest[1]);
            } else if (splitRequest[0].equals("exit")) {
                response = "disconnected";
            } else {
                response = "invalidInput";
            }
            dataOutputStream.writeUTF(response);
            dataOutputStream.flush();
            System.out.println("bank wrote: " + response);
            if (!response.equals("disconnected")) {
                handle();
            } else {
                clientSocket.close();
                dataInputStream.close();
                dataOutputStream.close();
            }
        }
    }

    public static void main(String[] args) {
        BankServer bankServer = BankServer.getServer();
        bankServer.start();
    }
}
