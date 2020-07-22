package Main.server.consoleViewOld;

public class UserPanelMenu extends Menu {
    public UserPanelMenu(Menu parentMenu) {
        super("User Panel Menu", parentMenu);
        this.subMenus.put(1, new SignInMenu(this));
        this.subMenus.put(2, logOut());
        this.subMenus.put(3, new UserMenu(this));
    }

    private Menu logOut() {
        return new Menu("Logout", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void execute() throws Exception {
                System.out.println("You have logged out successfully.\n");
                generalController.logout();
                this.parentMenu.run();
            }
        };
    }
}
