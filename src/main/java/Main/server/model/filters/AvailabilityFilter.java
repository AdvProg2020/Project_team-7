package Main.server.model.filters;

import Main.server.model.Product;

import java.util.ArrayList;

public class AvailabilityFilter extends Filter {
    private final String name;

    public AvailabilityFilter(String filterName, ArrayList<Product> products) {
        this.name = "Availability";
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if(product.getAvailability()!=0){
                filterdProducts.add(product);
            }
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getAvailability()==0)
                filterdProducts.remove(product);
        }
    }

    protected String show() {
        return name ;
    }

    public String getName() {
        return name;
    }
}
