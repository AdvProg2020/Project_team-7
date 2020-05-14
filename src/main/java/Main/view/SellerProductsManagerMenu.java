package Main.view;

public class SellerProductsManagerMenu extends Menu {
    public SellerProductsManagerMenu(Menu parentMenu) {
        super("Manage products", parentMenu);
        this.subMenus.put(1, viewProduct());
        this.subMenus.put(2, viewBuyers());
        this.subMenus.put(3, editProduct());
    }

    private Menu viewProduct() {
        return new Menu("View product", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a product Id or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    sellerController.viewSellerProductWithId(input);
                    this.run();
                }
            }
        };
    }

    private Menu viewBuyers() {
        return new Menu("View buyers", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a product Id or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        sellerController.viewBuyersOfProductWithId(input);
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    private Menu editProduct() {
        return null;
    }

    @Override
    public void run() throws Exception {
        System.out.println(sellerController.showSellerProducts());
        this.show();
        this.execute();
    }
}

//TODO complete edit menu
