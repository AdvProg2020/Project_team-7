package Main.view;

public class ManagerPanelMenu extends Menu {
    public ManagerPanelMenu(Menu parentMenu) {
        super("manager Panel",parentMenu);
        this.subMenus.put(1, new PersonalInfoMenu(this));
        this.subMenus.put(2, new UsersManagerMenu(this));
        this.subMenus.put(3, new AllProductsManagerMenu(this));
        this.subMenus.put(4, createDiscountCode());
        this.subMenus.put(5, new DiscountCodeManagerMenu(this));
        this.subMenus.put(6, new RequestManagerMenu(this));
    }

    private Menu createDiscountCode() {
        return null;
    }
}
