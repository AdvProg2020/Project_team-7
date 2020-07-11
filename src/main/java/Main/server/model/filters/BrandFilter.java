package Main.server.model.filters;

import Main.server.model.Product;

import java.util.ArrayList;

public class BrandFilter extends Filter {
    private final String name;
    private String brandName;

    public BrandFilter(String brandName, ArrayList<Product> products) {
        this.name = "Brand";
        this.brandName = brandName;
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getBrand().equals(brandName))
                filterdProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.getBrand().equals(brandName))
                filterdProducts.remove(product);
        }
    }

    protected String show() {
        return name + " : " + brandName;
    }

    public String getName() {
        return name;
    }
}
