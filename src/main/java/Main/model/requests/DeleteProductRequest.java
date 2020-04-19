package Main.model.requests;

import Main.model.Product;

public class DeleteProductRequest extends Request {
    private Product product;
    private final String name;

    public DeleteProductRequest(Product product, String name) {
        this.product = product;
        this.name = name;
    }
}
