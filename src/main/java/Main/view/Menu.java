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
    protected HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
    protected Menu parentMenu;
    public static Scanner scanner = new Scanner(System.in);
    public static GeneralController generalController = new GeneralController();
    public static ManagerController managerController = new ManagerController();
    public static SellerController sellerController = new SellerController();
    public static BuyerController buyerController = new BuyerController();
}
