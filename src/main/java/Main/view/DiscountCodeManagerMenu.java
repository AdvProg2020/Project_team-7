package Main.view;

import Main.model.requests.EditDiscountCode;

public class DiscountCodeManagerMenu extends Menu {
    public DiscountCodeManagerMenu(Menu parentMenu) {
        super("Manage discount codes", parentMenu);
        this.subMenus.put(1, viewDiscountCode());
        this.subMenus.put(2, editDiscountCode());
        this.subMenus.put(3, removeDiscountCode());

    }

    private Menu viewDiscountCode() {
        return new Menu("View discount code", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a code or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    System.out.println(managerController.viewDiscountCodeWithCode(input));
                    this.run();
                }
            }
        };
    }

    private Menu editDiscountCode() {
        return new Menu("Edit discount code", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a code or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        EditDiscountCode editDiscountCode = managerController.getDiscountCodeToEdit(input);
                        getFieldsToEdit(editDiscountCode);
                        managerController.submitDiscountCodeEdits(editDiscountCode);
                        System.out.println("Discount code edited successfully.");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }

                }
            }
        };
    }

    public void getFieldsToEdit(EditDiscountCode editDiscountCode) {
        System.out.println("Fields you are allowed to edit:(You can insert any field you want to edit unless you insert" +
                " Submit.)\nStart date\nEnd date\nPercent\nMaximum amount\nMaximum number of use\nBuyer username to be added" +
                "\nBuyer username to be removed");
        String input = new String();
        while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("submit")) {
            System.out.println("Enter new content:");
            String newContent = scanner.nextLine();
            if (input.equalsIgnoreCase("start date"))
                editDiscountCode.setStartDate(newContent);
            else if (input.equalsIgnoreCase("end date"))
                editDiscountCode.setEndDate(newContent);
            else if (input.equalsIgnoreCase("percent"))
                editDiscountCode.setPercent(newContent);
            else if (input.equalsIgnoreCase("maximum amount"))
                editDiscountCode.setMaxAmount(newContent);
            else if (input.equalsIgnoreCase("maximum number of use"))
                editDiscountCode.setMaxNumberOfUse(newContent);
            else if (input.equalsIgnoreCase("buyer username to be added"))
                editDiscountCode.addUserToBeAdded(newContent);
            else if (input.equalsIgnoreCase("buyer username to be removed"))
                editDiscountCode.addUserToBeRemoved(newContent);
            else
                System.out.println("there is no field with this name!");
        }
    }

    private Menu removeDiscountCode() {
        return new Menu("Remove discount code", parentMenu) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a code or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        managerController.removeDiscountCodeWithCode(input);
                        System.out.println("Discount code removed successfully.");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    @Override
    public void run() throws Exception {
        System.out.println(managerController.showAllDiscountCodes());
        this.show();
        this.execute();
    }
}
