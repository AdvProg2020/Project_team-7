package Main.view;

public class UserMenu extends Menu {
    public UserMenu(Menu parentMenu) {
        super("User Menu", parentMenu);
        this.subMenus.put(1, new ManagerPanelMenu(this));
        this.subMenus.put(2, new SellerPanelMenu(this));
        this.subMenus.put(3, new BuyerPanelMenu(this));
    }
}
