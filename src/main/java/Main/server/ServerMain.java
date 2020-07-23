package Main.server;

import Main.client.requestBuilder.Client;
import Main.server.controller.BuyerController;
import Main.server.controller.GeneralController;
import Main.server.controller.ManagerController;
import Main.server.controller.SellerController;
import Main.server.model.ShopFinance;
import Main.server.serverRequestProcessor.Server;

import java.util.Date;
import java.util.Scanner;

public class ServerMain {
    public static Server server;
    public static GeneralController generalController = new GeneralController();
    public static BuyerController buyerController = new BuyerController();
    public static SellerController sellerController = new SellerController();
    public static ManagerController managerController = new ManagerController();

    public static void main(String[] args) {
        System.out.println(GeneralController.readDataAndSetStringRecordObjects());
        ServerMain.generalController.initializeIDs();

//        while (true) {
//            System.out.println("enter server IP");
//            Scanner scanner = new Scanner(System.in);
//            String IP = scanner.nextLine();
//            System.out.println("enter server port");
//            String port = scanner.nextLine();
//            try{
//                Integer.parseInt(port);
//            }catch (Exception e){
//                System.err.println("connection to bank failed :(");
//                System.err.println("IP or port might be invalid invalid");
//                main(null);
//            }
//            BankClient.setIP(IP);
//            BankClient.setPort(Integer.parseInt(port));
//
//            if (BankClient.getResponseFromBankServer("hi").equals("failure")) {
//                System.err.println("connection to bank failed :(");
//                System.err.println("IP or port might be invalid invalid");
//                main(null);
//            } else {
//                break;
//            }
//        }

        try {
            generalController.giveDiscountCodeToSpecialBuyers();
        } catch (Exception e) {
            e.printStackTrace();
        }


        server = Server.getServer();
        server.start();
    }
}