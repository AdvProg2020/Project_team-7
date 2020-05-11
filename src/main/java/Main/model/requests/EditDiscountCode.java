package Main.model.requests;

import Main.model.accounts.BuyerAccount;
import Main.model.discountAndOffTypeService.DiscountCode;
import Main.model.exceptions.DiscountAndOffTypeServiceException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EditDiscountCode {

    private DiscountCode discountCode;
    private String startDate;
    private String endDate;
    private double percent;
    private double maxAmount;
    private int maxNumberOfUse;
    private ArrayList<BuyerAccount> usersToBeAdded = new ArrayList<BuyerAccount>();
    private ArrayList<BuyerAccount> usersToBeRemoved = new ArrayList<BuyerAccount>();
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public EditDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public void setStartDate(String startDate) throws Exception {
        DiscountAndOffTypeServiceException.validateInputDate(startDate);
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) throws Exception {
        DiscountAndOffTypeServiceException.validateInputDate(endDate);
        this.endDate = endDate;
    }

    public void setPercent(String percent) throws Exception {
        DiscountAndOffTypeServiceException.validateInputPercent(percent);
        this.percent = Double.parseDouble(percent);
    }

    public void setMaxAmount(String maxAmount) throws Exception {
        DiscountAndOffTypeServiceException.validateInputAmount(maxAmount);
        this.maxAmount = Double.parseDouble(maxAmount);
    }

    public void setMaxNumberOfUse(String maxNumberOfUse) throws Exception {
        DiscountAndOffTypeServiceException.validateInputMaxNumOfUse(maxNumberOfUse);
        this.maxNumberOfUse = Integer.parseInt(maxNumberOfUse);
    }

    public void addUserToBeAdded(String userName) throws Exception {
        BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(userName);

        usersToBeAdded.add(buyerAccount);
    }

    public void addUserToBeRemoved(String userName) throws Exception {
        BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(userName);
        if (!discountCode.isThereBuyerWithReference(buyerAccount)) {
            throw new Exception("There is no buyer with this user name in discount code's user list !\n");
        }
        usersToBeAdded.add(buyerAccount);
    }

    public void acceptRequest() throws Exception {
        String errors = new String();

        acceptDates(errors);
        acceptPercent(errors);
        acceptMaxAmount(errors);
        acceptMaxNumberOfUse(errors);
        acceptBuyersToBeAdded();
        acceptBuyersToBeRemoved();

        if (errors.length() != 0) {
            throw new Exception(errors);
        }
    }

    private void acceptDates(String errors) {
        String finalStartDate = (startDate == null ? dateFormat.format(discountCode.getStartDate()) : startDate);
        String finalEndDate = (endDate == null ? dateFormat.format(discountCode.getEndDate()) : endDate);
        try {
            discountCode.setDates(finalStartDate, finalEndDate);
        } catch (Exception e) {
            errors.concat(e.getMessage());
        }
    }

    private void acceptPercent(String errors) {
       if(percent!=0){
           discountCode.setPercent(this.percent);
       }
    }

    private void acceptMaxAmount(String errors) {
        if(maxAmount!=0){
            discountCode.setMaxAmount(this.maxAmount);
        }
    }

    private void acceptMaxNumberOfUse(String errors) {
        if(maxNumberOfUse!=0){
            discountCode.setMaxNumberOfUse(this.maxNumberOfUse);
        }
    }

    private void acceptBuyersToBeAdded() {
        for (BuyerAccount buyerAccount : usersToBeAdded) {
            discountCode.addUser(buyerAccount);
        }
    }

    private void acceptBuyersToBeRemoved() {
        for (BuyerAccount buyerAccount : usersToBeRemoved) {
            discountCode.removeUser(buyerAccount);
        }
    }
}
