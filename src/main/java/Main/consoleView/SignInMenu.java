package Main.consoleView;

public class SignInMenu extends Menu {
    public SignInMenu(Menu parentMenu) {
        super("Sign-in Menu", parentMenu);
        this.subMenus.put(1, new RegistrationMenu(this));
        this.subMenus.put(2, new LoginMenu(this));
    }
}
