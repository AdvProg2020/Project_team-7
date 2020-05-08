package Main.model;

import Main.model.accounts.SellerAccount;

import java.sql.Time;
import java.util.ArrayList;

public class Off {
    private static StringBuilder lastUsedOffID = new StringBuilder("@");
    private SellerAccount seller;
    private String offId;
    private ArrayList<Product> products = new ArrayList<Product>();
    private Time startTime;
    private Time endTime;
    private double offAmount;
    private OffStatus offStatus;
    private static ArrayList<Off> allOffs = new ArrayList<Off>();

    public Off(ArrayList<Product> products, OffStatus offStatus, Time startTime, Time endTime,
               double offAmount, SellerAccount seller) {
        this.offId = IDGenerator.getNewID(lastUsedOffID);
//TODO

    }

    public static void addOff(Off off) {
        allOffs.add(off);
    }


    public String viewMe() {
        return
                "Id: " + offId +
                        "\nseller: " + seller.getCompanyName() +
                        "\nproducts: " + makeProductList() +
                        "\noff amount: " + offAmount + "%" +
                        "\nstart time:" + startTime.toString() +
                        "\nend time: " + endTime.toString();
    }

    public static String viewAllOffs() {
        String viewAll = "";
        for (Off off : allOffs) {
            viewAll = viewAll.concat(off.viewMe());
        }
        return viewAll;
    }

    public String makeProductList() {
        StringBuilder list = new StringBuilder();
        for (Product product : products) {
            list.append("\n" + product.getProductId());
        }
        return list.toString();
    }

    public static Off getOffWithId(String offId) throws Exception {
        for (Off off : allOffs) {
            if (off.offId.equalsIgnoreCase(offId))
                return off;
        }
        throw new Exception("There is no off with this ID !\n");
    }

    public static void removeOff(Off off) {
        allOffs.remove(off);
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

    //time format: 2020/2/27,14:50
    public void setStartTime(String startTime) {
        String[] splitted = startTime.split("[/,:]");
        this.startTime.setYear(Integer.parseInt(splitted[0]));
        this.startTime.setMonth(Integer.parseInt(splitted[1]) - 1);
        this.startTime.setDate(Integer.parseInt(splitted[2]));
        this.startTime.setHours(Integer.parseInt(splitted[3]));
        this.startTime.setMinutes(Integer.parseInt(splitted[4]));
    }

    public void setEndTime(String endTime) {
        String[] splitted = endTime.split("[/,:]");
        this.endTime.setYear(Integer.parseInt(splitted[0]));
        this.endTime.setMonth(Integer.parseInt(splitted[1]) - 1);
        this.endTime.setDate(Integer.parseInt(splitted[2]));
        this.endTime.setHours(Integer.parseInt(splitted[3]));
        this.endTime.setMinutes(Integer.parseInt(splitted[4]));

    }

    public void setOffAmount(double offAmount) {
        this.offAmount = offAmount;
    }

    //TODO : scheduledExecutorService

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
