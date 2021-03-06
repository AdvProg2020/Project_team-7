package Main.server.model;

import Main.server.controller.GeneralController;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class Category implements Serializable {
    private String name;
    private ArrayList<String> specialFeatures;
    private ArrayList<Product> products = new ArrayList<>();
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String imagePath;

    private ArrayList<String> productsStringRecord = new ArrayList<>();

    public Category(String name, ArrayList<String> specialFeatures,String imagePath) {
        this.name = name;
        this.specialFeatures = specialFeatures;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<String> categoriesList(){
        ArrayList<String> list = new ArrayList<>();
        for (Category category : allCategories) {
            list.add(category.name);
        }
        return list;
    }

    public static boolean isThereCategoryWithName(String name) {
        for (Category category : allCategories) {
            if (category.name.equals(name)) {
                return true;
            }
        }
        return name.equals("-");
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
            features.append("\n").append(specialFeature);
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
                listOfProducts.append("\n").append(product.getName()).append("    Id: ").append(product.getProductId());
            }
            return listOfProducts.toString();
        }
    }

    public static Category getCategoryWithName(String categoryName) throws Exception {
        for (Category category : allCategories) {
            if (category.getName().equalsIgnoreCase(categoryName))
                return category;
        }
        if (categoryName.equals("-"))
            return new Category("-", new ArrayList<>(),"");
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

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/categories.json")));
            Category[] allcat = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Category[].class);
            allCategories = (allcat == null) ? new ArrayList<>() : new ArrayList<>(asList(allcat));
            setImagePaths();
            return "Read Categories Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from categories.json";
        }
    }

    private static void setImagePaths(){

    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/categories.json");
            Category[] allcat = new Category[allCategories.size()];
            allcat = allCategories.toArray(allcat);
            GeneralController.yagsonMapper.toJson(allcat, Category[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Categories Data Successfully.";
        } catch (IOException e) {
            return "Problem saving categories data.";
        }
    }

    public static void setStringRecordObjects() {
        try {
            setStringRecordProducts();
        } catch (Exception e) {
        }
    }

    private static void setStringRecordProducts() throws Exception {
        for (Category category : allCategories) {
            category.products.clear();
            for (String productID : category.productsStringRecord) {
                category.products.add(Product.getProductWithId(productID));
            }
        }
    }

    public static void getObjectStringRecords() {
        getProductsStringRecord();
    }

    private static void getProductsStringRecord() {
        for (Category category : allCategories) {
            category.productsStringRecord.clear();
            for (Product product : category.products) {
                category.productsStringRecord.add(product.getProductId());
            }
        }
    }

}
