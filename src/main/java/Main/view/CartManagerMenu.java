package Main.view;

public class CartManagerMenu extends Menu {
    public CartManagerMenu(Menu parentMenu) {
        super("View Cart", parentMenu);
        this.subMenus.put(1, new InCartProductManagerMenu(this));
        this.subMenus.put(2, showTotalPrice());
        this.subMenus.put(3, new PurchaseMenu(this));

    }

    private Menu showTotalPrice() {
        return new Menu("Show total price", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter 'Show' or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("show")) {
                    System.out.println(buyerController.showTotalCartPrice());
                    this.run();
                } else {
                    System.out.println("Invalid input!");
                    this.run();
                }
            }
        };
    }
}
