package Main.model.requests;

import Main.model.discountAndOffTypeService.*;

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

    public void acceptRequest() throws Exception {
        for (String changeField : changes.keySet()) {
            if (changeField.equals("offId")) {
                //TODO exception you are not allowed to edit this field
            } else if (changeField.equals("products")) {
                //TODO removeOrAddSomeProducts(changes.get(changeField));
            } else if (changeField.equals("startTime")) {
                //TODO to string end date
                //off.setDates(changes.get(changeField), off.getEndDate().toString());
            } else if (changeField.equals("endTime")) {
                //TODO to string start date
                //off.setDates(off.getStartDate().toString(), changes.get(changeField));
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
