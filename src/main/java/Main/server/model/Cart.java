package Main.server.model;

import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.server.model.discountAndOffTypeService.Off;
import Main.server.model.discountAndOffTypeService.OffStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Cart implements Serializable {
    private ArrayList<CartProduct> cartProducts = new ArrayList<>();

    public double getCartTotalPriceConsideringOffs() {
        double cartTotalPriceConsideringOffs = 0;
        for (CartProduct cartProduct : cartProducts) {
            cartTotalPriceConsideringOffs += (cartProduct.getProduct().getProductFinalPriceConsideringOff()) * cartProduct.getNumberOfProduct();
        }
        return cartTotalPriceConsideringOffs;
    }

    public double calculateCartTotalOffs() {
        double cartTotalOff = 0;
        for (CartProduct cartProduct : cartProducts) {
            Off off = cartProduct.getProduct().getOff();
            if (off != null && off.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
                off.removeOff();
            } else if (off != null && off.getOffStatus().equals(OffStatus.APPROVED_OFF)) {
                cartTotalOff += cartProduct.getProduct().getPrice() * off.getOffAmount() / 100;
            }
        }
        return cartTotalOff;
    }

    public void removeProductFromCart(CartProduct cartProduct) {
        cartProducts.remove(cartProduct);
    }

    public CartProduct getCartProductByProductId(String productId) throws Exception {
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().getProductId().equals(productId)) {
                return cartProduct;
            }
        }
        throw new Exception("There is no product with given Id in the cart !\n");
    }

    public String toStringForBuyer() {
        StringBuilder cartsProductsview = new StringBuilder();
        for (CartProduct cartProduct : cartProducts) {
            cartsProductsview.append(cartProduct.toStringForBuyLog());
        }
        return "Cart :\n" + cartsProductsview + "\ncart total cost (not considering discount codes): " +
                getCartTotalPriceConsideringOffs() + "\n";
    }

    public String toStringForSeller() {
        StringBuilder soldProductsView = new StringBuilder();
        for (CartProduct cartProduct : cartProducts) {
            soldProductsView.append(cartProduct.toStringForSellLog());
        }
        return "Sold products :\n" + soldProductsView + "\n";
    }

    public HashMap<SellerAccount, Cart> getAllSellersCarts() {
        HashMap<SellerAccount, Cart> allSellersCart = new HashMap<>();
        for (CartProduct cartProduct : cartProducts) {
            addProductToRelevantSellersCart(allSellersCart, cartProduct);
        }
        return allSellersCart;
    }

    private void addProductToRelevantSellersCart(HashMap<SellerAccount, Cart> allSellersCarts, CartProduct cartProduct) {
        SellerAccount seller = cartProduct.getFinalSeller();
        if (allSellersCarts.containsKey(seller)) {
            allSellersCarts.get(seller).addCartProduct(cartProduct);
        } else {
            Cart cart = new Cart();
            cart.addCartProduct(cartProduct);
            allSellersCarts.put(seller, cart);
        }
    }

    public void addCartProduct(CartProduct cartProduct) {
        if (!cartProducts.contains(cartProduct)) {
            cartProducts.add(cartProduct);
        }
    }

    public ArrayList<Product> getCartsProductList() {
        ArrayList<Product> cartsProductList = new ArrayList<>();
        for (CartProduct cartProduct : cartProducts) {
            cartProduct.getProduct().setTempNumberOfProduct(cartProduct.getNumberOfProduct());
            cartProduct.getProduct().setTempTotalPrice(cartProduct.getProduct().getPrice() * cartProduct.getNumberOfProduct());
            cartProduct.getProduct().setTempCartProduct(cartProduct);
            cartsProductList.add(cartProduct.getProduct());
        }
        return cartsProductList;
    }

    public ArrayList<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public boolean isThereProductWithID(String productID) {
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().getProductId().equals(productID)) {
                return true;
            }
        }
        return false;
    }

    public void emptyCart() {
        cartProducts.clear();
    }
}
