package Main.consoleViewOld;

public class MainMenu extends Menu {
    public MainMenu() {
        super("Main Menu", null);
        this.subMenus.put(1, new UserPanelMenu(this));
        this.subMenus.put(2, new OffMenu(this));
        this.subMenus.put(3, new ProductsMenu(this));
    }

    @Override
    public void run() throws Exception {
        System.out.println("Welcome to MFM online shop :))");
        this.show();
        this.execute();
    }
}
