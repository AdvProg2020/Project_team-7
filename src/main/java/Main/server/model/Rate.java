package Main.server.model;

import Main.server.model.accounts.BuyerAccount;

import java.io.Serializable;

public class Rate implements Serializable {
    private BuyerAccount buyer;
    private Product product;
    private double score;

    public Rate(BuyerAccount buyer, Product product, double score) {
        this.buyer = buyer;
        this.product = product;
        this.score = score;
    }

    public BuyerAccount getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public double getScore() {
        return score;
    }
}
