package Main.model;

import java.sql.Time;
import java.util.ArrayList;

public class Off {
    private String offId;
    private ArrayList<Product> products = new ArrayList<Product>();
    private Time startTime;
    private Time endTime;
    private double offAmount;
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
}
