package Main.bankServer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BankAPI {
    public static int port= 1676;
    public static final String IP = "127.0.0.1";

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

    public static String getResponseFromBankServer(String message){
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println(getResponseFromBankServer(scanner.nextLine()));
        }
    }
}
