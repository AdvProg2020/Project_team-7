package Main.model.discountAndOffTypeService;

import Main.model.exceptions.DiscountAndOffTypeServiceException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DiscountAndOffTypeService {
    protected Date startDate;
    protected Date endDate;
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String inputDateFormat = "yyyy/MM/dd HH:mm:ss";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputDateFormat);

    public DiscountAndOffTypeService(String startDate, String endDate) throws Exception {
        DiscountAndOffTypeServiceException.validateInputDate(startDate);
        DiscountAndOffTypeServiceException.validateInputDate(endDate);
        setDates(startDate, endDate);
    }

    public void setDates(String startDate, String endDate) throws Exception {
        Date inputStartDate = simpleDateFormat.parse(startDate);
        Date inputEndDate = simpleDateFormat.parse(endDate);
        DiscountAndOffTypeServiceException.compareStartAndEndDate(inputStartDate, inputEndDate);
        this.startDate = inputStartDate;
        this.endDate = inputEndDate;
    }

    protected abstract void expire();

    protected boolean isDiscountOrOffActiveNow(Date startDate, Date endDate) {
        Date dateNow = new Date();
        if ((startDate.compareTo(dateNow) <= 0) && (endDate.compareTo(dateNow) > 0)) {
            return true;
        }
        return false;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    //TODO : basics are done but real expiration left
}
