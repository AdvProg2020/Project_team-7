package Main.view;

import Main.controller.GeneralController;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.accounts.SellerAccount;

public class PersonalInfoMenu extends Menu {
    public PersonalInfoMenu(Menu parentMenu) {
        super("View personal information", parentMenu);
        this.subMenus.put(1, edit());

    }

    private Menu edit() {
        return new Menu("edit", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Fields you are allowed to edit:");
                System.out.println(getAllowedFieldsToEdit());
                System.out.println("Enter a field or Back to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    System.out.println("Enter the new content:");
                    String newContent = scanner.nextLine();
                    System.out.println(generalController.editPersonalInfo(input, newContent));
                    this.run();
                }
            }
        };
    }

    public String getAllowedFieldsToEdit() {
        StringBuilder list = new StringBuilder();
        list.append("first name\nlast name\nemail\nphone number\npassword");
        if (GeneralController.currentUser instanceof BuyerAccount)
            list.append("\nbalance");
        else if (GeneralController.currentUser instanceof SellerAccount)
            list.append("\ncompany name\ncompany extra information");
        return list.toString();
    }

    @Override
    public void run() throws Exception {
        System.out.println(generalController.viewPersonalInfo());
        this.show();
        this.execute();
    }
}
