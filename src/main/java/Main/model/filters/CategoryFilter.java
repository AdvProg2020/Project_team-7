package Main.model.filters;

import Main.model.Product;

import java.util.ArrayList;

public class CategoryFilter extends Filter {
    private final String name;
    private String productName;

    public CategoryFilter(String productName, ArrayList<Product> products) {
        this.name = "Product Name";
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getCategory().getName().equals(productName))
                filterdProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.getCategory().getName().equals(productName))
                filterdProducts.remove(product);
        }
    }

    protected String show() {
        return name + " : " + productName;
    }

    public String getName() {
        return name;
    }
}
