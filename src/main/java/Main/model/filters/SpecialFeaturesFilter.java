package Main.model.filters;

import Main.model.Product;

import java.util.ArrayList;

public class SpecialFeaturesFilter extends Filter {
    private final String name;
    private String featureTitle;
    private String desiredFilter;

    public SpecialFeaturesFilter(String featureTitle, String desiredFilter, ArrayList<Product> products) {
        this.name = featureTitle;
        this.featureTitle = featureTitle;
        this.desiredFilter = desiredFilter;
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getSpecialFeatures().get(featureTitle).equals(desiredFilter))
                filterdProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.getSpecialFeatures().get(featureTitle).equals(desiredFilter))
                filterdProducts.remove(product);
        }
    }

    protected String show() {
        return featureTitle + " : " + desiredFilter;
    }

    public String getName() {
        return name;
    }
}
