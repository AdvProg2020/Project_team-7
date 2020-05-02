package Main.model.filters;

import Main.Main;
import Main.controller.GeneralController;
import Main.model.Product;

import java.util.ArrayList;

public abstract class Filter {
    public ArrayList<Product> applyFilter(ArrayList<Product> products) {
        ArrayList<Product> filteredProducts = new ArrayList<Product>();
        for (Filter currentFilter : Main.generalController.currentFilters) {
            currentFilter.apply(filteredProducts, products);
        }
        return filteredProducts;
    }

    public abstract void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products);
}