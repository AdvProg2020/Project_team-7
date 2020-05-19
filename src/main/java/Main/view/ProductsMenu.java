package Main.view;

import Main.controller.GeneralController;

public class ProductsMenu extends Menu {
    public ProductsMenu(Menu parentMenu) {
        super("Products", parentMenu);
        this.subMenus.put(1, showCategories());
        this.subMenus.put(2, new FilteringMenu(this));
        this.subMenus.put(3, new SortingMenu(this));
        this.subMenus.put(4, showFilteredAndSortedProducts());
        this.subMenus.put(5, showProduct());
    }

    private Menu showCategories() {
        return new Menu("Show categories", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Press enter to continue or insert 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("")) {
                    System.out.println(generalController.showAllCategories() + "\n");
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    private Menu showFilteredAndSortedProducts() {
        return new Menu("Show filtered and sorted products", this) {
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
                    System.out.println(generalController.showFilteredAndSortedProducts() + "\n");
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
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
                        System.out.println(e.getMessage() + "\n");
                        this.run();
                    }
                }
            }
        };
    }

    /*public void run() throws Exception {
        System.out.println(generalController.showAllProducts());
        this.show();
        this.execute();
    }*/
}