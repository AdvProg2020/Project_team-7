package Main.view;

public class AllProductsManagerMenu extends Menu {
    public AllProductsManagerMenu(Menu parentMenu) {
        super("Manage all products", parentMenu);
        this.subMenus.put(1, removeProduct());
        this.subMenus.put(2, new SortingMenu(this));

    }

    private Menu removeProduct() {
        return new Menu("Remove product", this) {
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
                        managerController.removeProductWithId(input);
                        System.out.println("Product removed successfully.\n");
                        this.run();
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
        System.out.println(generalController.showAllProducts() + "\n");
        this.show();
        this.execute();
    }
}
