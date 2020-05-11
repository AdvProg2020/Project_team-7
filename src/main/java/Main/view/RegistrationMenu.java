package Main.view;

public class RegistrationMenu extends Menu {
    public RegistrationMenu(Menu parentMenu) {
        super("Create account", parentMenu);
        this.subMenus.put(2, registerManager());
        this.subMenus.put(1, registerBuyer());
        this.subMenus.put(3, registerSeller());

    }

    @Override
    public void show() {
        System.out.println(this.getName() + ":");
        System.out.println("Enter type of user [manager|buyer|seller] or Back to return");
    }

    @Override
    public void execute() {
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("back"))
            this.parentMenu.run();
        else {
            System.out.println("Enter your username");
            String username = scanner.nextLine();
            //TODO Input password
            generalController.createAccount(input, username); //TODO might be changed
            if (input.equalsIgnoreCase("manager"))
                registerManager().run();
            else if (input.equalsIgnoreCase("buyer"))
                registerBuyer().run();
            else if (input.equalsIgnoreCase("seller"))
                registerSeller().run();
        }
    }

    private Menu registerManager() {
        return new Menu("Register manager", this) {
            @Override
            public void show() {

            }
        };
    }

    private Menu registerBuyer() {
        return null;
    }

    private Menu registerSeller() {
        return null;
    }
}

//TODO complete subMenus after controller
