package Main.model;

import Main.model.accounts.BuyerAccount;

public class Comment {
    private BuyerAccount user;
    private String title;
    private String content;
    private boolean isBoughtByUser;
    private Product product;
    private CommentStatus commentStatus;

    public Comment(BuyerAccount user, Product product, String title, String content, boolean isBoughtByUser) {
        this.user = user;
        this.product = product;
        this.title = title;
        this.content = content;
        this.isBoughtByUser = isBoughtByUser;
        this.commentStatus = CommentStatus.PENDING_APPROVAL_COMMENT;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Product getProduct() {
        return product;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public BuyerAccount getUser() {
        return user;
    }
}
