package Main;

import Main.controller.BuyerController;
import Main.controller.GeneralController;
import Main.controller.ManagerController;
import Main.controller.SellerController;
import Main.view.MainMenu;
import Main.view.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
        try {
            currentMenu.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
