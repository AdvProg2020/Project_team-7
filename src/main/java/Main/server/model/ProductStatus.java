package Main.server.model;

import java.io.Serializable;

public enum ProductStatus implements Serializable {
    APPROVED_PRODUCT,
    PENDING_CREATION_PRODUCT,
    PENDING_EDIT_PRODUCT
}
