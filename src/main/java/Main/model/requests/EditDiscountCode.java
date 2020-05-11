package Main.model.requests;

import Main.model.DiscountCode;
import Main.model.accounts.BuyerAccount;
import Main.model.exceptions.DiscountCodeInputException;

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

    public EditDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public void setStartDate(String startDate) throws Exception {
        DiscountCodeInputException.validateInputDate(startDate);
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) throws Exception {
        DiscountCodeInputException.validateInputDate(endDate);
        this.endDate = endDate;
    }

    public void setPercent(String percent) throws Exception {
        double discountPrecent;
        try {
             discountPrecent = Double.parseDouble(percent);
        } catch (Exception e) {
            throw new Exception("invalid input number !");
        }
        DiscountCodeInputException.validateInputPercent(discountPrecent);
        this.percent = discountPrecent;
    }

    public void setMaxAmount(String maxAmount) throws Exception {
        double discountMaxAmount;
        try {
            discountMaxAmount = Double.parseDouble(maxAmount);
        } catch (Exception e) {
            throw new Exception("invalid input number !");
        }
        DiscountCodeInputException.validateInputMaxAmount(discountMaxAmount);
        this.maxAmount = discountMaxAmount;
    }

    public void setMaxNumberOfUse(String maxNumberOfUse) throws Exception {
        int discountMaxNumOfUse;
        try {
            discountMaxNumOfUse = Integer.parseInt(maxNumberOfUse);
        } catch (Exception e) {
            throw new Exception("invalid input number !");
        }
        DiscountCodeInputException.validateInputMaxNumOfUse(discountMaxNumOfUse);
        this.maxNumberOfUse = discountMaxNumOfUse;
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

        acceptStartTime(errors);
        acceptEndTime(errors);
        acceptPercent(errors);
        acceptMaxAmount(errors);
        acceptMaxNumberOfUse(errors);
        acceptBuyersToBeAdded();
        acceptBuyersToBeRemoved();

        if (errors.length() != 0) {
            throw new Exception(errors);
        }
    }

    private void acceptStartTime(String errors) {
        try {
            discountCode.setStartDate(startDate);
        } catch (Exception e) {
            errors.concat(e.getMessage());
        }
    }

    private void acceptEndTime(String errors) {
        try {
            discountCode.setEndDate(endDate);
        } catch (Exception e) {
            errors.concat(e.getMessage());
        }
    }

    private void acceptPercent(String errors) {
        try {
            discountCode.setPercent(this.percent);
        } catch (Exception e) {
            errors.concat(e.getMessage());
        }
    }

    private void acceptMaxAmount(String errors) {
        try {
            discountCode.setMaxAmount(this.maxAmount);
        } catch (Exception e) {
            errors.concat(e.getMessage());
        }
    }

    private void acceptMaxNumberOfUse(String errors) {
        try {
            discountCode.setMaxNumberOfUse(this.maxNumberOfUse);
        } catch (Exception e) {
            errors.concat(e.getMessage());
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
