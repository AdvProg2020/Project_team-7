package Main.view;

import Main.controller.GeneralController;

public class ProductsMenu extends Menu {
    public ProductsMenu(Menu parentMenu) {
        super("Products", parentMenu);
        this.subMenus.put(1, showCategories());
        this.subMenus.put(2, new FilteringMenu(this));
        this.subMenus.put(3, new SortingMenu(this));
        this.subMenus.put(4, showProducts());
        this.subMenus.put(5, showProduct());
        this.subMenus.put(6, new SortingMenu(this));
    }

    private Menu showCategories() {
        return new Menu("Show categories", this) {
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
                    System.out.println(generalController.showAllCategories());
                    this.run();
                } else {
                    System.out.println("Invalid input!");
                    this.run();
                }
            }
        };
    }

    private Menu showProducts() {
        return new Menu("Show products", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter 'Show' or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.run();
                } else if (input.equalsIgnoreCase("show")) {
                    System.out.println(generalController.showFilteredAndSortedProducts());
                    this.run();
                } else {
                    System.out.println("Invalid input!");
                    this.run();
                }
            }
        };
    }

    private Menu showProduct() {
        return new Menu("Show product", this) {
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

    public void run() throws Exception {
        System.out.println(generalController.showAllProducts());
        this.show();
        this.execute();
    }
}