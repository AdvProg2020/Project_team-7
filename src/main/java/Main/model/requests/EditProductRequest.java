package Main.model.requests;

import Main.model.Product;

import java.util.HashMap;

public class EditProductRequest extends Request {
    private Product product;
    private HashMap<String,String> changes;
    private final String name;

    public EditProductRequest(Product product, HashMap<String, String> changes, String name) {
        this.product = product;
        this.changes = changes;
        this.name = name;
    }
}
