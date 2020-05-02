package Main.model.filters;

import Main.model.Product;

import java.util.ArrayList;

public class BrandFilter extends Filter {
    private final String name;
    private String brandName = null;

    public BrandFilter(String name, String brandName, ArrayList<Product> products) {
        this.name = name;
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products){
        for (Product product : products) {
            if (product.getBrand().equals(brandName))
                filterdProducts.add(product);
        }
    }
}
