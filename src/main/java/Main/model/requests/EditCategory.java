package Main.model.requests;

import Main.model.Category;
import Main.model.Product;

import java.util.ArrayList;

public class EditCategory {

    private Category category;
    private String name;
    private ArrayList<String> specialFeaturesToBeAdded = new ArrayList<String>();
    private ArrayList<String> specialFeaturesToBeRemoved = new ArrayList<String>();
    private ArrayList<Product> productsToBeAdded = new ArrayList<Product>();
    private ArrayList<Product> productsToBeRemoved = new ArrayList<Product>();

    public EditCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProductToBeAdded(String productID) throws Exception {
        Product product = Product.getProductWithId(productID);
        productsToBeAdded.add(product);
    }

    public void addProductToBeRemoved(String productID) throws Exception {
        Product product = Product.getProductWithId(productID);
        if (!category.isThereProductWithReference(product)) {
            throw new Exception("There is no product with this ID in this category !\n");
        }
        productsToBeRemoved.add(product);
    }

    public void addSpecialFeatureToBeAdded(String specialFeature) throws Exception {
        if (category.isThereSpecialFeature(specialFeature)) {
            throw new Exception("There is already a special feature with title \"" + specialFeature + "\" in this category !\n");
        }
        specialFeaturesToBeAdded.add(specialFeature);
    }

    public void addSpecialFeatureToBeRemoved(String specialFeature) throws Exception {
        if (!category.isThereSpecialFeature(specialFeature)) {
            throw new Exception("There is no special feature with this title in this category!\n");
        }
        specialFeaturesToBeRemoved.add(specialFeature);
    }

    public void acceptRequest() throws Exception {
        String errors = new String();
        acceptName(errors);
        acceptSpecialFeaturesToBeAdded();
        acceptSpecialFeaturesToBeRemoved();
        acceptProductsToBeAdded();
        acceptProductsToBeRemoved();

        if (errors.length() != 0) {
            throw new Exception(errors);
        }
    }

    private void acceptName(String errors) {
        if (name != null) {
            try {
                category.setName(this.name);
            } catch (Exception e) {
                errors.concat(e.getMessage());
            }
        }
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

    private void acceptProductsToBeAdded() {
        for (Product product : productsToBeAdded) {
            category.addProduct(product);
        }
    }

    private void acceptProductsToBeRemoved() {
        for (Product product : productsToBeRemoved) {
            category.removeProduct(product);
        }
    }
}
