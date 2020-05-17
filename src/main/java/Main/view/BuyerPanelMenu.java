package Main.view;

public class BuyerPanelMenu extends Menu {
    public BuyerPanelMenu(Menu parentMenu) {
        super("Buyer panel", parentMenu);
        this.subMenus.put(1, new PersonalInfoMenu(this));
        this.subMenus.put(2, new CartManagerMenu(this));
        this.subMenus.put(3, new BuyerOrdersManagerMenu(this));
        this.subMenus.put(4, viewBalance());
        this.subMenus.put(5, viewDiscountCode());

    }

    private Menu viewBalance() {
        return new Menu("View balance", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter 'View' or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("view")) {
                    System.out.println(buyerController.viewBuyerBalance() + "\n");
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    private Menu viewDiscountCode() {
        return new Menu("View discount codes", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter 'View' or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("view")) {
                    System.out.println(buyerController.viewBuyerDiscountCodes() + "\n");
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }
}
