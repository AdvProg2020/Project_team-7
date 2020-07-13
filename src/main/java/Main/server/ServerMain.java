package Main.server;

import Main.client.graphicView.GraphicMain;
import Main.server.controller.BuyerController;
import Main.server.controller.GeneralController;
import Main.server.serverRequestProcessor.Server;
import Main.server.controller.ManagerController;
import Main.server.controller.SellerController;

public class ServerMain {
    public static Server server;
    public static GeneralController generalController = new GeneralController();
    public static BuyerController buyerController = new BuyerController();
    public static SellerController sellerController = new SellerController();
    public static ManagerController managerController = new ManagerController();

    public static void main(String[] args) {
        System.out.println(GeneralController.readDataAndSetStringRecordObjects());
        GraphicMain.generalController.initializeIDs();
        try {
            generalController.giveDiscountCodeToSpecialBuyers();
        } catch (Exception e) {
            e.printStackTrace();
        }


        server = Server.getServer();
        server.start();
    }
}