package Main.model.requests;

import Main.model.Product;
import Main.model.ProductStatus;

public class AddProductRequest extends Request {
    private Product product;
    private final String name;

    public AddProductRequest(Product product, String name) {
        this.product = product;
        this.name = name;
        this.requestId = Integer.toString(Request.allRequests.size()*100+1);
    }

    public String showRequest() {
        String show = "Add New Product Request:\n" +
                "Request ID: " + this.requestId + "\n" +
                product.showProductDigest() + "\n";
        return show;
    }

    public void acceptRequest() {
        product.setProductStatus(ProductStatus.APPROVED_PRODUCT);
        Product.addProduct(product);
    }

    public void declineRequest() {
        //TODO: declines' implementation
    }
}
