package Main.bankServer;

public class Receipt {

    private String receiptType;
    private double money;
    private String sourceId;
    private String destId;
    private String description;
    private String receiptId;

    public Receipt(String receiptType, double money, String sourceId, String destId, String description) {
        this.receiptType = receiptType;
        this.money = money;
        this.sourceId = sourceId;
        this.destId = destId;
        this.description = description;
        this.receiptId = generateAccountId();
    }

    public String generateAccountId() {
        if (Bank.getAllReceipts().isEmpty())
            return "100";
        else {
            int lastUsedId = Integer.parseInt(Bank.getAllReceipts().get(Bank.getAllReceipts().size() - 1).getReceiptId());
            return Integer.toString(lastUsedId + 1);
        }
    }

    public String getReceiptId() {
        return receiptId;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public double getMoney() {
        return money;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getDestId() {
        return destId;
    }

    public String getDescription() {
        return description;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String makeJsonList(){
        return "{\"receiptType\":\"" + receiptType + "\"," +
                "\"money\":" + money + "," +
                "\"sourceAccountID\":" + sourceId + "," +
                "\"destAccountID\":" + destId + "," +
                "\"description\":\"" + description + "\"," +
                "\"id\":" + receiptId + "," +
                "\"paid\":1}";
    }
}
