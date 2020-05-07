package Main.model;

import Main.model.accounts.SellerAccount;

public class CartProduct {
    private SellerAccount finalSeller;
    private Product product;
    private int numberOfProduct;
    private Cart cart;

    public CartProduct(Product product, SellerAccount finalSeller, Cart cart) {
        this.product = product;
        this.finalSeller = finalSeller;
        this.numberOfProduct = 1;
        this.cart = cart;
    }

    public void increaseNumberByOne() {
        this.numberOfProduct += 1;
    }

    public void decreaseNumberByOne() throws Exception {
        if (numberOfProduct == 1) {
            cart.removeProductFromCart(this);
            throw new Exception("prodduct removed from cart !");
        }
        this.numberOfProduct -= 1;
    }

    public String toStringForSellLog() {
        return "[Product ID : " + product.getProductId() + "\tProduct Name : " + product.getName() + "\tBrand : " +
                product.getBrand() + "\nPrice : " + product.getPrice() + "\tOff : " + (product.getOff() != null ? "%" +
                product.getOff().getOffAmount() : "no off is set for this product") + "]\n";
    }

    public String toStringForBuyLog() {
        return "[Product ID : " + product.getProductId() + "\tProduct Name : " + product.getName() + "\tBrand : " +
                product.getBrand() + "\n" + finalSeller.viewMe() + "  Price : " + product.getPrice() + "\tOff : "
                + (product.getOff() != null ? "%" + product.getOff().getOffAmount() : "no off is set for this product") + "]\n";
    }

    public Product getProduct() {
        return product;
    }

    public SellerAccount getFinalSeller() {
        return finalSeller;
    }
}
