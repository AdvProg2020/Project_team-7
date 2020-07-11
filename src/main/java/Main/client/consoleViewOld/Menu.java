package Main.client.consoleViewOld;

import Main.server.controller.BuyerController;
import Main.server.controller.GeneralController;
import Main.server.controller.ManagerController;
import Main.server.controller.SellerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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

    public static void loadMenuScene(String fxmlName,String menuTitle){
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/resources/"+fxmlName+".fxml").toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //GraphicMain.stage.setTitle(menuTitle);
        //GraphicMain.stage.setScene(new Scene(root));
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

        System.out.println(new Date());
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
        String number = scanner.nextLine().trim();
        if ((!number.matches("\\d+")) || Integer.parseInt(number) > subMenus.size() + 1) {
            System.out.println("Invalid input!\n");
            this.run();
        } else {
            int chosenMenu = Integer.parseInt(number);
            if (chosenMenu == subMenus.size() + 1) {
                if (this.parentMenu == null) {
                    System.out.println(GeneralController.writeDataAndGetObjectStringRecords());
                    System.exit(1);
                } else
                    nextMenu = this.parentMenu;
            } else
                nextMenu = subMenus.get(chosenMenu);
            nextMenu.run();
        }
    }

//    public void run(String fxmlName,String menuTitle) throws Exception {
//        this.show(fxmlName,menuTitle);
//        this.execute();
//    }


    //before
    public void run() throws Exception{
        this.show();
        this.execute();
    }

    public String getName() {
        return name;
    }
}
