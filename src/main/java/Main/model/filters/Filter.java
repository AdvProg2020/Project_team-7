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

    public ArrayList<Product> applyFilter(ArrayList<Product> products) {
        ArrayList<Product> filteredProducts = new ArrayList<Product>();
        for (Filter currentFilter : GeneralController.currentFilters) {
            currentFilter.apply(filteredProducts, products);
        }
        return filteredProducts;
    }

    public abstract void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products);

    protected abstract String show();
}