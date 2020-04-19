package Main.model.filters;

import Main.model.Product;

import java.util.ArrayList;

public class ProductNameFilter extends Filter {
    private final String name;
    private String productName;

    public ProductNameFilter(String name, String productName, ArrayList<Product> products) {
        this.name = name;
    }
}
