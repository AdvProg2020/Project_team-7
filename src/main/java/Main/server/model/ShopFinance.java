package Main.server.model;

import Main.server.controller.GeneralController;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;

public class ShopFinance {
    private double minimumWalletBalance;
    private double commission;
    private String accountID;
    private String username;
    private String password;
    private static ShopFinance shopFinance;

    public static ShopFinance getInstance(){
        if(shopFinance == null){
            shopFinance = new ShopFinance();
        }
        return shopFinance;
    }

    public void setMinimumWalletBalance(double minimumWalletBalance) {
        this.minimumWalletBalance = minimumWalletBalance;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/finance.json")));
            shopFinance = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, ShopFinance.class);
            return "Read ShopFinance Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from finance.json";
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/finance.json");
            GeneralController.yagsonMapper.toJson(ShopFinance.class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved ShopFinance Data Successfully.";
        } catch (IOException e) {
            return "Problem saving finance data.";
        }
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
