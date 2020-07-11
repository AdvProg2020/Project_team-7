package Main.server.model.filters;

import Main.server.model.Product;
import Main.server.model.accounts.SellerAccount;

import java.util.ArrayList;

public class SellerFilter extends Filter {
    private final String name;
    private String sellerUserName;

    public SellerFilter(String sellerUserName, ArrayList<Product> products) {
        this.name = "Seller";
        this.sellerUserName = sellerUserName;
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) throws Exception {
        for (Product product : products) {
            if (product.getSellers().contains(SellerAccount.getSellerWithUserName(sellerUserName)))
                filterdProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) throws Exception {
        for (Product product : products) {
            if (!product.getSellers().contains(SellerAccount.getSellerWithUserName(sellerUserName)))
                filterdProducts.remove(product);
        }
    }

    protected String show() {
        return name + " : " + sellerUserName;
    }

    public String getName() {
        return name;
    }
}
