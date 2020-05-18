package Main.model.requests;

import Main.model.Product;
import Main.model.ProductStatus;

public class AddProductRequest extends Request {
    private Product product;
    private final String name;

    public AddProductRequest(Product product, String name) {
        super();
        this.product = product;
        this.name = name;
    }

    public String showRequest() {
        return "Add New Product Request:\n" +
                "\tRequest ID: " + this.requestId + "\n" +
                product.showProductDigest() + "\n";
    }

    public void acceptRequest() {
        product.setProductStatus(ProductStatus.APPROVED_PRODUCT);
        product.addProduct(product);
    }

    public void declineRequest() {
        allRequests.remove(this);
    }
}
