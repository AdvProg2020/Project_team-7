package Main.server;

import java.io.*;
import java.net.Socket;

public class BankClient {
    private static int port;
    private static String IP;

    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;

    public static void ConnectToBankServer(Socket socket) throws IOException {
        try {
            outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            throw new IOException("Exception while initiating connection:");
        }
    }

    public static String sendMessage(String message) throws IOException {
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
            return inputStream.readUTF();
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    public static String getResponseFromBankServer(String message) {
        try {
            Socket socket = new Socket(IP, port);
            ConnectToBankServer(socket);
            String response = sendMessage(message);
            sendMessage("exit");
            socket.close();
            inputStream.close();
            outputStream.close();
            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }
    }

    public static void setPort(int port) {
        BankClient.port = port;
    }

    public static void setIP(String IP) {
        BankClient.IP = IP;
    }
}
