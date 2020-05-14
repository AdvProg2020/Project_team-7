package Main.model.requests;

import Main.model.Category;
import Main.model.Product;

import java.util.ArrayList;

public class EditCategory {

    private Category category;
    private String name;
    private ArrayList<String> specialFeaturesToBeAdded = new ArrayList<String>();
    private ArrayList<String> specialFeaturesToBeRemoved = new ArrayList<String>();
    private ArrayList<String> productsToBeAdded = new ArrayList<>();
    private ArrayList<String> productsToBeRemoved = new ArrayList<>();

    public EditCategory(Category category) {
        this.category = category;
        this.name = category.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProductToBeAdded(String productID) {
        productsToBeAdded.add(productID);
    }

    public void addProductToBeRemoved(String productID) {
        productsToBeRemoved.add(productID);
    }

    public void addSpecialFeatureToBeAdded(String specialFeature) {
        specialFeaturesToBeAdded.add(specialFeature);
    }

    public void addSpecialFeatureToBeRemoved(String specialFeature) {
        specialFeaturesToBeRemoved.add(specialFeature);
    }

    public void acceptRequest() throws Exception {
        category.setName(name);
        acceptSpecialFeaturesToBeAdded();
        acceptSpecialFeaturesToBeRemoved();
        acceptProductsToBeAdded();
        acceptProductsToBeRemoved();
    }

    private void acceptSpecialFeaturesToBeAdded() {
        for (String specialFeature : specialFeaturesToBeAdded) {
            category.addSpecialFeature(specialFeature);
        }
    }

    private void acceptSpecialFeaturesToBeRemoved() {
        for (String specialFeature : specialFeaturesToBeRemoved) {
            category.removeSpecialFeature(specialFeature);
        }
    }

    private void acceptProductsToBeAdded() throws Exception {
        for (String productID : productsToBeAdded) {
            category.addProduct(Product.getProductWithId(productID));
        }
    }

    private void acceptProductsToBeRemoved() throws Exception {
        for (String productID : productsToBeRemoved) {
            category.removeProduct(Product.getProductWithId(productID));
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSpecialFeaturesToBeAdded() {
        return specialFeaturesToBeAdded;
    }

    public ArrayList<String> getSpecialFeaturesToBeRemoved() {
        return specialFeaturesToBeRemoved;
    }

    public ArrayList<String> getProductsToBeAdded() {
        return productsToBeAdded;
    }

    public ArrayList<String> getProductsToBeRemoved() {
        return productsToBeRemoved;
    }

    public Category getCategory() {
        return category;
    }
}
