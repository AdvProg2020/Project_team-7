package Main.view;

public class BuyerPanelMenu extends Menu {
    public BuyerPanelMenu(Menu parentMenu) {
        super("Buyer panel", parentMenu);
        this.subMenus.put(1, new PersonalInfoMenu(this));
        this.subMenus.put(2, new CartManagerMenu(this));

    }

    private Menu viewBalance() {
        return null;
    }

    private Menu viewDiscountCode() {
        return null;
    }
}
