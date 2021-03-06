package Main.server.consoleViewOld;

public class SortingMenu extends Menu {
    public SortingMenu(Menu parentMenu) {
        super("Sorting", parentMenu);
        this.subMenus.put(1, showAvailableSorts());
        this.subMenus.put(2, sort());
        this.subMenus.put(3, currentSort());
        this.subMenus.put(4, disableSort());
    }

    private Menu showAvailableSorts() {
        return new Menu("Show available sorts", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Press enter to continue or insert 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.run();
                } else if (input.equalsIgnoreCase("")) {
                    if (this.parentMenu.parentMenu instanceof RequestManagerMenu)
                        System.out.println(generalController.showAvailableSorts("request"));
                    else if (this.parentMenu.parentMenu instanceof ProductsMenu || this.parentMenu.parentMenu instanceof
                            InCartProductManagerMenu || this.parentMenu.parentMenu instanceof AllProductsManagerMenu
                            || this.parentMenu.parentMenu instanceof SellerProductsManagerMenu)
                        System.out.println(generalController.showAvailableSorts("product"));
                    else if (this.parentMenu.parentMenu instanceof UsersManagerMenu)
                        System.out.println(generalController.showAvailableSorts("user"));
                    this.run();
                } else {
                    System.out.println("Invalid input!");
                    this.run();
                }
            }
        };
    }

    private Menu sort() {
        return new Menu("Sort by", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Press enter to continue or insert 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.run();
                } else if (input.equalsIgnoreCase("")) {
                    System.out.println("Insert sort type:");
                    String sortType = scanner.nextLine().trim();
                    if (this.parentMenu.parentMenu instanceof RequestManagerMenu)
                        System.out.println(generalController.makeSort(sortType, "request"));
                    else if (this.parentMenu.parentMenu instanceof ProductsMenu || this.parentMenu.parentMenu instanceof
                            InCartProductManagerMenu || this.parentMenu.parentMenu instanceof AllProductsManagerMenu
                            || this.parentMenu.parentMenu instanceof SellerProductsManagerMenu)
                        System.out.println(generalController.makeSort(sortType, "product"));
                    else if (this.parentMenu.parentMenu instanceof UsersManagerMenu)
                        System.out.println(generalController.makeSort(sortType, "user"));
                    this.run();
                } else {
                    System.out.println("Invalid input!");
                    this.run();
                }
            }
        };
    }

    private Menu currentSort() {
        return new Menu("Show current sort", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Press enter to continue or insert to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.run();
                } else if (input.equalsIgnoreCase("")) {
                    if (this.parentMenu.parentMenu instanceof RequestManagerMenu)
                        System.out.println(generalController.showCurrentRequestSort());
                    else if (this.parentMenu.parentMenu instanceof ProductsMenu || this.parentMenu.parentMenu instanceof
                            InCartProductManagerMenu || this.parentMenu.parentMenu instanceof AllProductsManagerMenu
                            || this.parentMenu.parentMenu instanceof SellerProductsManagerMenu)
                        System.out.println(generalController.showCurrentProductSort());
                    else if (this.parentMenu.parentMenu instanceof UsersManagerMenu)
                        System.out.println(generalController.showCurrentUserSort());
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    private Menu disableSort() {
        return new Menu("Disable sort", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Press enter to continue or insert 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.run();
                } else if (input.equalsIgnoreCase("")) {
                    System.out.println(generalController.disableSort() + "\n");
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }
}
