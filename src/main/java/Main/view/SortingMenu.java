package Main.view;

public class SortingMenu extends Menu {
    public SortingMenu(Menu parentMenu) {
        super("Sorting",parentMenu);
        this.subMenus.put(1, showAvailableSorts());
        this.subMenus.put(2, sort());
        this.subMenus.put(3, currentSort());
        this.subMenus.put(4, disableSort());
    }

    private Menu showAvailableSorts() {
        return new Menu("Show available sorts", this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter show available sorts or Back to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back")) {
                    try {
                        this.parentMenu.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                } else if (input.equalsIgnoreCase("show available sorts")){
                    if (this.parentMenu instanceof RequestManagerMenu)
                        System.out.println(generalController.showAvailableSorts("request"));
                    else if (this.parentMenu instanceof ProductsMenu || this.parentMenu instanceof InCartProductManagerMenu || this.parentMenu instanceof AllProductsManagerMenu || this.parentMenu instanceof SellerProductsManagerMenu)
                        System.out.println(generalController.showAvailableSorts("product"));
                    else if (this.parentMenu instanceof UsersManagerMenu)
                        System.out.println(generalController.showAvailableSorts("user"));
                }
                try {
                    this.run();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    this.run();
                }
            }
        };
    }

    private Menu sort() {
        return new Menu("Sort by", this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter sort or Back to return");
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
                } else if (input.equalsIgnoreCase("sort")){
                    System.out.println("Enter sort type: ");
                    String sortType = scanner.nextLine();
                    generalController.makeSort(sortType);
                }
                try {
                    this.run();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    this.run();
                }
            }
        };
    }

    private Menu currentSort() {
        return new Menu("Show current sort", this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter show current sort or Back to return");
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
                } else if (input.equalsIgnoreCase("show current sort")){
                    System.out.println(generalController.showCurrentSort());
                }
                try {
                    this.run();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    this.run();
                }
            }
        };
    }

    private Menu disableSort() {
        return new Menu("Disable sort", this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter disable sort or Back to return");
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
                } else if (input.equalsIgnoreCase("disable sort")){
                    System.out.println(generalController.disableSort());
                }
                try {
                    this.run();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    this.run();
                }
            }
        };
    }
}
