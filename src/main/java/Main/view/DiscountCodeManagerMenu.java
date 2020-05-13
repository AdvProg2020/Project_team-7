package Main.view;

public class DiscountCodeManagerMenu extends Menu {
    public DiscountCodeManagerMenu(Menu parentMenu) {
        super("Manage discount codes",parentMenu);
        this.subMenus.put(1, viewDiscountCode());
        this.subMenus.put(2, editDiscountCode());
        this.subMenus.put(3, removeDiscountCode());

    }

    private Menu viewDiscountCode() {
        return new Menu("View discount code",this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter a code or Back to return:");
            }
            @Override
            public void execute() throws Exception{
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else{
                    System.out.println(managerController.viewDiscountCodeWithCode(input));
                    this.run();
                }
            }
        };
    }

    private Menu editDiscountCode() {
        return null;
    }

    private Menu removeDiscountCode() {
        return new Menu("Remove discount code",parentMenu){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter a code or Back to return:");
            }
            @Override
            public void execute() throws Exception{
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else{
                    try {
                        managerController.removeDiscountCodeWithCode(input);
                        System.out.println("Discount code removed successfully.");
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
    public void run() throws Exception{
        System.out.println(managerController.showAllDiscountCodes());
        this.show();
        this.execute();
    }
}

//TODO complete editDiscountCode
