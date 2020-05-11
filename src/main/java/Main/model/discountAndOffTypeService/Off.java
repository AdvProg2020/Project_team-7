package Main.model.discountAndOffTypeService;

import Main.model.IDGenerator;
import Main.model.Product;
import Main.model.accounts.SellerAccount;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Off extends DiscountAndOffTypeService{
    private static StringBuilder lastUsedOffID = new StringBuilder("@");
    private SellerAccount seller;
    private String offId;
    private ArrayList<Product> products = new ArrayList<Product>();
    private Date startDate;
    private Date endDate;
    private double offAmount;
    private OffStatus offStatus;
    private static ArrayList<Off> allOffs = new ArrayList<Off>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Off(ArrayList<Product> products, OffStatus offStatus, String startDate, String endDate,
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
                        "\nstart date:" + dateFormat.format(startDate) +
                        "\nend date: " + dateFormat.format(endDate);
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

    public void setOffAmount(double offAmount) {
        this.offAmount = offAmount;
    }

    //TODO : scheduledExecutorService

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
