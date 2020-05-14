package Main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PropertyPermission;

public class Category {
    private String name;
    private ArrayList<String> specialFeatures = new ArrayList<String>();
    private ArrayList<Product> products = new ArrayList<Product>();
    private static ArrayList<Category> allCategories = new ArrayList<Category>();

    public Category(String name, ArrayList<String> specialFeatures) {
        this.name = name;
        this.specialFeatures = specialFeatures;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean isThereCategoryWithName(String name) {
        for (Category category : allCategories) {
            if (category.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static String showAllCategories() {
        if (allCategories.isEmpty())
            return "NO categories have been created yet!";
        else {
            StringBuilder listOfCategories = new StringBuilder();
            listOfCategories.append("categories:");
            for (Category category : allCategories) {
                listOfCategories.append("\n" + category.getName());
            }
            return listOfCategories.toString();
        }
    }

    public String showSpecialFeatures() {
        StringBuilder features = new StringBuilder();
        features.append("specialFeatures:");
        for (String specialFeature : specialFeatures) {
            features.append("\n" + specialFeature);
        }
        return features.toString();
    }

    public String showAllProducts() {
        if (products.isEmpty())
            return "No products have been added to this category!";
        else {
            StringBuilder listOfProducts = new StringBuilder();
            listOfProducts.append("Products in this category:");
            for (Product product : products) {
                listOfProducts.append("\n" + product.getName() + "    Id: " + product.getProductId());
            }
            return listOfProducts.toString();
        }
    }

    public void editCategory(HashMap<String, String> changes) {

    }

    public static Category getCategoryWithName(String categoryName) throws Exception {
        for (Category category : allCategories) {
            if (category.getName().equalsIgnoreCase(categoryName))
                return category;
        }
        throw new Exception("There is no category with name : " + categoryName + "\n");
    }

    public static void addCategory(Category category) {
        allCategories.add(category);
    }

    public void removeCategory() {
        allCategories.remove(this);
        for (Product product : this.products) {
            product.removeCategory();
            product.emptySpecialFeatures();
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSpecialFeatures() {
        return specialFeatures;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.removeCategory();
    }

    public void addProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
            for (String specialFeature : specialFeatures) {
                product.addSpecialFeature(specialFeature, null);
            }
        }
    }

    public boolean isThereProductWithReference(Product product) {
        return products.contains(product);
    }

    public void addSpecialFeature(String specialFeature) {
        if (!specialFeatures.contains(specialFeature)) {
            specialFeatures.add(specialFeature);
            addSpecialFeatureToProducts(specialFeature);
        }
    }

    private void addSpecialFeatureToProducts(String specialFeature) {
        for (Product product : products) {
            product.addSpecialFeature(specialFeature, null);
        }
    }

    public void removeSpecialFeature(String specialFeature) {
        specialFeatures.remove(specialFeature);
        removeSpecialFeatureFromProducts(specialFeature);
    }

    private void removeSpecialFeatureFromProducts(String specialFeature) {
        for (Product product : products) {
            product.removeSpecialFeature(specialFeature);
        }
    }

    public boolean isThereSpecialFeature(String specialFeature) {
        return specialFeatures.contains(specialFeature);
    }
}
