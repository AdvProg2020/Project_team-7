package Main.model.requests;

import Main.model.Comment;
import Main.model.CommentStatus;

public class AddCommentRequest extends Request {
    private Comment comment;
    private final String name;

    public AddCommentRequest(Comment comment, String name) {
        super();
        this.comment = comment;
        this.name = name;
    }

    public String showRequest() {
        return "Add New Comment Request:\n" +
                "\tRequest ID: " + this.requestId + "\n" +
                "\tComment is related to product " + comment.getProduct().getName() + "\n" +
                "\tis written by user " + comment.getUser().getFirstName() + " " + comment.getUser().getLastName() + "\n" +
                "\tComment Title: " + comment.getTitle() + "\n" +
                "\tComment Content: " + comment.getContent();
    }

    public void acceptRequest() {
        comment.getProduct().addComment(comment);
        comment.setCommentStatus(CommentStatus.APPROVED_COMMENT);
    }

    public void declineRequest() {
        allRequests.remove(this);
        comment.setCommentStatus(CommentStatus.DECLINED_COMMENT);
    }
}
