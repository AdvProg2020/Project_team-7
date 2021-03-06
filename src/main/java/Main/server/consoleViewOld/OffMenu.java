package Main.server.consoleViewOld;

public class OffMenu extends Menu {
    public OffMenu(Menu parentMenu) {
        super("Off Menu", parentMenu);
        this.subMenus.put(1, new FilteringMenu(this));
        this.subMenus.put(2, new SortingMenu(this));
        this.subMenus.put(3, showProduct());
        this.subMenus.put(4, new UserPanelMenu(this));
    }

    private Menu showProduct() {
        return new Menu("Show product", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Insert product Id or 'Back' to return:");
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

    @Override
    public void run() throws Exception {
        System.out.println(generalController.showAllOffProducts() + "\n");
        this.show();
        this.execute();
    }
}
