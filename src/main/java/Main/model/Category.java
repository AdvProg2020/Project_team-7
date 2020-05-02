package Main.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private String name;
    private ArrayList<String> specialFeatures = new ArrayList<String>();
    private ArrayList<Product> products = new ArrayList<Product>();
    private static ArrayList<Category> allCategories = new ArrayList<Category>();

    public Category(String name, ArrayList<String> specialFeatures) {
        this.name = name;
        this.specialFeatures = specialFeatures;
        allCategories.add(this);
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
            features.append("\n"+specialFeature);
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

    public static Category getCategoryWithName(String categoryName) {
        for (Category category : allCategories) {
            if (category.getName().equalsIgnoreCase(categoryName))
                return category;
        }
        return null;
        //TODO invalid name exception
    }

    public static void addCategory(Category category) {
        allCategories.add(category);
    }

    public static void removeCategory(Category category) {
        allCategories.remove(category);
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
}
