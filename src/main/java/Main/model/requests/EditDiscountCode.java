package Main.model.requests;

import Main.model.DiscountCode;
import Main.model.accounts.BuyerAccount;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class EditDiscountCode{

    private DiscountCode discountCode;
    private String startTime;
    private String endTime;
    private double percent;
    private double maxAmount;
    private int maxNumberOfUse;
    private ArrayList<BuyerAccount> usersToBeAdded = new ArrayList<BuyerAccount>();
    private ArrayList<BuyerAccount> usersToBeRemoved = new ArrayList<BuyerAccount>();

    public EditDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setPercent(String percent) throws Exception {
        try {
            this.percent = Double.parseDouble(percent);
        }catch (Exception e){
            throw new Exception("invalid input number !");
        }
    }

    public void setMaxAmount(String maxAmount) throws Exception {
        try {
            this.maxAmount = Double.parseDouble(maxAmount);
        }catch (Exception e){
            throw new Exception("invalid input number !");
        }
    }

    public void setMaxNumberOfUse(String maxNumberOfUse) throws Exception {
        try {
            this.maxNumberOfUse = Integer.parseInt(maxNumberOfUse);
        }catch (Exception e){
            throw new Exception("invalid input number !");
        }
    }

    public void addUserToBeAdded(String userName) throws Exception {
        BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(userName);

        usersToBeAdded.add(buyerAccount);
    }

    public void addUserToBeRemoved(String userName) throws Exception {
        BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(userName);

        usersToBeAdded.add(buyerAccount);
    }

    public void acceptRequest() throws Exception {
        String errors = new String();
        try {
            discountCode.setStartTime(startTime);
        }catch (Exception e){
            errors.concat(e.getMessage());
        }
        try {
            discountCode.setEndTime(endTime);
        }catch (Exception e){
            errors.concat(e.getMessage());
        }
        try{
            discountCode.setPercent(this.percent);
        }catch (Exception e){
            errors.concat(e.getMessage());
        }
        try{
            discountCode.setMaxAmount(this.maxAmount);
        }catch (Exception e){
            errors.concat(e.getMessage());
        }
        try{
            discountCode.setMaxNumberOfUse(this.maxNumberOfUse);
        }catch (Exception e){
            errors.concat(e.getMessage());
        }
        for (BuyerAccount buyerAccount : usersToBeAdded) {
            discountCode.addUser(buyerAccount);
        }
        for (BuyerAccount buyerAccount : usersToBeRemoved) {
            try {
                discountCode.removeUser(buyerAccount);
            }catch (Exception e){
                errors.concat(e.getMessage());
            }
        }
        if(errors.length()!=0){
            throw new Exception(errors);
        }
    }
}
