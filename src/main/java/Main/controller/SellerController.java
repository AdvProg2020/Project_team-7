package Main.controller;

import Main.model.Category;
import Main.model.Product;
import Main.model.accounts.SellerAccount;
import Main.model.discountAndOffTypeService.Off;
import Main.model.exceptions.DiscountAndOffTypeServiceException;
import Main.model.requests.AddOffRequest;
import Main.model.requests.AddProductRequest;
import Main.model.requests.Request;

import java.util.ArrayList;

public class SellerController {
    public String viewCompanyInformation() {
        return ((SellerAccount) GeneralController.currentUser).viewCompanyInformation();
    }

    public String viewSalesHistory() {
        return ((SellerAccount) GeneralController.currentUser).viewSalesHistory();
    }

    public String showSellerProducts() {
        return ((SellerAccount) GeneralController.currentUser).showSellerProducts();
    }

    public String viewSellerProductWithId(String productId) {
        return ((SellerAccount) GeneralController.currentUser).viewSellerProductWithId(productId);
    }

    public void editProductWithId(String productId) {

    }

    public String viewBuyersOfProductWithId(String productId) throws Exception {
        return Product.getProductWithId(productId).viewBuyers();
    }

    public void addProduct(ArrayList<String> productInfo) throws Exception {
        Product product = new Product(productInfo.get(0), productInfo.get(1), Integer.parseInt(productInfo.get(2)), Category.getCategoryWithName(productInfo.get(3)), productInfo.get(4), null, Double.parseDouble(productInfo.get(5)), ((ArrayList) productInfo.subList(6, productInfo.size())));
        Request request = new AddProductRequest(product, "Add product request");
        Request.addRequest(request);
    }

    public String viewSellerOffs() {
        return ((SellerAccount) GeneralController.currentUser).viewSellerOffs();
    }

    public String viewAllOffs() {
        return Off.viewAllOffs();
    }

    public String viewOffWithId(String offId) throws Exception {
        return Off.getOffWithId(offId).viewMe();
    }

    public void editOffWithId(String offId) {

    }

    public void addOff(ArrayList<String> productIDs, ArrayList<String> offInfo) throws Exception {
        validateOffInputInfo(productIDs,offInfo);
        ArrayList<Product> products = extractOffProductsList(productIDs);
        Off off = new Off(products, offInfo.get(0), offInfo.get(1), Double.parseDouble(offInfo.get(2)), SellerAccount.getSellerWithUserName(offInfo.get(3)));
        AddOffRequest addOffRequest = new AddOffRequest(off, "Add New Off Request");
        Request.addRequest(addOffRequest);
    }

    private void validateOffInputInfo(ArrayList<String> productIDs, ArrayList<String> offInfo) throws Exception {
        String addOffErrors = new String();

        try {
            validateAddOffProducts(productIDs);
        } catch (Exception e) {
            addOffErrors.concat(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(offInfo.get(0));
        } catch (Exception e) {
            addOffErrors.concat("start date is invalid :\n" + e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(offInfo.get(1));
        } catch (Exception e) {
            addOffErrors.concat("end date is invalid :\n" + e.getMessage());
        }
        if (addOffErrors.isEmpty()) {
            try {
                DiscountAndOffTypeServiceException.compareStartAndEndDate(offInfo.get(0), offInfo.get(1));
            } catch (Exception e) {
                addOffErrors.concat(e.getMessage());
            }
        }
        try {
            DiscountAndOffTypeServiceException.validateInputAmount(offInfo.get(2));
        } catch (Exception e) {
            addOffErrors.concat(e.getMessage());
        }
        try {
            SellerAccount.getSellerWithUserName(offInfo.get(3));
        } catch (Exception e) {
            addOffErrors.concat(e.getMessage());
        }

        if (!addOffErrors.isEmpty()) {
            throw new Exception("there were some errors in adding Off : \n" + addOffErrors);
        }
    }

    private ArrayList<Product> extractOffProductsList(ArrayList<String> productIDs) throws Exception {
        ArrayList<Product> products = new ArrayList<>();
        for (String productID : productIDs) {
            products.add(Product.getProductWithId(productID));
        }
        return products;
    }

    private void validateAddOffProducts(ArrayList<String> productIDs) throws Exception {
        String invalidProductsOffErrors = new String();

        for (String productID : productIDs) {
            try {
                Product.getProductWithId(productID);
            } catch (Exception e) {
                invalidProductsOffErrors.concat(e.getMessage());
            }
        }

        if (!invalidProductsOffErrors.isEmpty()) {
            throw new Exception("there were some errors in setting off products : \n" + invalidProductsOffErrors);
        }
    }

    public String viewSellerBalance() {
        return ((SellerAccount) GeneralController.currentUser).viewBalance();
    }
}
