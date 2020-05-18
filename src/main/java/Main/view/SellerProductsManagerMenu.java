package Main.view;

import Main.model.requests.EditProductRequest;

public class SellerProductsManagerMenu extends Menu {
    public SellerProductsManagerMenu(Menu parentMenu) {
        super("Manage products", parentMenu);
        this.subMenus.put(1, viewProduct());
        this.subMenus.put(2, viewBuyers());
        this.subMenus.put(3, editProduct());
        this.subMenus.put(4, new SortingMenu(this));
    }

    private Menu viewProduct() {
        return new Menu("View product", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter product Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    System.out.println(sellerController.viewSellerProductWithId(input) + "\n");
                    this.run();
                }
            }
        };
    }

    private Menu viewBuyers() {
        return new Menu("View buyers", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter product Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        System.out.println(sellerController.viewBuyersOfProductWithId(input) + "\n");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "\n");
                        this.run();
                    }
                }
            }
        };
    }

    private Menu editProduct() {
        return new Menu("Edit product", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter an Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        EditProductRequest editProductRequest = sellerController.getProductToEdit(input);
                        getFieldsToEdit(editProductRequest);
                        sellerController.submitProductEdits(editProductRequest);
                        System.out.println("Product edited successfully.\n");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "\n");
                        this.run();
                    }
                }
            }
        };
    }

    public void getFieldsToEdit(EditProductRequest editProductRequest) {
        System.out.println("Fields you are allowed to edit:(You can insert any field you want to edit unless you insert" +
                " Submit.)\nName\nBrand\nAvailability\nDescription\nPrice\nOff Id (You can insert off Id or 'delete' to" +
                " remove off for product)");
        String input;
        while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("submit")) {
            System.out.println("Enter the content:");
            String newContent = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("name")) {
                editProductRequest.addEditedFieldTitle("name");
                editProductRequest.setName(newContent);
            } else if (input.equalsIgnoreCase("brand")) {
                editProductRequest.addEditedFieldTitle("brand");
                editProductRequest.setBrand(newContent);
            } else if (input.equalsIgnoreCase("availability")) {
                editProductRequest.addEditedFieldTitle("availability");
                editProductRequest.setAvailability(newContent);
            } else if (input.equalsIgnoreCase("description")) {
                editProductRequest.addEditedFieldTitle("description");
                editProductRequest.setDescription(newContent);
            } else if (input.equalsIgnoreCase("price")) {
                editProductRequest.addEditedFieldTitle("price");
                editProductRequest.setPrice(newContent);
            } else if (input.equalsIgnoreCase("off id")) {
                editProductRequest.addEditedFieldTitle("off");
                editProductRequest.setOffID(newContent);
            } else
                System.out.println("There is no field with this name!");

        }
    }

    @Override
    public void run() throws Exception {
        System.out.println(sellerController.showSellerProducts());
        this.show();
        this.execute();
    }
}
