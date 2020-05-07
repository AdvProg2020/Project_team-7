package Main.model.requests;

import Main.model.DiscountCode;
import java.util.ArrayList;

public class EditDiscountCode{

    private DiscountCode discountCode;
    private String startTime;
    private String endTime;
    private String percent;
    private String maxAmount;
    private String maxNumberOfUse;
    private ArrayList<String> usersToBeAdded = new ArrayList<String>();
    private ArrayList<String> usersToBeRemoved = new ArrayList<String>();

    public EditDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setMaxNumberOfUse(String maxNumberOfUse) {
        this.maxNumberOfUse = maxNumberOfUse;
    }

    public void addUserToBeAdded(String userName) {

    }

    public void addUserToBeRemoved(String userName) {
    }

    public void acceptRequest() {

    }
}
