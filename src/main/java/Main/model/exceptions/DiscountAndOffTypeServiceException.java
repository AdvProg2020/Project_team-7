package Main.model.exceptions;

public class DiscountAndOffTypeServiceException {

    public static void validateInputAmount(String maxAmount) throws Exception {
        double discountMaxAmount;
        try{
            discountMaxAmount = Double.parseDouble(maxAmount);
        }catch (Exception e){
            throw new Exception("discount max amount and off amount must be of double type! \n");
        }
        if(discountMaxAmount<=0){
            throw new Exception("discount max amount and off amount must be positive!\n");
        }
    }

    public static void validateInputMaxNumOfUse(String maxNumberOfUse) throws Exception {
        int discountMaxNumOfUse;
        try{
            discountMaxNumOfUse = Integer.parseInt(maxNumberOfUse);
        }catch (Exception e){
            throw new Exception("discount max number of use must be of integer type! \n");
        }

        if(discountMaxNumOfUse<=0){
            throw new Exception("max number of use must be positive!\n");
        }
    }

    public static void validateInputPercent(String percent) throws Exception {
        double discountPercent;
        try{
            discountPercent = Double.parseDouble(percent);
        }catch (Exception e){
            throw new Exception("discount percent must be of double type! \n");
        }
        if(discountPercent<=0){
            throw new Exception("discount percent must be positive!\n");
        }
    }

    public static void validateInputDate(String inputDate) throws Exception {
        if(!inputDate.matches("[0-9]{1,4}/[0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) {
            throw new Exception("this date isn't acceptable! please enter date in format yyyy/MM/dd HH:mm:ss \n");
        }
    }
}
