package Main.view;

public class PersonalInfoMenu extends Menu {
    public PersonalInfoMenu(Menu parentMenu) {
        super("View personal information",parentMenu);
        this.subMenus.put(1,edit());

    }

    private Menu edit() {
        return null;
    }

    @Override
    public void run(){

    }
}
