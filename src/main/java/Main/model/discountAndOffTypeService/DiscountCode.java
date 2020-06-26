package Main.model.discountAndOffTypeService;

import Main.controller.GeneralController;
import Main.model.IDGenerator;
import Main.model.accounts.BuyerAccount;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.time.DateUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static java.util.Arrays.asList;

public class DiscountCode extends DiscountAndOffTypeService {
    private static StringBuilder lastUsedCodeID;
    private String code;
    private double percent;
    private double maxAmount;
    private int maxNumberOfUse;
    private HashMap<BuyerAccount, Integer> users = new HashMap<>();
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();

    private HashMap<String, Integer> usersStringRecord = new HashMap<>();

    public DiscountCode(String startDate, String endDate, String percent, String maxAmount, String maxNumberOfUse, ArrayList<BuyerAccount> users) throws Exception {
        super(startDate, endDate);
        this.code = IDGenerator.getNewID(lastUsedCodeID);
        this.percent = Double.parseDouble(percent);
        this.maxAmount = Double.parseDouble(maxAmount);
        this.maxNumberOfUse = Integer.parseInt(maxNumberOfUse);
        setUsers(users);
    }

    public static ArrayList<String> getDiscountsList(){
        ArrayList<String> list = new ArrayList<>();
        for (DiscountCode discountCode : allDiscountCodes) {
            String info = "@"+discountCode.code+" \t\t\t\t"+discountCode.percent;
            list.add(info);
        }
        return list;
    }

    public double getPercent() {
        return percent;
    }

    public void setUsers(ArrayList<BuyerAccount> buyers) {
        for (BuyerAccount buyer : buyers) {
            users.put(buyer, maxNumberOfUse);
        }
    }

    public void addDiscountCode() {
        allDiscountCodes.add(this);
        for (BuyerAccount buyer : users.keySet()) {
            buyer.addDiscountCode(this);
        }
    }

