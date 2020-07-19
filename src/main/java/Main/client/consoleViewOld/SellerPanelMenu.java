package Main.client.consoleViewOld;

import Main.server.model.Category;
import Main.server.model.exceptions.CreateProductException;

import java.util.ArrayList;

public class SellerPanelMenu extends Menu {
    public SellerPanelMenu(Menu parentMenu) {
        super("Seller panel", parentMenu);
        this.subMenus.put(1, new PersonalInfoMenu(this));
        this.subMenus.put(2, viewCompanyInfo());
        this.subMenus.put(3, viewSalesHistory());
        this.subMenus.put(4, new SellerProductsManagerMenu(this));
        this.subMenus.put(5, addProduct());
        this.subMenus.put(6, removeProduct());
        this.subMenus.put(7, showCategories());
        this.subMenus.put(8, new OffManagerMenu(this));
        this.subMenus.put(9, viewBalance());

    }

    private Menu viewCompanyInfo() {
        return new Menu("View company information", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter 'Continue' to view information or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("continue")) {
                    System.out.println(sellerController.viewCompanyInformation(null));
                    this.run();
                } else {
                    System.out.println("Invalid input!");
                    this.run();
                }
            }
        };
    }

    private Menu viewSalesHistory() {
        return new Menu("View sales history", this) {
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
                    System.out.println(sellerController.viewSalesHistory() + "\n");
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    private Menu addProduct() {
        return new Menu("Add product", this) {
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
                    try {
                        ArrayList<String> productInfo = new ArrayList<>();
                        getFieldsToCreateProduct(productInfo);
                        sellerController.addProduct(productInfo, null);
                        System.out.println("Product created successfully.\n");
                        this.run();
                    } catch (CreateProductException.InvalidProductInputInfo e) {
                        System.out.println(e.getMessage() + "\n");
                        this.run();
                    } catch (CreateProductException.GetCategoryFromUser e) {
                        ArrayList specialFeatures = new ArrayList();
                        getSpecialFeatures(specialFeatures, e.getCategory());
                        sellerController.setSpecialFeatures(e.getProduct().getProductId(), specialFeatures);
                        System.out.println("Product created successfully.\n");
                        this.run();

                    }
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    public void getFieldsToCreateProduct(ArrayList<String> productInfo) {
        System.out.println("Name:");
        String name = scanner.nextLine().trim();
        productInfo.add(name);
        System.out.println("Brand:");
        String brand = scanner.nextLine().trim();
        productInfo.add(brand);
        System.out.println("Availability:");
        String availability = scanner.nextLine().trim();
        productInfo.add(availability);
        System.out.println("Description:");
        String description = scanner.nextLine().trim();
        productInfo.add(description);
        System.out.println("Price:");
        String price = scanner.nextLine().trim();
        productInfo.add(price);
        System.out.println("Do you want to allocate a category for this product? please insert yes or no:");
        String beingInACategory = scanner.nextLine().trim();
        productInfo.add(beingInACategory);
        System.out.println("If yes, Insert category name, otherwise just ignore this part:");
        String categoryName = scanner.nextLine().trim();
        if (beingInACategory.equalsIgnoreCase("no"))
            categoryName = "-";
        productInfo.add(categoryName);

    }

    public void getSpecialFeatures(ArrayList<String> specialFeatures, Category category) {
        for (String feature : category.getSpecialFeatures()) {
            System.out.println(feature + ":");
            String input = scanner.nextLine().trim();
            specialFeatures.add(input);
        }

    }

    private Menu removeProduct() {
        return new Menu("Remove product", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Insert product Id or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        sellerController.removeProductWithID(input,null);
                        System.out.println("Product removed successfully.\n");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "\n");
                        this.run();
                    }
                }
            }
        };
    }

    private Menu showCategories() {
        return new Menu("Show categories", this) {
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
                    System.out.println(generalController.showAllCategories() + "\n");
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    private Menu viewBalance() {
        return new Menu("View Balance", this) {
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
                    System.out.println(sellerController.viewSellerBalance(null) + "\n");
                    this.run();
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }
}
