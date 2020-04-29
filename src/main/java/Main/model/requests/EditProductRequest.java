package Main.model.requests;

import Main.model.Product;
import Main.model.ProductStatus;

import java.util.HashMap;

public class EditProductRequest extends Request {
    private Product product;
    private HashMap<String, String> changes;
    private final String name;

    public EditProductRequest(Product product, HashMap<String, String> changes, String name) {
        product.setProductStatus(ProductStatus.PENDING_EDIT_PRODUCT);
        this.product = product;
        this.changes = changes;
        this.name = name;
    }

    public String showRequest() {
        String show = "Edit Product Request:\n" +
                "Request ID: " + this.requestId + "\n" +
                "Product ID: " + product.getProductId() + "\n" +
                "Product Name: " + product.getName() + "\n" +
                "Product Brand: " + product.getBrand() + "\n" +
                "Editing Following Fields:\n";
        for (String s : changes.keySet()) {
            show.concat("edit " + s + " to " + changes.get(s) + "\n");
        }
        return show;
    }

    public void acceptRequest() {
        for (String changeField : changes.keySet()) {
            if (changeField.equals("productId")) {
                //TODO exception you are not allowed to change this
            } else if (changeField.equals("name"))
                product.setName(changes.get(changeField));
            else if (changeField.equals("brand"))
                product.setBrand(changes.get(changeField));
            else if (changeField.equals("availability"))
                product.setAvailability(Integer.parseInt(changes.get(changeField)));
            else if (changeField.equals("category")) {
                /*TODO product.changeCategoryTo(changes.get(changeField));
                Or you are not allowed*/
            } else if (changeField.equals("description"))
                product.setDescription(changes.get(changeField));
            else if (changeField.equals("price"))
                product.setPrice(Double.parseDouble(changes.get(changeField)));
            else {
                /*TODO edit special features
                if not found, throw exception for wrong edit request*/
            }
        }
        product.setProductStatus(ProductStatus.APPROVED_PRODUCT);
    }

    public void declineRequest() {
        product.setProductStatus(ProductStatus.APPROVED_PRODUCT);
    }
}
