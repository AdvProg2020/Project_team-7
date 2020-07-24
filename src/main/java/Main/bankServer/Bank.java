package Main.bankServer;

import java.util.ArrayList;
import java.util.HashMap;

public class Bank {

    private HashMap<String, BankTokenInfo> tokens = new HashMap<>();
    public static int TOKEN_EXPIRATION_MINUTES = 60;
    private static ArrayList<BankAccount> allAccounts = new ArrayList<>();
    private static ArrayList<Receipt> allReceipts = new ArrayList<>();
    private static ArrayList<Receipt> allPaidReceipts = new ArrayList<>();
    private static Bank bank;

    public static Bank getInstance() {
        if (bank == null) {
            bank = new Bank();
        }
        return bank;
    }

    public String createAccount(String firstName, String lastName, String username, String password, String repeatPassword){
        if(!password.equals(repeatPassword)){
            return "passwords do not match";
        }else if(!allAccounts.isEmpty() && isThereAccountWithUsername(username)){
            return "username is not available";
        }else{
            BankAccount bankAccount = new BankAccount(firstName, lastName,username,password);
            allAccounts.add(bankAccount);
            return bankAccount.getAccountId();
        }
    }

    public boolean isThereAccountWithUsername(String username){
        for (BankAccount account : allAccounts) {
            if(account.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public String getToken(String username, String password){
        if(isThereAccountWithUsername(username) && getAccountWithUsername(username).getPassword().equals(password)){
            return addToken(username,password);
        }else{
            return "invalid username or password";
        }
    }

    public BankAccount getAccountWithUsername(String username){
        for (BankAccount account : allAccounts) {
            if(account.getUsername().equals(username))
                return account;
        }
        return null;
    }

    public String createReceipt(String token, String type, String money, String sourceId, String destId, String description){
         if(!(type.equals("deposit") || type.equals("withdraw") || type.equals("move"))){
             return "invalid receipt type";
         }else if(!money.matches("\\d+") || Integer.parseInt(money)==0){
             return "invalid money";
         }else if(!isAccountIdValid(sourceId) && !sourceId.equals("-1")){
             return "source account id is invalid";
         }else if(!isAccountIdValid(destId) && !destId.equals("-1")){
             return "dest account id is invalid";
         }else if(sourceId.equals(destId)){
             return "equal source and dest account";
         }else if((type.equals("deposit") && destId.equals("-1")) || (type.equals("withdraw") && sourceId.equals("-1"))
                 || (type.equals("move") && destId.equals("-1")) || (type.equals("move") && sourceId.equals("-1"))) {
             return "invalid account id";
         }else if(!isTokenValid(token,type,sourceId,destId)){
             return "token is invalid";
         } else if(hasTokenExpired(token)){
             return "token expired";
         }else if(!isDescriptionValid(description)){
             return "your input contains invalid characters";
         }else{
             Receipt receipt = new Receipt(type,Double.parseDouble(money),sourceId,destId,description);
             allReceipts.add(receipt);
             return receipt.getReceiptId();
         }
    }

    public boolean isDescriptionValid(String description){
        if(description.contains("@") || description.contains("#") ||description.contains("%") ||
                description.contains("*") ||description.contains("$") ||description.contains("^") ||description.contains("&")){
            return false;
        }
        return true;
    }

    public boolean isAccountIdValid(String id){
        for (BankAccount account : allAccounts) {
            if(account.getAccountId().equals(id))
                return true;
        }
        return false;
    }

    public boolean isTokenValid(String token, String type, String srcId, String destId){
        if(!tokens.containsKey(token))
            return false;
        else{
            if(type.equals("deposit")){
                BankAccount bankAccount = getAccountWithUsername(getTokenInfo(token).getUsername());
                if(!bankAccount.getAccountId().equals(destId))
                    return false;
            }else{
                BankAccount bankAccount = getAccountWithUsername(getTokenInfo(token).getUsername());
                if(!bankAccount.getAccountId().equals(srcId))
                    return false;
            }
        }
        return true;
    }

    public BankTokenInfo getTokenInfo(String token) {
        for (String tokenString : tokens.keySet()) {
            if (tokenString.equals(token)) {
                return tokens.get(tokenString);
            }
        }
        return null;
    }

    public String addToken(String username, String password) {
        String token = getRandomString(5);
        BankTokenInfo bankTokenInfo = new BankTokenInfo(username,password);
        tokens.put(token, bankTokenInfo);
        return token;
    }

    private String getRandomString(int stringLength) {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvwxyz";

        StringBuilder stringBuilder = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; i++) {

            int index = (int) (alphaNumericString.length() * Math.random());
            stringBuilder.append(alphaNumericString.charAt(index));
        }

        return stringBuilder.toString();
    }

    public boolean hasTokenExpired(String token) {
        BankTokenInfo bankTokenInfo = tokens.get(token);
        if (bankTokenInfo.hasTokenExpired()) {
            return true;
        }
        return false;
    }

    public String getTransactions(String token, String type){
        if(!tokens.containsKey(token)){
            return "token is invalid";
        }else if(hasTokenExpired(token)){
            return "token expired";
        }else if(!(type.equals("+") || type.equals("-") || type.equals("*")) && (!isReceiptIdValid(type) ||
                (getReceiptWithId(type).getReceiptType().equals("deposit") &&
                        !getAccountWithUsername(getTokenInfo(token).getUsername()).getAccountId().equals(getReceiptWithId(type).getDestId())) ||
                (!getReceiptWithId(type).getReceiptType().equals("deposit") &&
                !getAccountWithUsername(getTokenInfo(token).getUsername()).getAccountId().equals(getReceiptWithId(type).getSourceId())))){
            return "invalid receipt id";
        }else{
            BankAccount bankAccount = getAccountWithUsername(getTokenInfo(token).getUsername());
            StringBuilder stringBuilder = new StringBuilder();
            if(!(type.equals("+") || type.equals("-") || type.equals("*"))){
                return getReceiptWithId(type).makeJsonList();
            }else{
                if(type.equals("+")){
                    for (Receipt receipt : bankAccount.getTransactionsToThisAccount()) {
                        stringBuilder.append(receipt.makeJsonList() + "*");
                    }
                }else if(type.equals("-")){
                    for (Receipt receipt : bankAccount.getTransactionsFromThisAccount()) {
                        stringBuilder.append(receipt.makeJsonList() + "*");
                    }
                }else if(type.equals("*")){
                    for (Receipt receipt : bankAccount.getTransactionsToThisAccount()) {
                        stringBuilder.append(receipt.makeJsonList() + "*");
                    }
                    for (Receipt receipt : bankAccount.getTransactionsFromThisAccount()) {
                        stringBuilder.append(receipt.makeJsonList() + "*");
                    }
                }
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                return stringBuilder.toString();
            }
        }
    }

    public String payReceipt(String receiptId){
        if(!isReceiptIdValid(receiptId)){
            return "invalid receipt id";
        }else if(isReceiptPaid(receiptId)){
            return "receipt is paid before";
        }else{
            Receipt receipt = getReceiptWithId(receiptId);
            if((receipt.getReceiptType().equals("deposit") && receipt.getDestId().equals("-1")) ||
                    (receipt.getReceiptType().equals("withdraw") && receipt.getSourceId().equals("-1")) ||
                    (receipt.getReceiptType().equals("move") && receipt.getDestId().equals("-1")) ||
                    (receipt.getReceiptType().equals("move") && receipt.getSourceId().equals("-1")) ||
                    isAccountIdValid(receipt.getDestId()) || isAccountIdValid(receipt.getSourceId())){
                return "invalid account id";
            }else if(!receipt.getReceiptType().equals("deposit") && getAccountWithId(receipt.getSourceId()).getBalance()<receipt.getMoney()){
                return "source account does not have enough money";
            }else{
                if(receipt.getReceiptType().equals("deposit")){
                    receipt.setSourceId("-1");
                    BankAccount bankAccount = getAccountWithId(receipt.getDestId());
                    bankAccount.setBalance(bankAccount.getBalance()+receipt.getMoney());
                    bankAccount.getTransactionsToThisAccount().add(receipt);

                }else if(receipt.getReceiptType().equals("withdraw")){
                    receipt.setDestId("-1");
                    BankAccount bankAccount = getAccountWithId(receipt.getSourceId());
                    bankAccount.setBalance(bankAccount.getBalance()-receipt.getMoney());
                    bankAccount.getTransactionsFromThisAccount().add(receipt);
                }else{
                    BankAccount sourceAccount = getAccountWithId(receipt.getSourceId());
                    BankAccount destAccount = getAccountWithId(receipt.getDestId());
                    destAccount.setBalance(destAccount.getBalance()+receipt.getMoney());
                    destAccount.getTransactionsToThisAccount().add(receipt);
                    sourceAccount.setBalance(sourceAccount.getBalance()-receipt.getMoney());
                    sourceAccount.getTransactionsFromThisAccount().add(receipt);
                }
                allPaidReceipts.add(receipt);
                allReceipts.remove(receipt);
                return "done successfully";
            }
        }
    }

    public String getBalance(String token){
        if(!tokens.containsKey(token)){
            return "token is invalid";
        }else if(hasTokenExpired(token)) {
            return "token expired";
        }else{
            return Double.toString(getAccountWithUsername(getTokenInfo(token).getUsername()).getBalance());
        }
    }

    public BankAccount getAccountWithId(String id){
        for (BankAccount account : allAccounts) {
            if(account.getAccountId().equals(id))
                return account;
        }
        return null;
    }

    public boolean isReceiptIdValid(String id){
        for (Receipt receipt : allReceipts) {
            if(receipt.getReceiptId().equals(id))
                return true;
        }
        for (Receipt receipt : allPaidReceipts) {
            if(receipt.getReceiptId().equals(id))
                return true;
        }
        return false;
    }

    public boolean isReceiptPaid(String id){
        for (Receipt receipt : allPaidReceipts) {
            if(receipt.getReceiptId().equals(id))
                return true;
        }
        return false;
    }

    public Receipt getReceiptWithId(String id){
        for (Receipt receipt : allReceipts) {
            if(receipt.getReceiptId().equals(id)){
                return receipt;
            }
        }
        return null;
    }

    public static ArrayList<BankAccount> getAllAccounts() {
        return allAccounts;
    }

    public static ArrayList<Receipt> getAllReceipts() {
        return allReceipts;
    }
}
