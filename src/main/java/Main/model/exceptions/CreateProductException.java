package Main.model.exceptions;

import Main.model.Category;
import Main.model.Product;

public abstract class CreateProductException extends Exception {

    protected String errorMessage;

    public static void validateAvailability(String availability) throws Exception {
        try {
            int availabilityAmount = Integer.parseInt(availability);
            if (availabilityAmount < 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("availability must be a non-negative integer !\n");
        }
    }

    public static void validatePrice(String price) throws Exception {
        try {
            double priceAmount = Double.parseDouble(price);
            if (priceAmount <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("price must be a positive double !\n");
        }
    }

    public String getMessage() {
        return errorMessage;
    }

    public static class InvalidProductInputInfo extends CreateProductException {
        public InvalidProductInputInfo(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static class GetCategoryFromUser extends CreateProductException {
        Category category;
        Product product;

        public GetCategoryFromUser(Category category, Product product) {
            this.category = category;
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }

        public Category getCategory() {
            return category;
        }
    }
}
