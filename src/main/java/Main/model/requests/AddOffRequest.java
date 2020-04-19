package Main.model.requests;

import Main.model.Off;

public class AddOffRequest extends Request {
    private Off off;
    private final String name;

    public AddOffRequest(Off off, String name) {
        this.off = off;
        this.name = name;
    }
}
