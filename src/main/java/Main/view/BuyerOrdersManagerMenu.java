package Main.view;

public class BuyerOrdersManagerMenu extends Menu {
    public BuyerOrdersManagerMenu(Menu parentMenu) {
        super("Manage orders", parentMenu);
        this.subMenus.put(1, showOrder());
        this.subMenus.put(2, rate());

    }

    private Menu showOrder() {
        return new Menu("Show order", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter order Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    System.out.println(buyerController.showOrderWithId(input) + "\n");
                    this.run();
                }
            }
        };
    }

    private Menu rate() {
        return new Menu("Rate", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter product Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    System.out.println("Enter your score from 1 to 5:");
                    String score = scanner.nextLine().trim();
                    try {
                        buyerController.rateProductWithId(input, score);
                        System.out.println("Rate registered successfully.\n");
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
        System.out.println(buyerController.viewBuyerOrders() + "\n");
        this.show();
        this.execute();
    }
}
