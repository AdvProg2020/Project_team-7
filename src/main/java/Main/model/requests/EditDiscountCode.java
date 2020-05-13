package Main.model.requests;

import Main.model.accounts.BuyerAccount;
import Main.model.discountAndOffTypeService.DiscountCode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EditDiscountCode {

    private DiscountCode discountCode;
    private String startDate;
    private String endDate;
    private String percent;
    private String maxAmount;
    private String maxNumberOfUse;
    private ArrayList<String> usersToBeAdded = new ArrayList<>();
    private ArrayList<String> usersToBeRemoved = new ArrayList<>();
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public EditDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
        this.startDate = dateFormat.format(discountCode.getStartDate());
        this.endDate = dateFormat.format(discountCode.getEndDate());
        this.percent = "" + discountCode.getDiscountCodeAmount();
        this.maxAmount = "" + discountCode.getMaxAmount();
        this.maxNumberOfUse = "" + discountCode.getMaxNumberOfUse();
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
        usersToBeAdded.add(userName);
    }

    public void addUserToBeRemoved(String userName){
        usersToBeAdded.add(userName);
    }

    public void acceptRequest() throws Exception {
        discountCode.setDates(startDate,endDate);
        discountCode.setPercent(Double.parseDouble(percent));
        discountCode.setMaxAmount(Double.parseDouble(maxAmount));
        discountCode.setMaxNumberOfUse(Integer.parseInt(maxNumberOfUse));
        acceptBuyersToBeAdded();
        acceptBuyersToBeRemoved();
    }


    private void acceptBuyersToBeAdded() throws Exception {
        for (String userName : usersToBeAdded) {
            BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(userName);
            discountCode.addUser(buyerAccount);
        }
    }

    private void acceptBuyersToBeRemoved() throws Exception {
        for (String userName : usersToBeRemoved) {
            BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(userName);
            discountCode.removeUser(buyerAccount);
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPercent() {
        return percent;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public String getMaxNumberOfUse() {
        return maxNumberOfUse;
    }

    public ArrayList<String> getUsersToBeAdded() {
        return usersToBeAdded;
    }

    public ArrayList<String> getUsersToBeRemoved() {
        return usersToBeRemoved;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }
}
