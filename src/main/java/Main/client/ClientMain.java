package Main.client;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.Client;

import java.util.Scanner;

public class ClientMain {
    public static Client client;

    public static void main(String[] args) {
        while (true) {
            System.out.println("enter server IP");
            Scanner scanner = new Scanner(System.in);
            String IP = scanner.nextLine();
            System.out.println("enter server port");
            String port = scanner.nextLine();

            try {
                client = Client.getClient(IP,port);
                break;
            } catch (Exception e) {
                System.err.println("connection failed :(");
                System.err.println("IP or port might be invalid invalid");
                main(null);
            }
        }
        GraphicMain.main(null);
    }
}
