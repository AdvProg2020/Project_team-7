package Main.server.model;

import java.io.Serializable;

public enum CommentStatus implements Serializable {
    PENDING_APPROVAL_COMMENT,
    APPROVED_COMMENT,
    DECLINED_COMMENT
}
