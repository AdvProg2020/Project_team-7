package Main.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BankClient {
    private static int port;
    private static String IP;
    private static double minimumWalletBalance;
    private static double commission;

    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;

    public static void ConnectToBankServer() throws IOException {
        try {
            Socket socket = new Socket(IP, port);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new IOException("Exception while initiating connection:");
        }
    }

    public static void StartListeningOnInput() {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(inputStream.readUTF());
                } catch (IOException e) {
                    System.out.println("disconnected");
                    System.exit(0);
                }
            }
        }).start();
    }

    public static void SendMessage(String msg) throws IOException {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    public static void setBankClient(int port, String IP, double minimumWalletBalance,double commission){
        BankClient.port = port;
        BankClient.IP = IP;
        BankClient.minimumWalletBalance = minimumWalletBalance;
        BankClient.commission = commission;

    }

    public static double getMinimumWalletBalance() {
        return minimumWalletBalance;
    }

    public static double getCommission() {
        return commission;
    }
}
