package Main.model.discountAndOffTypeService;

import Main.controller.GeneralController;
import Main.model.IDGenerator;
import Main.model.Product;
import Main.model.accounts.SellerAccount;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;

public class Off extends DiscountAndOffTypeService {
    private static StringBuilder lastUsedOffID ;
    private SellerAccount seller;
    private String offId;
    private ArrayList<Product> products = new ArrayList<>();
    private Date startDate;
    private Date endDate;
    private double offAmount;
    private OffStatus offStatus;
    public static ArrayList<Off> allOffs = new ArrayList<Off>();


    //TODO : trim :\\\\\\ therefore not allowed spaces some where :((((
    //TODO : Change Dates type from String to date if u can:)
    public Off(ArrayList<Product> products, String startDate, String endDate,
               double offAmount, SellerAccount seller) throws Exception {
        super(startDate, endDate);
        this.products = products;
        this.offStatus = OffStatus.PENDING_CREATION_OFF;
        this.offId = IDGenerator.getNewID(lastUsedOffID);
        this.offAmount = offAmount;
        this.seller = seller;
    }


    public void addOff(Off off) {
        allOffs.add(this);
        this.seller.addOff(this);
        addOffToProducts();
    }

    private void addOffToProducts() {
        for (Product product : products) {
            product.setOff(this);
        }
    }

    public String viewMe() {
        return
                "Id: " + offId +
                        "\n\tseller: " + seller.getCompanyName() +
                        "\n\tproducts: " + makeProductList() +
                        "\n\toff amount: " + offAmount + "%" +
                        "\n\tstart date:" + dateFormat.format(startDate) +
                        "\n\tend date: " + dateFormat.format(endDate) +
                        "\n\tstatus: " + offStatus;
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
            list.append("\n").append(product.getProductId());
        }
        return list.toString();
    }

    public static Off getOffWithId(String offId) throws Exception {
        Off foundOff = null;
        for (Off off : allOffs) {
            if (off.offId.equalsIgnoreCase(offId)) {
                foundOff = off;
            }
        }
        if (foundOff == null) {
            throw new Exception("There is no off with ID : " + offId + "\n");
        }
        if (foundOff.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
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
        product.removeOff(this);
    }

    private void removeOffFromProducts() {
        for (Product product : products) {
            product.removeOff(this);
        }
    }

    public void addProduct(Product product) {
        if (products.contains(product)) {
            products.add(product);
            product.setOff(this);
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean isThereProductWithReference(Product product) {
        return products.contains(product);
    }

    public OffStatus getOffStatus() {
        return offStatus;
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/offs.json")));
            Off[] allOff = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Off[].class);
            allOffs = (allOff == null) ? new ArrayList<>() : new ArrayList<>(asList(allOff));
            setLastUsedOffID();
            return "Read Offs Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from offs.json";
        }
    }

    public static void setLastUsedOffID() {
        if (allOffs.size() == 0) {
            lastUsedOffID = new StringBuilder("@");
        } else {
            lastUsedOffID = new StringBuilder(allOffs.get(allOffs.size() - 1).getOffId());
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/offs.json");
            Off[] allOff = new Off[allOffs.size()];
            allOff = allOffs.toArray(allOff);
            GeneralController.yagsonMapper.toJson(allOff, Off[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Offs Data Successfully.";
        } catch (IOException e) {
            return "Problem saving offs data.";
        }
    }
}
