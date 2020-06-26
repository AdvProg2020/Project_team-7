package Main.model.requests;

import Main.model.Product;
import Main.model.ProductStatus;
import Main.model.discountAndOffTypeService.Off;

import java.util.TreeSet;

public class EditProductRequest extends Request {
    private Product product;
    private TreeSet<String> editedFieldTitles = new TreeSet<>();
    private String name;
    private String brand;
    private String availability;
    private String description;
    private String price;
    private String offID;

    public EditProductRequest(Product product) {
        super();
        this.product = product;
        this.name = product.getName();
        this.brand = product.getBrand();
        this.availability = "" + product.getAvailability();
        this.description = product.getDescription();
        this.price = "" + product.getPrice();
        this.offID = (product.getOff() != null ? product.getOff().getOffId() : null);
    }

    public String showRequest() {
        return "Edit Product Request:\n" +
                "\tRequest ID: " + this.requestId + "\n" +
                "\tProduct ID: " + product.getProductId() + "\n" +
                "\tProduct Name: " + product.getName() + "\n" +
                "\tProduct Brand: " + product.getBrand() + "\n" +
                "\tEditing Following Fields:\n" + extractEditedFields();
    }

    private String extractEditedFields() {
        StringBuilder editedFields = new StringBuilder();

        for (String editedFieldTitle : editedFieldTitles) {
            if (editedFieldTitle.equalsIgnoreCase("name")) {
                editedFields.append("\tname : ").append(name).append("\n");
            } else if (editedFieldTitle.equalsIgnoreCase("price")) {
                editedFields.append("\tprice : ").append(price).append("\n");
            } else if (editedFieldTitle.equalsIgnoreCase("brand")) {
                editedFields.append("\tbrand : ").append(brand).append("\n");
            } else if (editedFieldTitle.equalsIgnoreCase("availability")) {
                editedFields.append("\tavailability : ").append(availability).append("\n");
            } else if (editedFieldTitle.equalsIgnoreCase("description")) {
                editedFields.append("\tdescription : ").append(description).append("\n");
            } else if (editedFieldTitle.equalsIgnoreCase("off")) {
                editedFields.append("\toff : ").append(offID).append("\n");
            }
        }
        return editedFields.toString();
    }

    public void acceptRequest() throws Exception {
        product.setName(name);
        product.setBrand(brand);
        product.setDescription(description);
        product.setAvailability(Integer.parseInt(availability));
        product.setPrice(Double.parseDouble(price));
        acceptOff(offID);
        product.setProductStatus(ProductStatus.APPROVED_PRODUCT);
    }

    private void acceptOff(String offID) throws Exception {
        if (offID == null) {
            product.setOff(null);
        }
        if (offID.equalsIgnoreCase("delete")) {
            product.setOff(null);
        } else {
            product.setOff(Off.getOffWithId(offID));
        }
    }

    public void declineRequest() {
        allRequests.remove(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setOffID(String offID) {
        this.offID = offID;
    }

    public void addEditedFieldTitle(String fieldTitle) {
        editedFieldTitles.add(fieldTitle);
    }

    public Product getProduct() {
        return product;
    }

    public String getName() {
        return name;
    }

    public String getAvailability() {
        return availability;
    }

    public String getPrice() {
        return price;
    }

    public String getOffID() {
        return offID;
    }
}
