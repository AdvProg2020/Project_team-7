package Main.model.discountAndOffTypeService;

import Main.model.exceptions.DiscountAndOffTypeServiceException;

import java.text.DateFormat;
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

    public DiscountAndOffStat getDiscountOrOffStat() {
        Date dateNow = new Date();
        if (startDate.compareTo(dateNow) > 0) {
            return DiscountAndOffStat.NOT_STARTED;
        }
        if (endDate.compareTo(dateNow) >= 0) {
            return DiscountAndOffStat.EXPIRED;
        }
        return DiscountAndOffStat.ACTIVE;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
