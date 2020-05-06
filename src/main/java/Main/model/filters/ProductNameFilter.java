package Main.model.filters;

import Main.model.Product;

import java.util.ArrayList;

public class ProductNameFilter extends Filter {
    private final String name;
    private String productName;

    public ProductNameFilter(String productName, ArrayList<Product> products) {
        this.name = "Product Name";
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getName().equals(productName))
                filterdProducts.add(product);
        }
    }

    protected String show() {
        return name + " : " + productName;
    }
}
