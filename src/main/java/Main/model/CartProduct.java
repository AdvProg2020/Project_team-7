package Main.model;

import Main.model.accounts.SellerAccount;
import Main.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.model.discountAndOffTypeService.Off;
import Main.model.discountAndOffTypeService.OffStatus;

public class CartProduct{
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
        if(product.getAvailability()<this.numberOfProduct){
            return false;
        }
        return true;
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
