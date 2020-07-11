package Main.server.model.filters;

import Main.server.model.Product;

import java.util.ArrayList;

public class ProductNameFilter extends Filter {
    private final String name;
    private String productName;

    public ProductNameFilter(String productName, ArrayList<Product> products) {
        this.name = "Product Name";
        this.productName = productName;
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getName().equals(productName))
                filterdProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.getName().equals(productName))
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
