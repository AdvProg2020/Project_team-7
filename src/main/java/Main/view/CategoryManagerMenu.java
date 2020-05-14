package Main.view;

import Main.model.requests.EditCategory;

import java.util.ArrayList;

public class CategoryManagerMenu extends Menu {
    public CategoryManagerMenu(Menu parentMenu) {
        super("Manage categories", parentMenu);
        this.subMenus.put(1, editCategory());
        this.subMenus.put(2, addCategory());
        this.subMenus.put(3, removeCategory());

    }

    private Menu editCategory() {
        return new Menu("Edit category", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a category name or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        EditCategory editCategory = managerController.getCategoryToEdit(input);
                        getFieldsToEdit(editCategory);
                        managerController.submitCategoryEdits(editCategory);
                        System.out.println("Category edited successfully.");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    public void getFieldsToEdit(EditCategory editCategory) {
        System.out.println("Fields you are allowed to edit:(You can insert any field you want to edit unless you insert" +
                " Submit.)\nName\nProduct Id to be added\nProduct Id to be removed\nSpecial feature to be added\n" +
                "Special feature to be removed");
        String input;
        while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("submit")) {
            System.out.println("Enter the content:");
            String newContent = scanner.nextLine();
            if (input.equalsIgnoreCase("name"))
                editCategory.setName(newContent);
            else if (input.equalsIgnoreCase("Product Id to be added"))
                editCategory.addProductToBeAdded(newContent);
            else if (input.equalsIgnoreCase("Product Id to be removed"))
                editCategory.addProductToBeRemoved(newContent);
            else if (input.equalsIgnoreCase("Special feature to be added"))
                editCategory.addSpecialFeatureToBeAdded(newContent);
            else if (input.equalsIgnoreCase("Special feature to be removed"))
                editCategory.addSpecialFeatureToBeRemoved(newContent);
            else
                System.out.println("there is no field with this name!");
        }
    }

    private Menu addCategory() {
        return new Menu("Add category", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter category name or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        ArrayList<String> specialFeatures = new ArrayList<>();
                        getSpecialFeatures(specialFeatures);
                        managerController.createCategory(input, specialFeatures);
                        System.out.println("Category created successfully.");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    public void getSpecialFeatures(ArrayList<String> specialFeatures) {
        System.out.println("Enter special features: (You can add special features unless you insert Done)");
        String input;
        while (!(input = scanner.nextLine().trim()).equalsIgnoreCase("done")) {
            specialFeatures.add(input);
        }
    }

    private Menu removeCategory() {
        return new Menu("Remove category", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a category Id or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        managerController.removeCategoryWithName(input);
                        System.out.println("Category removed successfully.");
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
        System.out.println(generalController.showAllCategories());
        this.show();
        this.execute();
    }
}
