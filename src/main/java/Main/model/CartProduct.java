package Main.model;

import Main.model.accounts.SellerAccount;
import sun.misc.JavaSecurityProtectionDomainAccess;

import java.util.ArrayList;

public class CartProduct {
    private SellerAccount finalSeller;
    private Product product;
    private int numberOfProduct;

    public CartProduct(Product product, SellerAccount finalSeller) {

    }

    public void increaseNumberByOne() {

    }

    public void decreaseNumberByOne() {

    }

    public String toStringForSellLog() {

        return null;
    }

    public String toStringForBuyLog() {

        return null;
    }

    public Product getProduct() {
        return product;
    }
}
