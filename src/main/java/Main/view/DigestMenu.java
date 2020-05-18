package Main.view;

import Main.controller.GeneralController;
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
            public void execute() throws Exception {
                if (GeneralController.currentUser == null || GeneralController.currentUser instanceof SellerAccount ||
                        GeneralController.currentUser instanceof ManagerAccount) {
                    System.out.println("You have not logged in yet!");
                    SignInMenu signInMenu = new SignInMenu(this);
                    signInMenu.run();
                } else {
                    System.out.println(generalController.addProductToCart());
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
                System.out.println("Enter seller's username or 'Back' to return:");
                System.out.println(generalController.showProductSellers());
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        generalController.selectSellerWithUsername(input);
                        System.out.println("Seller selected successfully.\n");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "\n");
                        this.run();
                    }

                }
            }
        };
    }

    @Override
    public void run() throws Exception {
        System.out.println(generalController.showProductDigest() + "\n");
        this.show();
        this.execute();
    }
}
