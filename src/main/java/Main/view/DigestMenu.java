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
                    System.out.println("You have not logged in yet! If you have already had an account, enter Login," +
                            " otherwise enter Register");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("login")) {
                        LoginMenu loginMenu = new LoginMenu(this);
                        loginMenu.run();
                    } else if (input.equalsIgnoreCase("register")) {
                        RegistrationMenu registrationMenu = new RegistrationMenu(this);
                        registrationMenu.run();
                    }

                } else {
                    System.out.println("The product added to your cart successfully!");
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
                    generalController.selectSellerWithName(input);
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
