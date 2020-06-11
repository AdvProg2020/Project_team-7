package Main.consoleViewOld;

public class ProductPageMenu extends Menu {
    public ProductPageMenu(Menu parentMenu) {
        super("Product Page Menu", parentMenu);
        this.subMenus.put(1, new DigestMenu(this));
        this.subMenus.put(2, showAttributes());
        this.subMenus.put(3, compareProduct());
        this.subMenus.put(4, new CommentMenu(this));
        this.subMenus.put(5, new UserPanelMenu(this));

    }

    private Menu showAttributes() {
        return new Menu("Show attributes", this) {
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
                    System.out.println(generalController.showProductAttributes() + "\n");
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    private Menu compareProduct() {
        return new Menu("Compare product", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println(generalController.showSummaryOfProducts());
                System.out.println("Insert another product Id or 'Back' to return:");

            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        System.out.println(generalController.compareProductWithProductWithId(input) + "\n");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "\n");
                        this.run();
                    }
                }
            }
        };
    }
}
