package Main.view;

import Main.controller.BuyerController;
import Main.controller.GeneralController;
import Main.controller.ManagerController;
import Main.controller.SellerController;
import Main.model.accounts.ManagerAccount;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private String name;
    protected HashMap<Integer, Menu> subMenus = new HashMap<>();
    protected Menu parentMenu;
    public static Scanner scanner;
    protected static GeneralController generalController;
    protected static ManagerController managerController;
    protected static SellerController sellerController;
    protected static BuyerController buyerController;

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public static void setGeneralController(GeneralController generalController) {
        Menu.generalController = generalController;
    }

    public static void setManagerController(ManagerController managerController) {
        Menu.managerController = managerController;
    }

    public static void setSellerController(SellerController sellerController) {
        Menu.sellerController = sellerController;
    }

    public static void setBuyerController(BuyerController buyerController) {
        Menu.buyerController = buyerController;
    }

    public void show() {
        System.out.println(this.name + ":");
        for (Integer menuNum : subMenus.keySet()) {
            System.out.println(menuNum + ". " + subMenus.get(menuNum).getName());
        }
        if (this.parentMenu != null)
            System.out.println((subMenus.size() + 1) + ". Back");
        else
            System.out.println((subMenus.size() + 1) + ". Exit");
    }

    public void execute() throws Exception {
        Menu nextMenu = null;
        String number = scanner.nextLine();
        if ((!number.matches("\\d+")) || Integer.parseInt(number) > subMenus.size() + 1) {
            System.out.println("Invalid input!");
            this.run();
        } else {
            int chosenMenu = Integer.parseInt(number);
            if (chosenMenu == subMenus.size() + 1) {
                if (this.parentMenu == null) {
                    System.out.println(GeneralController.writeData());
                    System.exit(1);
                } else
                    nextMenu = this.parentMenu;
            } else
                nextMenu = subMenus.get(chosenMenu);
            nextMenu.run();
        }
    }

    public void run() throws Exception {
        this.show();
        this.execute();
    }

    public String getName() {
        return name;
    }
}
