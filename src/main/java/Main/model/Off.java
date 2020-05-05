package Main.model;

import java.sql.Time;
import java.util.ArrayList;

public class Off {
    private String offId;
    private ArrayList<Product> products = new ArrayList<Product>();
    private Time startTime;
    private Time endTime;
    private double offAmount;
    private OffStatus offStatus;
    private static ArrayList<Off> allOffs = new ArrayList<Off>();

    public Off(String offId, ArrayList<Product> products, OffStatus offStatus, Time startTime, Time endTime, double offAmount) {

    }

    public static void addOff(Off off) {

    }

    public static String viewAllOffs() {
        return null;
    }

    public String viewMe() {
        return null;
    }

    public static Off getOffWithId(String offId) {
        return null;
    }

    public static void removeOff(Off off) {

    }

    public String getOffId() {
        return offId;
    }

    public double getOffAmount() {
        return offAmount;
    }

    public void setOffStatus(OffStatus offStatus) {
        this.offStatus = offStatus;
    }

    //time format: 2/27,14:50
    public void setStartTime(String startTime) {
        String[] splitted = startTime.split("[/,:]");
        this.startTime.setMonth(Integer.parseInt(splitted[0]) - 1);
        this.startTime.setDate(Integer.parseInt(splitted[1]));
        this.startTime.setHours(Integer.parseInt(splitted[2]));
        this.startTime.setMinutes(Integer.parseInt(splitted[3]));
    }

    public void setEndTime(String endTime) {
        String[] splitted = endTime.split("[/,:]");
        this.endTime.setMonth(Integer.parseInt(splitted[0]) - 1);
        this.endTime.setDate(Integer.parseInt(splitted[1]));
        this.endTime.setHours(Integer.parseInt(splitted[2]));
        this.endTime.setMinutes(Integer.parseInt(splitted[3]));

    }

    public void setOffAmount(double offAmount) {
        this.offAmount = offAmount;
    }

    //TODO : scheduledExecutorService

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
