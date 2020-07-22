package Main.server.consoleViewOld;

import Main.server.controller.GeneralController;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;

public class UserMenu extends Menu {
    public UserMenu(Menu parentMenu) {
        super("User Menu", parentMenu);
        this.subMenus.put(1, new ManagerPanelMenu(this));
        this.subMenus.put(2, new SellerPanelMenu(this));
        this.subMenus.put(3, new BuyerPanelMenu(this));
    }

    @Override
    public void run() throws Exception {
        System.out.println("Press enter to continue or insert 'Back' to return:");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("back"))
            this.parentMenu.run();
        else if (input.equalsIgnoreCase("")) {
            if (GeneralController.currentUser == null) {
                System.out.println("You have not logged in yet!");
                SignInMenu signInMenu = new SignInMenu(this);
                signInMenu.run();
            } else if (GeneralController.currentUser instanceof ManagerAccount)
                subMenus.get(1).run();
            else if (GeneralController.currentUser instanceof SellerAccount)
                subMenus.get(2).run();
            else
                subMenus.get(3).run();
        } else {
            System.out.println("Invalid input!\n");
            this.run();
        }
    }
}
