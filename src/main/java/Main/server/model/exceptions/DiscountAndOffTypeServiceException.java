package Main.server.model.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscountAndOffTypeServiceException {

    private static String inputDateFormat = "yyyy/MM/dd HH:mm:ss";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputDateFormat);

    public static void validateInputAmount(String maxAmount) throws Exception {
        double discountMaxAmount;
        try {
            discountMaxAmount = Double.parseDouble(maxAmount);
        } catch (Exception e) {
            throw new Exception("discount max amount and off amount must be of double type! \n");
        }
        if (discountMaxAmount <= 0) {
            throw new Exception("discount max amount and off amount must be positive!\n");
        }
    }

    public static void validateInputMaxNumOfUse(String maxNumberOfUse) throws Exception {
        int discountMaxNumOfUse;
        try {
            discountMaxNumOfUse = Integer.parseInt(maxNumberOfUse);
        } catch (Exception e) {
            throw new Exception("discount max number of use must be of integer type! \n");
        }

        if (discountMaxNumOfUse <= 0) {
            throw new Exception("max number of use must be positive!\n");
        }
    }

    public static void validateInputPercent(String percent) throws Exception {
        double discountPercent;
        try {
            discountPercent = Double.parseDouble(percent);
        } catch (Exception e) {
            throw new Exception("discount percent must be of double type! \n");
        }
        if (discountPercent <= 0) {
            throw new Exception("discount percent must be positive!\n");
        }
    }


    public static void validateInputDate(String inputDate) throws Exception {
        if (!inputDate.matches("[0-9]{1,4}/(0[1-9]|1[012]|[1-9])/(0[1-9]|[12][0-9]|3[01]|[1-9]) ([01][0-9]|2[0123]|[0-9]):([0-5][0-9]|[0-9]):([0-5][0-9]|[0-9])")) {
            throw new Exception("this date isn't acceptable! please enter date in format yyyy/MM/dd HH:mm:ss! \nalso make" +
                    " sure that month, day, hour, minute, second are respectively less than/equal to 12, 31, 23, 59, 59!\n");
        }
    }

    public static void compareStartAndEndDate(String startDate, String endDate) throws Exception {
        Date inputStartDate = simpleDateFormat.parse(startDate);
        Date inputEndDate = simpleDateFormat.parse(endDate);
        if (!(inputStartDate.compareTo(inputEndDate) < 0)) {
            throw new Exception("start date must be before end date !\n");
        }
    }
}
