package Main.server.model.discountAndOffTypeService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DiscountAndOffTypeService {
    protected String startDate;
    protected String endDate;
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private static String inputDateFormat = "yyyy/MM/dd HH:mm:ss";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputDateFormat);

    public DiscountAndOffTypeService(String startDate, String endDate) throws Exception {
        setDates(startDate, endDate);
    }

    public void setDates(String startDate, String endDate) throws Exception {

        Date inputStartDate = simpleDateFormat.parse(startDate);
        Date inputEndDate = simpleDateFormat.parse(endDate);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DiscountAndOffStat getDiscountOrOffStat() {
        Date dateNow = new Date();
        if (getStartDate().compareTo(dateNow) > 0) {
            return DiscountAndOffStat.NOT_STARTED;
        }
        if (getEndDate().compareTo(dateNow) < 0) {
            return DiscountAndOffStat.EXPIRED;
        }
        return DiscountAndOffStat.ACTIVE;
    }

    public String getStartDateInStringFormat(){
        return startDate;
    }

    public String getEndDateInStringFormat(){
        return endDate;
    }

    public Date getStartDate() {
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getEndDate()  {
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
