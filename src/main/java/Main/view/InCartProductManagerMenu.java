package Main.view;

public class InCartProductManagerMenu extends Menu {
    public InCartProductManagerMenu(Menu parentMenu) {
        super("Manage products in cart", parentMenu);
        this.subMenus.put(1, showProducts());
        this.subMenus.put(2, viewProduct());
        this.subMenus.put(3, increaseProductNumber());
        this.subMenus.put(4, decreaseProductNumber());
        this.subMenus.put(5, new SortingMenu(this));

    }

    private Menu showProducts() {
        return new Menu("Show product", this) {
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
                    System.out.println(buyerController.showCartProducts());
                    this.run();
                } else {
                    System.out.println("Invalid input!");
                    this.run();
                }
            }
        };
    }

    private Menu viewProduct() {
        return new Menu("View product", this) {
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
                    try {
                        System.out.println(generalController.setCurrentProductWithId(input));
                        ProductPageMenu productPageMenu = new ProductPageMenu(this);
                        productPageMenu.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    private Menu increaseProductNumber() {
        return new Menu("Increase product number", this) {
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
                    try {
                        buyerController.increaseProductWithId(input);
                        System.out.println("Product number increased successfully.");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }

        };
    }

    private Menu decreaseProductNumber() {
        return new Menu("Decrease product number", this) {
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
                    try {
                        buyerController.decreaseProductWithId(input);
                        System.out.println("Product number decreased successfully.");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }

        };
    }
}
