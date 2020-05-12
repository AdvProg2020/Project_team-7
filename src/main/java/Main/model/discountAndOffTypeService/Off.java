package Main.model.discountAndOffTypeService;

import Main.model.IDGenerator;
import Main.model.Product;
import Main.model.accounts.SellerAccount;
import Main.model.exceptions.DiscountAndOffTypeServiceException;

import java.util.ArrayList;
import java.util.Date;

public class Off extends DiscountAndOffTypeService {
    private static StringBuilder lastUsedOffID = new StringBuilder("@");
    private SellerAccount seller;
    private String offId;
    private ArrayList<Product> products = new ArrayList<Product>();
    private Date startDate;
    private Date endDate;
    private double offAmount;
    private OffStatus offStatus;
    public static ArrayList<Off> allOffs = new ArrayList<Off>();

    public Off(ArrayList<Product> products, OffStatus offStatus, String startDate, String endDate,
               String offAmount, SellerAccount seller) throws Exception {
        super(startDate, endDate);
        this.products=products;
        this.offStatus= offStatus;
        this.offId = IDGenerator.getNewID(lastUsedOffID);
        DiscountAndOffTypeServiceException.validateInputAmount(offAmount);
        this.offAmount = Double.parseDouble(offAmount);
        this.seller = seller;
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
            if (off.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
                off.removeOff();
            } else {
                viewAll = viewAll.concat(off.viewMe());
            }
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
        Off foundOff = null;
        for (Off off : allOffs) {
            if (off.offId.equalsIgnoreCase(offId)){
                foundOff = off;
            }
        }
        if(foundOff==null){
            throw new Exception("There is no off with this ID !\n");
        }
        if(foundOff.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)){
            foundOff.removeOff();
            throw new Exception("this off has expired !\n");
        }
        return foundOff;
    }

    public void removeOff() {
        allOffs.remove(this);
        removeOffFromProducts();
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

    public void setOffAmount(double offAmount) {
        this.offAmount = offAmount;
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    private void removeOffFromProducts() {
        for (Product product : products) {
            product.removeOff(this);
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
