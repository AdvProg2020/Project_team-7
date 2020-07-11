package Main.server.model.requests;

import Main.server.model.Product;
import Main.server.model.discountAndOffTypeService.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeSet;

public class EditOffRequest extends Request {
    private Off off;
    private String startDate;
    private String endDate;
    private String offAmount;
    private ArrayList<String> productIDsToBeAdded = new ArrayList<>();
    private ArrayList<String> productIDsToBeRemoved = new ArrayList<>();
    private TreeSet<String> editedFieldTitles = new TreeSet<>();
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public EditOffRequest(Off off) {
        super();
        this.off = off;
        this.startDate = dateFormat.format(off.getStartDate());
        this.endDate = dateFormat.format(off.getEndDate());
        this.offAmount = "" + off.getOffAmount();
    }

    public String showRequest() {
        return "Edit Off Request:\n" +
                "\tRequest ID: " + this.requestId + "\n" +
                "\tOff ID: " + off.getOffId() + "\n" +
                "\tEditing Following Fields:\n" + extractEditedFields();
    }

    private String extractEditedFields() {
        StringBuilder editedFields = new StringBuilder();

        for (String editedFieldTitle : editedFieldTitles) {
            if (editedFieldTitle.equalsIgnoreCase("start date")) {
                editedFields.append("\tstart date : ").append(startDate).append("\n");
            } else if (editedFieldTitle.equalsIgnoreCase("end date")) {
                editedFields.append("\tend date : ").append(endDate).append("\n");
            } else if (editedFieldTitle.equalsIgnoreCase("off amount")) {
                editedFields.append("\toff amount : ").append(offAmount).append("\n");
            } else if (editedFieldTitle.equalsIgnoreCase("add product")) {
                editedFields.append("\tadding products with following IDs :\n" + "\t\t").append(productIDsToBeAdded.toString());
            } else if (editedFieldTitle.equalsIgnoreCase("remove product")) {
                editedFields.append("\tadding products with following IDs :\n" + "\t\t").append(productIDsToBeRemoved.toString());
            }
        }
        return editedFields.toString();
    }


    public void acceptRequest() throws Exception {
        off.setDates(startDate, endDate);
        off.setOffAmount(Double.parseDouble(offAmount));
        acceptProductsToBeAdded();
        acceptProductsToBeRemoved();
        off.setOffStatus(OffStatus.APPROVED_OFF);
    }

    private void acceptProductsToBeAdded() throws Exception {
        for (String productID : productIDsToBeAdded) {
            off.addProduct(Product.getProductWithId(productID));
        }
    }

    private void acceptProductsToBeRemoved() throws Exception {
        for (String productID : productIDsToBeRemoved) {
            off.removeProduct(Product.getProductWithId(productID));
        }
    }

    public void declineRequest() {
        allRequests.remove(this);
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setOffAmount(String offAmount) {
        this.offAmount = offAmount;
    }

    public void addProductIDToBeAdded(String productID) {
        productIDsToBeAdded.add(productID);
    }

    public void addProductIDToBeRemoved(String productID) {
        productIDsToBeRemoved.add(productID);
    }

    public void addEditedFieldTitle(String fieldTitle) {
        editedFieldTitles.add(fieldTitle);
    }

    public Off getOff() {
        return off;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getOffAmount() {
        return offAmount;
    }

    public ArrayList<String> getProductIDsToBeAdded() {
        return productIDsToBeAdded;
    }

    public ArrayList<String> getProductIDsToBeRemoved() {
        return productIDsToBeRemoved;
    }

    public TreeSet<String> getEditedFieldTitles() {
        return editedFieldTitles;
    }
}
