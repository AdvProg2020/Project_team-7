package Main.model.requests;

import Main.model.Product;

public class DeleteProductRequest extends Request {
    private Product product;
    private final String name;

    public DeleteProductRequest(Product product, String name) {
        super();
        this.product = product;
        this.name = name;
    }

    public String showRequest() {
        String show = "Delete Product Request:\n" +
                "Request ID: " + this.requestId + "\n" +
                "Product ID: " + product.getProductId() + "\n" +
                "Product Name: " + product.getName() + "\n" +
                "Product Brand: " + product.getBrand() + "\n";
        return show;
    }

    public void acceptRequest() {
        Product.allProducts.remove(product);
    }

    public void declineRequest() {

    }
}
