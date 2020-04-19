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
}
