package Main.model.filters;

import Main.model.Product;

import java.util.ArrayList;

public class StockFilter extends Filter {
    private final String name;

    public StockFilter(String name, ArrayList<Product> products) {
        this.name = name;
    }
}
