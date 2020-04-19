package Main.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private String name;
    private ArrayList<String> specialFeatures = new ArrayList<String>();
    private ArrayList<Product> products = new ArrayList<Product>();
    private static ArrayList<Category> allCategories = new ArrayList<Category>();

    public Category(String name, ArrayList<String> specialFeatures) {

    }

    public static String showAllCategories() {
        return null;
    }

    public String showAvailableFilters() {
        return null;
    }

    public String showAllProducts() {
        return null;
    }

    public void editCategory(HashMap<String, String> changes) {

    }

    //id is the name of the category
    public static Category getCategoryWithId(String categoryId) {
        return null;
    }

    public static void addCategory(Category category) {

    }

    public static void removeCategory(Category category) {

    }
}
