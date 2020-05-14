package Main.view;

public class FilteringMenu extends Menu {
    public FilteringMenu(Menu parentMenu) {
        super("Filtering", parentMenu);
        this.subMenus.put(1, showAvailableFilters());
        this.subMenus.put(2, filter());
        this.subMenus.put(3, filter());
        this.subMenus.put(4, disableFilter());
    }

    private Menu showAvailableFilters() {
        return new Menu("Show available filters", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Show available filters or Back to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.run();
                } else if (input.equalsIgnoreCase("show available filters")){
                    System.out.println("Available filters are: ");
                    System.out.println(generalController.showAvailableFilters());
                    this.run();
                }
            }
        };
    }

    private Menu filter() {
        return new Menu("Filter by", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter one of the available filters or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.run();
                } else {
                    System.out.println("Enter desired value for your selected filter\n(If you chose price, you can " +
                            "separate start and end of price range with space,comma or dash)");
                    String filterInput = scanner.nextLine().trim();
                    System.out.println(generalController.createFilter(input, filterInput));
                    this.run();
                }
            }
        };
    }

    private Menu currentFilters() {
        return new Menu("Show current filters", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter show current filters or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.run();
                } else if (input.equalsIgnoreCase("show current filters")) {
                    System.out.println(generalController.showCurrentFilters());
                    this.run();
                }
            }
        };
    }

    private Menu disableFilter() {
        return new Menu("Disable filter", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter disable filter or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    this.parentMenu.run();
                } else if (input.equalsIgnoreCase("disable filter")) {
                    System.out.println("Enter the filter type you want to disable");
                    String disablingFilter = scanner.nextLine();
                    System.out.println(generalController.disableFilter(disablingFilter));
                    this.run();
                }
            }
        };
    }
}
