package Main.server.model.filters;

import Main.server.model.Product;

import java.util.ArrayList;

public class StockFilter extends Filter {
    private final String name;

    public StockFilter(ArrayList<Product> products) {
        this.name = "Only Available Products";
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getAvailability() > 0)
                filterdProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getAvailability() <= 0)
                filterdProducts.remove(product);
        }
    }

    protected String show() {
        return name;
    }

    public String getName() {
        return name;
    }
}
