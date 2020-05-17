package Main.model.filters;

import Main.Main;
import Main.controller.GeneralController;
import Main.model.Product;

import java.util.ArrayList;

public abstract class Filter {
    public static String showCurrentFilters() {
        String current = "";
        for (Filter filter : GeneralController.currentFilters) {
            current = current.concat(filter.show());
            current = current.concat("\n");
        }
        return current;
    }

    public static ArrayList<Product> applyFilter(ArrayList<Product> products) throws Exception {
        ArrayList<Product> filteredProducts = new ArrayList<Product>();
        for (Filter currentFilter : GeneralController.currentFilters) {
            currentFilter.apply(filteredProducts, products);
        }
        for (Filter currentFilter : GeneralController.currentFilters) {
            currentFilter.removeDiffs(filteredProducts, products);
        }
        if (filteredProducts.isEmpty())
            return products;
        return filteredProducts;
    }

    public abstract void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) throws Exception;

    public abstract void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) throws Exception;

    protected abstract String show();

    public abstract String getName();
}