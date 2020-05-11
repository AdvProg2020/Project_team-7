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
                System.out.println("Enter Back to return");
            }

            @Override
            public void execute() {
                System.out.println(generalController.showProductAttributes());
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
            }
        };
    }

    private Menu compareProduct() {
        return new Menu("Compare product", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter another product's Id or Back to return");
                //TODO list of products with ids
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    System.out.println(generalController.compareProductWithProductWithId(input));
                    this.run();
                }
            }
        };
    }
}
