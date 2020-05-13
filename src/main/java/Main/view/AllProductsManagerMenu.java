package Main.view;

public class AllProductsManagerMenu extends Menu {
    public AllProductsManagerMenu(Menu parentMenu) {
        super("Manage all products",parentMenu);
        this.subMenus.put(1,removeProduct());

    }

    private Menu removeProduct() {
        return new Menu("Remove product",this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter product Id or Back to return:");
            }
            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else{
                    try{
                        managerController.removeProductWithId(input);
                        System.out.println("Product removed successfully.");
                        this.run();
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    @Override
    public void run() throws Exception {
        System.out.println(generalController.showAllProducts());
        this.show();
        this.execute();
    }
}
