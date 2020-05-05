package Main.model;

import Main.model.accounts.BuyerAccount;

public class Rate {
    private BuyerAccount buyer;
    private Product product;
    private double score;

    public Rate(BuyerAccount buyer, Product product, double score) {
        this.buyer = buyer;
        this.product = product;
        //TODO : WHY THIS FIELD??
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
