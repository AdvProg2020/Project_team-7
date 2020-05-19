package Main.view;

public class PurchaseMenu extends Menu {
    public PurchaseMenu(Menu parentMenu) {
        super("Purchase Menu", parentMenu);
        this.subMenus.put(1, purchase());


    }

    private Menu purchase() {
        return new Menu("Purchase", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Press enter to continue or insert 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("")) {
                    buyerController.setReceiverInformation(getReceiverInformation());
                    System.out.println("If you have a discount code, insert code, otherwise insert continue:");
                    String input2 = scanner.nextLine().trim();
                    if (input2.equalsIgnoreCase("continue")) {
                        System.out.println(buyerController.showPurchaseInfo());
                        System.out.println("Do you want to pay? Please insert yes or no:");
                        String answer = scanner.nextLine().trim();
                        if (answer.equalsIgnoreCase("no"))
                            this.run();
                        else {
                            try {
                                System.out.println(buyerController.finalizePurchaseAndPay());
                                this.run();
                            } catch (Exception e) {
                                System.out.println(e.getMessage() + "\n");
                                this.run();
                            }
                        }
                    } else {
                        try {
                            buyerController.setPurchaseDiscountCode(input2);
                            System.out.println(buyerController.showPurchaseInfo());
                            System.out.println("Do you want to pay? Please insert yes or no:");
                            String answer = scanner.nextLine().trim();
                            if (answer.equalsIgnoreCase("no"))
                                this.run();
                            else {
                                buyerController.finalizePurchaseAndPay();
                                System.out.println("Purchase finished successfully.\n");
                                this.run();
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + "\n");
                            this.run();
                        }
                    }
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    public String getReceiverInformation() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("Receive information:\n\tFirst name:");
        String firstName = scanner.nextLine().trim();
        stringBuilder.append(firstName + "\n\t");
        System.out.println("Last name:");
        String lastName = scanner.nextLine().trim();
        stringBuilder.append(lastName + "\n\t");
        System.out.println("Email:");
        String email = scanner.nextLine().trim();
        stringBuilder.append(email + "\n\t");
        System.out.println("Address:");
        String address = scanner.nextLine().trim();
        stringBuilder.append(address + "\n\t");
        return stringBuilder.toString();
    }
}
