package Main.server.model.logs;

import java.io.Serializable;

public enum DeliveryStatus implements Serializable {
    DELIVERED,
    PENDING_DELIVERY
    //TODO : how can it change?
}