    public static String showAllDiscountCodes() {
        StringBuilder list = new StringBuilder();
        list.append("List of discount codes:");
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
                discountCode.removeDiscountCode();
            } else {
                list.append("\n").append(discountCode.getCode());
            }
        }
        if (list.toString().equals("List of discount codes:"))
            return "No discounts to show.";
        return list.toString();
    }

    public String viewMeAsManager() {
        return
                "code: " + code +
                        "\n\tpercent: " + percent + "%" +
                        "\n\tmaximum amount to be decreased: " + maxAmount +
                        "\n\tmaximum number of use for each user: " + maxNumberOfUse +
                        "\n\tlist of users who have this discount code: " + makeListOfBuyers() +
                        "\n\tstart date:" + dateFormat.format(startDate) +
                        "\n\tend date: " + dateFormat.format(endDate) + "\n";
    }

    public String viewMeAsBuyer(BuyerAccount buyerAccount) {
        return
                "code: " + code +
                        "\n\tpercent: " + percent + "%" +
                        "\n\tmaximum amount to be decreased: " + maxAmount +
                        "\n\tmaximum number of use for each user: " + maxNumberOfUse +
                        "\n\ttimes you have used this code so far : " + users.get(buyerAccount) +
                        "\n\tstart date:" + dateFormat.format(startDate) +
                        "\n\tend date: " + dateFormat.format(endDate) + "\n";
    }

    public String makeListOfBuyers() {
        StringBuilder list = new StringBuilder();
        for (BuyerAccount user : users.keySet()) {
            list.append("\n").append(user.getUserName());
        }
        return list.toString();
    }

    public static DiscountCode getDiscountCodeWithCode(String code) throws Exception {
        DiscountCode foundDiscountCode = null;
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.code.equals(code)) {
                foundDiscountCode = discountCode;
            }
        }
        if (foundDiscountCode == null) {
            throw new Exception("There is no discount code with code : " + code + "\n");
        }
        if (foundDiscountCode.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
            foundDiscountCode.removeDiscountCode();
            throw new Exception("this discount code has expired !\n");
        }
        return foundDiscountCode;
    }

    public void removeDiscountCode() {
        allDiscountCodes.remove(this);
        for (BuyerAccount buyerAccount : users.keySet()) {
            buyerAccount.removeDiscountCode(this);
        }
    }

    public String getCode() {
        return code;
    }

    public void removeUser(BuyerAccount buyerAccount) {
        users.remove(buyerAccount);
        buyerAccount.removeDiscountCode(this);
    }

    public double getDiscountCodeAmount() {
        return percent;
    }

    public void removeDiscountCodeIfBuyerHasUsedUpDiscountCode(BuyerAccount buyerAccount) {
        if (users.get(buyerAccount) == maxNumberOfUse) {
            this.removeUser(buyerAccount);
            buyerAccount.removeDiscountCode(this);
        }
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setMaxNumberOfUse(int maxNumberOfUse) {
        this.maxNumberOfUse = maxNumberOfUse;
    }

    public void addUser(BuyerAccount buyerAccount) {
        if (!users.containsKey(buyerAccount)) {
            users.put(buyerAccount, 0);
            buyerAccount.addDiscountCode(this);
        }
    }

    public boolean isThereBuyerWithReference(BuyerAccount buyerAccount) {
        return users.containsKey(buyerAccount);
    }

    public int getMaxNumberOfUse() {
        return maxNumberOfUse;
    }

    public static void giveBonusDiscountCodeToSpecialBuyers() throws Exception {
        Date startDate = new Date();
        if (!DateUtils.isSameDay(startDate, dateFormat.parse("2000/05/04 12:05:04"))) {
            return;
        }
        ArrayList<BuyerAccount> levelTwoBuyers = new ArrayList<>();
        ArrayList<BuyerAccount> levelOneBuyers = new ArrayList<>();
        setSpecialBuyers(levelOneBuyers, levelTwoBuyers);
        if (levelOneBuyers.size() != 0) {
            DiscountCode discountCodeLevelOne = new DiscountCode(dateFormat.format(startDate),
                    dateFormat.format(getDateForAfterWeeks(startDate, 2)), "20",
                    "40", "1", levelOneBuyers);
            discountCodeLevelOne.addDiscountCode();
        }
        if (levelTwoBuyers.size() != 0) {
            DiscountCode discountCodeLevelTwo = new DiscountCode(dateFormat.format(startDate),
                    dateFormat.format(getDateForAfterWeeks(startDate, 4)), "40",
                    "60", "2", levelTwoBuyers);
            discountCodeLevelTwo.addDiscountCode();
        }
    }

    private static void setSpecialBuyers(ArrayList<BuyerAccount> levelOneBuyers, ArrayList<BuyerAccount> levelTwoBuyers) {
        for (BuyerAccount buyer : BuyerAccount.getAllBuyers()) {
            int buyerBonusLevel = buyer.getBuyerBonusLevel();
            if (buyerBonusLevel == 2) {
                levelTwoBuyers.add(buyer);
            } else if (buyerBonusLevel == 1) {
                levelOneBuyers.add(buyer);
            }
        }
    }

    private static Date getDateForAfterWeeks(Date startDate, int numberOfWeeksToBeAdded) {
        return DateUtils.addWeeks(startDate, numberOfWeeksToBeAdded);
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/discounts.json")));
            DiscountCode[] allDis = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, DiscountCode[].class);
            allDiscountCodes = (allDis == null) ? new ArrayList<>() : new ArrayList<>(asList(allDis));
            setLastUsedCodeID();
            return "Read Discounts Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from discounts.json";
        }
    }

    public static void setLastUsedCodeID() {
        if (allDiscountCodes.size() == 0) {
            lastUsedCodeID = new StringBuilder("@");
        } else {
            lastUsedCodeID = new StringBuilder(allDiscountCodes.get(allDiscountCodes.size() - 1).getCode());
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/discounts.json");
            DiscountCode[] allDis = new DiscountCode[allDiscountCodes.size()];
            allDis = allDiscountCodes.toArray(allDis);
            GeneralController.yagsonMapper.toJson(allDis, DiscountCode[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Discounts Data Successfully.";
        } catch (IOException e) {
            return "Problem saving discounts data.";
        }
    }

    public static void setStringRecordObjects() {
        try {
            setStringRecordUsers();
        } catch (Exception e) {
        }
    }

    private static void setStringRecordUsers() throws Exception {
        for (DiscountCode discountCode : allDiscountCodes) {
            discountCode.users.clear();
            for (String buyerUserName : discountCode.usersStringRecord.keySet()) {
                discountCode.users.put(BuyerAccount.getBuyerWithUserName(buyerUserName), discountCode.usersStringRecord.get(buyerUserName));
            }
        }
    }

    public static void getObjectStringRecords() {
        getUsersStringRecord();
    }

    private static void getUsersStringRecord() {
        for (DiscountCode discountCode : allDiscountCodes) {
            discountCode.usersStringRecord.clear();
            for (BuyerAccount buyerAccount : discountCode.users.keySet()) {
                discountCode.usersStringRecord.put(buyerAccount.getUserName(), discountCode.users.get(buyerAccount));
            }
        }
    }

}
