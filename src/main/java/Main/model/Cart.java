package Main.model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartProduct> cartProducts = new ArrayList<CartProduct>();

    public double calculateCartTotalPriceConsideringOffs() {
        return 0;
    }

    public double calculateCartTotalOffs() {
        return 0;
    }

    public void removeProductFromCart(CartProduct cartProduct) {

    }

    public CartProduct getCartProductByProductId(String productId) {
        return null;
    }

    public String viewMe() {
        return null;
    }

    public void addCartProduct(CartProduct cartProduct) {

    }

    public String toStringCartsProductForLog(Cart cart, String status) {

        return status;
    }

    public ArrayList<Product> getCartsProductList() {
        ArrayList<Product> cartsProductList = new ArrayList<Product>();
        for (CartProduct cartProduct : cartProducts) {
            cartsProductList.add(cartProduct.getProduct());
        }
        return cartsProductList;
    }
}
