package Main.server.model.filters;

import Main.server.controller.GeneralController;
import Main.server.model.Product;

import java.util.ArrayList;

public abstract class Filter {
    public static String showCurrentFilters() {
        String current = "";
        for (Filter filter : GeneralController.currentFilters) {
            current = current.concat(filter.show());
            current = current.concat("\n");
        }
        if (current.equals(""))
            return "No filters selected!";
        return current;
    }

    public static ArrayList<Product> applyFilter(ArrayList<Product> products) throws Exception {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Filter currentFilter : GeneralController.currentFilters) {
            currentFilter.apply(filteredProducts, products);
        }
        for (Filter currentFilter : GeneralController.currentFilters) {
            currentFilter.removeDiffs(filteredProducts, products);
        }
        if (filteredProducts.isEmpty() && GeneralController.currentFilters.isEmpty())
            return products;
        return filteredProducts;
    }

    public abstract void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) throws Exception;

    public abstract void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) throws Exception;

    protected abstract String show();

    public abstract String getName();
}