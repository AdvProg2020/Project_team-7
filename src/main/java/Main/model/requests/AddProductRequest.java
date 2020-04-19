package Main.model.requests;

import Main.model.Product;

public class AddProductRequest extends Request {
    private Product product;
    private final String name;

    public AddProductRequest(Product product, String name) {
        this.product = product;
        this.name = name;
    }
}
