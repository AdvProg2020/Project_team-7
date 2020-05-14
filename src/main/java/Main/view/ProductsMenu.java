package Main.view;

import Main.controller.GeneralController;

public class ProductsMenu extends Menu {
    public ProductsMenu(Menu parentMenu) {
        super("Products", parentMenu);
        this.subMenus.put(1, showCategories());
        this.subMenus.put(2, new FilteringMenu(this));
        this.subMenus.put(3, new SortingMenu(this));
        this.subMenus.put(4, showProducts());
    }

    private Menu showCategories() {
        return new Menu("Show Categories", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Back to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    try {
                        this.parentMenu.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
                else {
                    System.out.println(generalController.showAllCategories());
                    this.run();
                }
            }
        };
    }

    private Menu showProducts() {
        return new Menu("Show Products", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Back to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    try {
                        this.parentMenu.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
                else {
                    System.out.println(generalController.showFilteredAndSortedProducts());
                    this.run();
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