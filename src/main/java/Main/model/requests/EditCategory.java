package Main.model.requests;

import Main.model.Category;
import Main.model.Product;

import java.util.ArrayList;

public class EditCategory {

    private Category category;
    private String name;
    private ArrayList<String> specialFeatures = new ArrayList<String>();
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

    public void addSpecialFeature(String specialFeature) {
        specialFeatures.add(specialFeature);
    }

    public void acceptRequest() {
        String errors = new String();
        try {
            category.setName(this.name);
        } catch (Exception e) {
            errors.concat(e.getMessage());
        }

        for (String specialFeature : specialFeatures) {
            try {
                category.addSpecialFeature(specialFeature);
            } catch (Exception e) {
                errors.concat(e.getMessage());
            }
        }

        for (Product product : productsToBeAdded) {
            category.addProduct(product);
        }

        for (Product product : productsToBeRemoved) {
            category.removeProduct(product);
        }
    }
}
