package Main.server.serverRequestProcessor;

import Main.server.controller.GeneralController;

import static Main.client.graphicView.GraphicMain.generalController;

public class ServerMain {
    public static void main(String[] args) {
        System.out.println(GeneralController.readDataAndSetStringRecordObjects());
        generalController.initializeIDs();
        try {
            generalController.giveDiscountCodeToSpecialBuyers();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Server server = Server.getServer();
        server.start();
    }
}
