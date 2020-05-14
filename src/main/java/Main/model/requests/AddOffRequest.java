package Main.model.requests;

import Main.model.discountAndOffTypeService.*;

public class AddOffRequest extends Request {
    private Off off;
    private final String name;

    public AddOffRequest(Off off, String name) {
        this.off = off;
        this.name = name;
    }

    public String showRequest() {
        String show = "Add New Off Request:\n" +
                "Request ID: " + this.requestId + "\n" +
                off.viewMe() + "\n";
        return show;
    }

    public void acceptRequest() {
        off.setOffStatus(OffStatus.APPROVED_OFF);
        off.addOff(off);
    }

    public void declineRequest() {
        off.removeOff();
    }
}
