package Main.server.model;

import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.server.model.discountAndOffTypeService.Off;
import Main.server.model.discountAndOffTypeService.OffStatus;

import java.io.Serializable;

public class CartProduct implements Serializable {
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

    public void increaseNumberByOne() throws Exception {
        if(product.getAvailability()<this.numberOfProduct+1){
           throw new Exception("sorry we are out of this product ! you can add no more of it!\n");
        }
        this.numberOfProduct += 1;
    }

    public void decreaseNumberByOne() throws Exception {
        if (numberOfProduct == 1) {
            cart.removeProductFromCart(this);
            throw new Exception("product removed from cart !");
        }
        this.numberOfProduct -= 1;
    }

    public String toStringForSellLog() {
        Off off = product.getOff();
        if (off != null && off.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
            off.removeOff();
        }
        return "[Product ID : " + product.getProductId() + "\tProduct Name : " + product.getName() + "\tBrand : " +
                product.getBrand() + "\nPrice : " + product.getPrice() + "\tOff : " +
                (product.getOff() != null ? "%" + product.getOff().getOffAmount() + "\toff status : " +
                        (product.getOff().getOffStatus().equals(OffStatus.APPROVED_OFF) ? "available" : "not available")
                        : "no off is set for this product") + "]\n";
    }

    public String toStringForBuyLog() {
        Off off = product.getOff();
        if (off != null && off.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
            off.removeOff();
        }
        return "[Product ID : " + product.getProductId() + "\tProduct Name : " + product.getName() + "\tBrand : " +
                product.getBrand() + "\n" + finalSeller.viewMe() + "  Price : " + product.getPrice() + "\tOff : "
                + (product.getOff() != null ? "%" + product.getOff().getOffAmount() : "no off is set for this product") + "]\n";
    }

    public boolean isProductAvailabilityEnough(){
        return product.getAvailability() >= this.numberOfProduct;
    }

    public Product getProduct() {
        return product;
    }

    public SellerAccount getFinalSeller() {
        return finalSeller;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }
}
