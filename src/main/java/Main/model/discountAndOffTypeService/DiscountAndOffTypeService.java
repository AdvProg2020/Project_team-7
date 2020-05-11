package Main.model.discountAndOffTypeService;

import Main.model.exceptions.DiscountAndOffTypeServiceException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscountAndOffTypeService {
    protected Date startDate;
    protected Date endDate;
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public DiscountAndOffTypeService(String startDate, String endDate) throws Exception {
        DiscountAndOffTypeServiceException.validateInputDate(startDate);
        DiscountAndOffTypeServiceException.validateInputDate(endDate);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public void setStartDate(String startDate) throws ParseException {
        String dateFormat = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        this.startDate = simpleDateFormat.parse(startDate);
    }

    public void setEndDate(String endDate) throws ParseException {
        String dateFormat = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        this.endDate = simpleDateFormat.parse(endDate);
    }

    protected void expireIfNeeded(){}
}
