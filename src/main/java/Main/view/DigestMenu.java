package Main.view;

import Main.controller.GeneralController;
import Main.controller.ManagerController;
import Main.model.accounts.ManagerAccount;
import Main.model.accounts.SellerAccount;

public class DigestMenu extends Menu {
    public DigestMenu(Menu parentMenu) {
        super("Digest", parentMenu);
        this.subMenus.put(1, addToCart());
        this.subMenus.put(2, selectSeller());
        this.subMenus.put(3, new UserPanelMenu(this));
    }

    private Menu addToCart() {
        return new Menu("Add to cart", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void execute() {
                if (GeneralController.currentUser == null || GeneralController.currentUser instanceof SellerAccount ||
                        GeneralController.currentUser instanceof ManagerAccount) {
                    System.out.println("You have not logged in yet! ");
                    UserPanelMenu userPanelMenu = new UserPanelMenu(this);
                    userPanelMenu.run();
                } else {
                    generalController.addProductToCart();
                }
                this.parentMenu.run();
            }
        };
    }

    private Menu selectSeller() {
        return new Menu("Select seller", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter seller's username or Back to return");
                //TODO list of sellers
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    generalController.selectSellerWithId(input);
                    this.run();
                }
            }
        };
    }

    @Override
    public void run() {
        System.out.println(generalController.showProductDigest());
        this.show();
        this.execute();
    }
}
