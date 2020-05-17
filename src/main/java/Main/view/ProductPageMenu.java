package Main.view;

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
                System.out.println("Enter 'Show' or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("show")) {
                    System.out.println(generalController.showProductAttributes());
                    this.run();
                } else {
                    System.out.println("Invalid input!");
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
                System.out.println(generalController.showAllProducts());
                System.out.println("Enter another product Id or 'Back' to return:");

            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        System.out.println(generalController.compareProductWithProductWithId(input));
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
