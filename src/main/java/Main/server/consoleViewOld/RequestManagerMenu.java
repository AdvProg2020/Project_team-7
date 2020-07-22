package Main.server.consoleViewOld;

public class RequestManagerMenu extends Menu {
    public RequestManagerMenu(Menu parentMenu) {
        super("Manage requests", parentMenu);
        this.subMenus.put(1, viewRequestDetails());
        this.subMenus.put(2, acceptRequest());
        this.subMenus.put(3, declineRequest());
        this.subMenus.put(4, new SortingMenu(this));

    }

    private Menu viewRequestDetails() {
        return new Menu("View request details", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Insert request Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    System.out.println(managerController.showRequestDetailsWithId(input));
                    this.run();
                }
            }
        };
    }

    private Menu acceptRequest() {
        return new Menu("Accept request", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Insert request Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        managerController.acceptRequestWithId(input);
                        System.out.println("Request accepted successfully.\n");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "\n");
                        this.run();
                    }
                }
            }
        };
    }

    private Menu declineRequest() {
        return new Menu("Decline request", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Insert request Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        managerController.declineRequestWithId(input);
                        System.out.println("Request declines successfully.\n");
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
        System.out.println(managerController.showAllRequests() + "\n");
        this.show();
        this.execute();
    }
}
