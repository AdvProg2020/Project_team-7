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
        return "Delete Product Request:\n" +
                "\tRequest ID: " + this.requestId + "\n" +
                "\tProduct ID: " + product.getProductId() + "\n" +
                "\tProduct Name: " + product.getName() + "\n" +
                "\tProduct Brand: " + product.getBrand() + "\n";
    }

    public void acceptRequest() {
        Product.allProducts.remove(product);
    }

    public void declineRequest() {

    }
}
