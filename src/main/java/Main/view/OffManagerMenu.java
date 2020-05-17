package Main.view;

import Main.model.requests.EditOffRequest;

import java.util.ArrayList;

public class OffManagerMenu extends Menu {
    public OffManagerMenu(Menu parentMenu) {
        super("Manage offs", parentMenu);
        this.subMenus.put(1, viewOff());
        this.subMenus.put(2, editOff());
        this.subMenus.put(3, addOff());
    }

    private Menu viewOff() {
        return new Menu("View off", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter off Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        System.out.println(sellerController.viewOffWithId(input));
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    private Menu editOff() {
        return new Menu("Edit off", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter off Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        EditOffRequest editOffRequest = sellerController.getOffToEdit(input);
                        getFieldsToEdit(editOffRequest);
                        sellerController.submitOffEdits(editOffRequest);
                        System.out.println("Off edited successfully.");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }

                }
            }
        };
    }

    public void getFieldsToEdit(EditOffRequest editOffRequest) {
        System.out.println("Fields you are allowed to edit:(You can insert any field you want to edit unless you insert" +
                " Submit.)\nStart date\nEnd date\nOff amount\nProduct Id to be added\nProduct Id to be removed");
        String input;
        while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("submit")) {
            System.out.println("Enter the content:");
            String newContent = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("start date")) {
                editOffRequest.addEditedFieldTitle("start date");
                editOffRequest.setStartDate(newContent);
            } else if (input.equalsIgnoreCase("end date")) {
                editOffRequest.addEditedFieldTitle("end date");
                editOffRequest.setEndDate(newContent);
            } else if (input.equalsIgnoreCase("off amount")) {
                editOffRequest.addEditedFieldTitle("off amount");
                editOffRequest.setOffAmount(newContent);
            } else if (input.equalsIgnoreCase("product id to be added")) {
                editOffRequest.addEditedFieldTitle("add product");
                editOffRequest.addProductIDToBeAdded(newContent);
            } else if (input.equalsIgnoreCase("product id to be removed")) {
                editOffRequest.addEditedFieldTitle("remove product");
                editOffRequest.addProductIDToBeRemoved(newContent);
            } else
                System.out.println("there is no field with this name!");
        }
    }

    private Menu addOff() {
        return new Menu("Create off", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter 'Continue' to create off or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("continue")) {
                    try {
                        ArrayList<String> offInfo = new ArrayList<>();
                        getOffInfo(offInfo);
                        ArrayList<String> productsList = new ArrayList<>();
                        getProductIdList(productsList);
                        sellerController.addOff(productsList, offInfo);
                        System.out.println("Off created successfully.");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                } else {
                    System.out.println("Invalid input!");
                    this.run();
                }
            }
        };
    }

    public void getOffInfo(ArrayList<String> offInfo) {
        System.out.println("Enter off information:\nStart date: (The input date should be in " +
                "<yyyy/MM/dd HH:mm:ss>format)");
        String startDate = scanner.nextLine().trim();
        offInfo.add(startDate);
        System.out.println("End date:");
        String endDate = scanner.nextLine().trim();
        offInfo.add(endDate);
        System.out.println("Off amount:");
        String percent = scanner.nextLine().trim();
        offInfo.add(percent);
    }

    public void getProductIdList(ArrayList<String> productsList) {
        System.out.println("Number of products having this off:");
        int numberOfProducts = scanner.nextInt();
        System.out.println();
        for (int i = 0; i < numberOfProducts; i++) {
            String productId = scanner.nextLine().trim();
            productsList.add(productId);
        }
    }

    @Override
    public void run() throws Exception {
        System.out.println(sellerController.viewSellerOffs());
        this.show();
        this.execute();
    }
}
