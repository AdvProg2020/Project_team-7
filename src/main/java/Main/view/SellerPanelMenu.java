package Main.view;

import Main.model.Category;
import Main.model.exceptions.CreateProductException;

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
                System.out.println("Enter Continue to view information or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("continue")) {
                    System.out.println(sellerController.viewCompanyInformation());
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
                System.out.println("Enter Continue to view information or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("continue")) {
                    System.out.println(sellerController.viewSalesHistory());
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
                System.out.println("Enter Continue to create a product or Back to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        ArrayList<String> productInfo = new ArrayList<>();
                        getFieldsToCreateProduct(productInfo);
                        sellerController.addProduct(productInfo);
                    } catch (CreateProductException.InvalidProductInputInfo e) {
                        System.out.println(e.getMessage());
                        this.run();
                    } catch (CreateProductException.GetCategoryFromUser e) {
                        ArrayList specialFeatures = new ArrayList();
                        getSpecialFeatures(specialFeatures, e.getCategory());
                        sellerController.setSpecialFeatures(e.getProduct(), specialFeatures);
                        System.out.println("Product created successfully.");
                        this.run();

                    }
                }
            }
        };
    }

    public void getFieldsToCreateProduct(ArrayList<String> productInfo) {
        System.out.println("Name:");
        String name = scanner.nextLine();
        productInfo.add(name);
        System.out.println("Brand:");
        String brand = scanner.nextLine();
        productInfo.add(brand);
        System.out.println("Availability:");
        String availability = scanner.nextLine();
        productInfo.add(availability);
        System.out.println("Description:");
        String description = scanner.nextLine();
        productInfo.add(description);
        System.out.println("Price:");
        String price = scanner.nextLine();
        productInfo.add(price);
        System.out.println("Do you want to allocate a category for this product? please insert yes or no:");
        String beingInACategory = scanner.nextLine();
        productInfo.add(beingInACategory);
        System.out.println("If yes, Insert category name, otherwise just ignore this part:");
        String categoryName = scanner.nextLine();
        productInfo.add(categoryName);

    }

    public void getSpecialFeatures(ArrayList<String> specialFeatures, Category category) {
        for (String feature : category.getSpecialFeatures()) {
            System.out.println(feature + ":");
            String input = scanner.nextLine();
            specialFeatures.add(input);
        }

    }

    private Menu removeProduct() {
        return new Menu("Remove product", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a product Id or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        sellerController.removeProductWithID(input);
                        System.out.println("Product removed successfully.");
                        this.run();
                    } catch (Exception e){
                        System.out.println(e.getMessage());
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
                System.out.println("Enter View or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("view")) {
                    System.out.println(generalController.showAllCategories());
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
                System.out.println("Enter View or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("view")) {
                    System.out.println(sellerController.viewSellerBalance());
                    this.run();
                }
            }
        };
    }
}
