package Main.model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartProduct> cartProducts = new ArrayList<CartProduct>();

    public double calculateCartTotalPriceConsideringOffs() {
        double cartTotalPriceConsideringOffs = 0;
        for (CartProduct cartProduct : cartProducts) {
            cartTotalPriceConsideringOffs += cartProduct.getProduct().getProductFinalPriceConsideringOff();
        }
        return cartTotalPriceConsideringOffs;
    }

    public double calculateCartTotalOffs() {
        double cartTotalOff = 0;
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().getOff() != null) {
                cartTotalOff += cartProduct.getProduct().getPrice() * cartProduct.getProduct().getOff().getOffAmount() / 100;
            }
        }
        return cartTotalOff;
    }

    public void removeProductFromCart(CartProduct cartProduct) {
        cartProducts.remove(cartProduct);
    }

    public CartProduct getCartProductByProductId(String productId) {
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().getProductId().equals(productId)) {
                return cartProduct;
            }
        }
        return null;
    }

    public String viewMe() {
        StringBuilder cartsProductsview = new StringBuilder();
        for (CartProduct cartProduct : cartProducts) {
            cartsProductsview.append(cartProduct.toStringForBuyLog());
        }
        return "Cart :\n" + cartsProductsview + "\n\ncart total cost : " + calculateCartTotalPriceConsideringOffs() + "\n";
    }

    public void addCartProduct(CartProduct cartProduct) {
        if (!cartProducts.contains(cartProduct)) {
            cartProducts.add(cartProduct);
        }
    }

    public ArrayList<Product> getCartsProductList() {
        ArrayList<Product> cartsProductList = new ArrayList<Product>();
        for (CartProduct cartProduct : cartProducts) {
            cartsProductList.add(cartProduct.getProduct());
        }
        return cartsProductList;
    }

    public boolean isThereProductWithID(String productID){
        for (CartProduct cartProduct : cartProducts) {
            if(cartProduct.getProduct().getProductId().equals(productID)){
                return true;
            }
        }
        return false;
    }

    public void emptyCart() {
        cartProducts.clear();
    }
}
