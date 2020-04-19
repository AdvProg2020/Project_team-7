package Main.model.filters;

import Main.model.Product;

import java.util.ArrayList;

public class PriceRangeFilter extends Filter{
    private final String name;
    private double startRange;
    private double endRange;

    public PriceRangeFilter(String name, double startRange, double endRange, ArrayList<Product> products) {
        this.name = name;
    }
}
