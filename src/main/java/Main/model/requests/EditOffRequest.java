package Main.model.requests;

import Main.model.Off;

import java.util.HashMap;

public class EditOffRequest extends Request {
    private Off off;
    private HashMap<String,String> changes;
    private final String name;

    public EditOffRequest(Off off, HashMap<String, String> changes, String name) {
        this.off = off;
        this.changes = changes;
        this.name = name;
    }
}
