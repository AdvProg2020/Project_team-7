package Main.model.requests;

import Main.model.Off;
import Main.model.OffStatus;

import java.util.HashMap;

public class EditOffRequest extends Request {
    private Off off;
    private HashMap<String, String> changes;
    private final String name;

    public EditOffRequest(Off off, HashMap<String, String> changes, String name) {
        this.off = off;
        this.changes = changes;
        this.name = name;
    }

    public String showRequest() {
        String show = "Edit Off Request:\n" +
                "Request ID: " + this.requestId + "\n" +
                "Off ID: " + off.getOffId() + "\n" +
                "Editing Following Fields:\n";
        for (String s : changes.keySet()) {
            show.concat("edit " + s + " to " + changes.get(s) + "\n");
        }
        return show;
    }

    public void acceptRequest() {
        for (String changeField : changes.keySet()) {
            if (changeField.equals("offId")) {
                //TODO exception you are not allowed to edit this field
            } else if (changeField.equals("products")) {
                //TODO removeOrAddSomeProducts(changes.get(changeField));
            } else if (changeField.equals("startTime")) {
                //TODO if off started, throw exception it is not possible
                //TODO check time format with regex
                //time regex: "^((1[0-2])|(0?[1-9]))/((0?[1-9])|([12][0-9])|(3[01])),((0?[0-9])|(1[0-9])|(2[0-3])):((0?[0-9])|([12345][0-9]))$"
                off.setStartDate((changes.get(changeField)));
            } else if (changeField.equals("endTime")) {
                //TODO check time format with regex
                //time regex: "^((1[0-2])|(0?[1-9]))/((0?[1-9])|([12][0-9])|(3[01])),((0?[0-9])|(1[0-9])|(2[0-3])):((0?[0-9])|([12345][0-9]))$"
                off.setEndDate((changes.get(changeField)));
            } else if (changeField.equals("offAmount")) {
                //TODO wrong off amount
                off.setOffAmount(Double.parseDouble(changeField));
            } else {
                //TODO exception
            }
        }
        off.setOffStatus(OffStatus.APPROVED_OFF);
    }

    public void declineRequest() {
        off.setOffStatus(OffStatus.APPROVED_OFF);
    }
}
