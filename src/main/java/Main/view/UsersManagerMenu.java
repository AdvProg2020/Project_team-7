package Main.view;

public class UsersManagerMenu extends Menu {
    public UsersManagerMenu(Menu parentMenu) {
        super("Manage users",parentMenu);
        this.subMenus.put(1,view());
        this.subMenus.put(2,deleteUser());
        this.subMenus.put(3,createManagerProfile());

    }

    private Menu view() {
        return null;
    }

    private Menu deleteUser() {
        return null;
    }

    private Menu createManagerProfile() {
        return null;
    }

    @Override
    public void run(){

    }
}
