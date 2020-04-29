package Main.model;

import Main.model.accounts.BuyerAccount;

public class Comment {
    private BuyerAccount user;
    private String title;
    private String content;
    private boolean isBoughtByUser;
    private Product product;
    private CommentStatus commentStatus;

    public Comment(BuyerAccount user, Product product, String title, String content, CommentStatus commentStatus, boolean isBoughtByUser) {

    }

    public void setCommentStatus(CommentStatus commentStatus) {

    }

    public BuyerAccount getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isBoughtByUser() {
        return isBoughtByUser;
    }

    public Product getProduct() {
        return product;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public void setUser(BuyerAccount user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBoughtByUser(boolean boughtByUser) {
        isBoughtByUser = boughtByUser;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
