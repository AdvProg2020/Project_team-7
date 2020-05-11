package Main.model.exceptions;

public class DiscountAndOffTypeServiceException {

    public static void validateInputAmount(double maxAmount) throws Exception {
        if(maxAmount<=0){
            throw new Exception("max amount must be a positive double");
        }
    }

    public static void validateInputMaxNumOfUse(int maxNumberOfUse) throws Exception {
        if(maxNumberOfUse<=0){
            throw new Exception("max number of use must be a positive integer");
        }
    }

    public static void validateInputPercent(double percent) throws Exception {
        if(percent<=0){
            throw new Exception("discount percent must be a positive double");
        }
    }

    public static void validateInputDate(String inputDate) throws Exception {
        if(!inputDate.matches("[0-9]{1,4}/[0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) {
            throw new Exception("this date isn't acceptable! please enter date in format yyyy/MM/dd HH:mm:ss \n");
        }
    }
}
