package Main.model.requests;

import Main.model.Product;
import Main.model.discountAndOffTypeService.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeSet;

public class EditOffRequest extends Request {
    private Off off;
    private String startDate;
    private String endDate;
    private String offAmount;
    //OffStatus
    //empty treeSet
    //pending edit
    //handle pending edit
    private ArrayList<String> productIDsToBeAdded;
    private ArrayList<String> productIDsToBeRemoved;
    private TreeSet<String> editedFieldTitles;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public EditOffRequest(Off off) {
        this.off = off;
        this.startDate = dateFormat.format(off.getStartDate());
        this.endDate = dateFormat.format(off.getEndDate());
        this.offAmount = "" + off.getOffAmount();
    }

    public String showRequest() {
        String show = "Edit Off Request:\n" +
                "Request ID: " + this.requestId + "\n" +
                "Off ID: " + off.getOffId() + "\n" +
                "Editing Following Fields:\n" + extractEditedFields(editedFieldTitles);
        return show;
    }

    private String extractEditedFields(TreeSet<String> editedFieldTitles) {
        String editedFields = new String();

        for (String editedFieldTitle : editedFieldTitles) {
            if (editedFieldTitle.equalsIgnoreCase("start date")) {
                editedFields.concat("\tstart date : " + startDate + "\n");
            } else if (editedFieldTitle.equalsIgnoreCase("end date")) {
                editedFields.concat("\tend date : " + endDate + "\n");
            } else if (editedFieldTitle.equalsIgnoreCase("off amount")) {
                editedFields.concat("\toff amount : " + offAmount + "\n");
            } else if (editedFieldTitle.equalsIgnoreCase("add product")) {
                editedFields.concat("\tadding products with following IDs :\n" + "\t\t" + productIDsToBeAdded.toString());
            } else if (editedFieldTitle.equalsIgnoreCase("remove product")) {
                editedFields.concat("\tadding products with following IDs :\n" + "\t\t" + productIDsToBeRemoved.toString());
            }
        }
        return editedFields;
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
