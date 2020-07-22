package Main.server.consoleViewOld;

import Main.server.controller.BuyerController;
import Main.server.controller.GeneralController;
import Main.server.controller.ManagerController;
import Main.server.controller.SellerController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        GeneralController generalController = new GeneralController();
        SellerController sellerController = new SellerController();
        BuyerController buyerController = new BuyerController();
        ManagerController managerController = new ManagerController();
        Menu.setScanner(new Scanner(System.in));
        Menu.setBuyerController(buyerController);
        Menu.setGeneralController(generalController);
        Menu.setManagerController(managerController);
        Menu.setSellerController(sellerController);
        Menu currentMenu = new MainMenu();
        System.out.println(GeneralController.readDataAndSetStringRecordObjects());
        generalController.initializeIDs();
        generalController.giveDiscountCodeToSpecialBuyers();
        currentMenu.run();
    }
}
