package Main.model;

import Main.model.accounts.SellerAccount;

public class CartProduct {
    private SellerAccount finalSeller;
    private Product product;
    private int numberOfProduct;
    private Cart cart;

    public CartProduct(Product product, SellerAccount finalSeller,Cart cart) {
        this.product = product;
        this.finalSeller = finalSeller;
        this.numberOfProduct = 1;
        this.cart = cart;
    }

    public void increaseNumberByOne() {
        this.numberOfProduct += 1;
    }

    public void decreaseNumberByOne() throws Exception {
        if(numberOfProduct==1){
           cart.removeProductFromCart(this);
           throw new Exception("prodduct removed from cart !");
        }
        this.numberOfProduct -= 1;
    }

    public String toStringForSellLog() {
        return "[Product ID : " + product.getProductId() + "    Product Name : " + product.getName() + "    Brand : " +
                product.getBrand() + "\nPrice : " + product.getPrice() + "  Off : %" + product.getOff().getOffAmount() + "]\n";
    }

    public String toStringForBuyLog() {
        return "[Product ID : " + product.getProductId() + "    Product Name : " + product.getName() + "Brand : " +
                product.getBrand() + "\nFinal Seller : " + finalSeller + "  Price : " + product.getPrice() + "  Off : %"
                + product.getOff().getOffAmount() + "]\n";
    }

    public Product getProduct() {
        return product;
    }
}
