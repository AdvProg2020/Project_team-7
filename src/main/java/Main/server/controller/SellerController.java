package Main.server.controller;

import Main.server.model.Category;
import Main.server.model.Product;
import Main.server.model.ProductStatus;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.Off;
import Main.server.model.discountAndOffTypeService.OffStatus;
import Main.server.model.exceptions.AccountsException;
import Main.server.model.exceptions.CreateProductException;
import Main.server.model.exceptions.DiscountAndOffTypeServiceException;
import Main.server.model.logs.SellLog;
import Main.server.model.requests.*;
import Main.server.serverRequestProcessor.Server;

import java.util.ArrayList;

public class SellerController {
    public String viewCompanyInformation(String token) {
        return ((SellerAccount) Server.getServer().getTokenInfo(token).getUser()).viewCompanyInformation();
    }

    public String viewSalesHistory() {
        return ((SellerAccount) GeneralController.currentUser).viewSalesHistory();
    }

    public ArrayList<String> getSellLogIds(String token) {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<SellLog> sellHistory = ((SellerAccount) Server.getServer().getTokenInfo(token).getUser()).getSellHistory();
        if (sellHistory.isEmpty()) {
            return null;
        } else {
            for (SellLog sellLog : sellHistory) {
                arrayList.add(sellLog.getLogId());
            }
            return arrayList;
        }
    }

    public String viewLogDetails(String logId, String token) {
        try {
            return ((SellerAccount) Server.getServer().getTokenInfo(token).getUser()).getLogDetails(logId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public ArrayList<String> getSellerOffIds(String token) {
        SellerAccount sellerAccount = (SellerAccount) Server.getServer().getTokenInfo(token).getUser();
        return sellerAccount.getOffIds();
    }

    public String getOffDetails(String token, String offId) {
        SellerAccount sellerAccount = (SellerAccount) Server.getServer().getTokenInfo(token).getUser();
        return sellerAccount.getOffWithId(offId).viewMe();
    }

    public String showSellerProducts() {
        return ((SellerAccount) GeneralController.currentUser).showSellerProducts();
    }

    public String viewSellerProductWithId(String productId) {
        return ((SellerAccount) GeneralController.currentUser).viewSellerProductWithId(productId);
    }

    public EditProductRequest getProductToEdit(String productID, String token) throws Exception {
        Product product = Product.getProductWithId(productID);
        validateProductEditPermission(product, token);
        return new EditProductRequest(product);
    }

    private void validateProductEditPermission(Product product, String token) throws Exception {
        SellerAccount sellerAccount = (SellerAccount) Server.getServer().getTokenInfo(token).getUser();
        if (!sellerAccount.doesSellerHaveProductWithReference(product)) {
            throw new Exception("this product doesn't belong to you !\n");
        }
    }

    public void submitProductEdits(EditProductRequest editProductRequest, String token) throws Exception {
        validateInputEditEditProductInfo(editProductRequest, token);
        editProductRequest.getProduct().setProductStatus(ProductStatus.PENDING_EDIT_PRODUCT);
        Request.addRequest(editProductRequest);
    }

    private void validateInputEditEditProductInfo(EditProductRequest editProductRequest, String token) throws Exception {
        StringBuilder editProductErrors = new StringBuilder();

        try {
            AccountsException.validateNameTypeInfo("product name", editProductRequest.getName());
        } catch (AccountsException e) {
            editProductErrors.append(e.getErrorMessage());
        }
        try {
            CreateProductException.validateAvailability(editProductRequest.getAvailability());
        } catch (Exception e) {
            editProductErrors.append(e.getMessage());
        }
        try {
            CreateProductException.validatePrice(editProductRequest.getPrice());
        } catch (Exception e) {
            editProductErrors.append(e.getMessage());
        }
        if (editProductRequest.getOffID() != null) {
            try {
                validateEditProductEditedOff(editProductRequest.getOffID(), token);
            } catch (Exception e) {
                editProductErrors.append(e.getMessage());
            }
        }
        if (editProductErrors.length() != 0) {
            throw new Exception("there were some errors in editing product :\n" + editProductErrors.toString());
        }
    }

    private void validateEditProductEditedOff(String editedOff, String token) throws Exception {
        if (!editedOff.equalsIgnoreCase("delete")) {
            return;
        }
        Off off = Off.getOffWithId(editedOff);
        SellerAccount sellerAccount = (SellerAccount) Server.getServer().getTokenInfo(token).getUser();
        if (!sellerAccount.doesSellerHaveOffWithReference(off)) {
            throw new Exception("off with ID : " + editedOff + " doesn't belong to you!\n");
        }
    }

    public void removeProductWithID(String productID, String token) throws Exception {
        Product product = Product.getProductWithId(productID);
        validateRemoveProductPermission(product, token);
        product.removeProduct();
    }

    private void validateRemoveProductPermission(Product product, String token) throws Exception {
        SellerAccount sellerAccount = (SellerAccount) Server.getServer().getTokenInfo(token).getUser();
        if (!sellerAccount.doesSellerHaveProductWithReference(product)) {
            throw new Exception("this product doesn't belong to you!\n");
        }
    }

    public String viewBuyersOfProductWithId(String productId) throws Exception {
        return Product.getProductWithId(productId).viewBuyers();
    }

    public void addProduct(ArrayList<String> productInfo, String token) throws CreateProductException.InvalidProductInputInfo,
            CreateProductException.GetCategoryFromUser {
        validateAddProductInfo(productInfo);
        Product product = new Product(productInfo.get(0), productInfo.get(1), Integer.parseInt(productInfo.get(2)),
                productInfo.get(3), Double.parseDouble(productInfo.get(4)), (SellerAccount) Server.getServer().getTokenInfo(token).getUser());
        Request request = new AddProductRequest(product, "Add product request");
        Request.addRequest(request);

        doesNewProductsHaveInitialCategory(product, productInfo.get(5), productInfo.get(6));
    }

    private void doesNewProductsHaveInitialCategory(Product product, String doesUserWantToAddCategory, String categoryName)
            throws CreateProductException.GetCategoryFromUser {
        Category category = null;
        try {
            category = Category.getCategoryWithName(categoryName);
        } catch (Exception e) {
        }
        product.setCategory(category);
        throw new CreateProductException.GetCategoryFromUser(category, product);
    }

    public void setSpecialFeatures(String productId, ArrayList<String> specialFeatures) {
        Product product = null;
        try {
            product = Product.getProductWithId(productId);
            product.setSpecialFeatures(specialFeatures);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateAddProductInfo(ArrayList<String> productInfo) throws CreateProductException.InvalidProductInputInfo {
        StringBuilder addProductErrors = new StringBuilder();

        try {
            AccountsException.validateNameTypeInfo("product name", productInfo.get(0));
        } catch (AccountsException e) {
            addProductErrors.append(e.getErrorMessage());
        }
        try {
            CreateProductException.validateAvailability(productInfo.get(2));
        } catch (Exception e) {
            addProductErrors.append(e.getMessage());
        }
        try {
            CreateProductException.validatePrice(productInfo.get(4));
        } catch (Exception e) {
            addProductErrors.append(e.getMessage());
        }
        if (productInfo.get(5).trim().equalsIgnoreCase("yes")) {
            try {
                Category.getCategoryWithName(productInfo.get(6));
            } catch (Exception e) {
                addProductErrors.append(e.getMessage());
            }
        } else if (!productInfo.get(5).trim().equalsIgnoreCase("no")) {
            addProductErrors.append("we couldn't recognize if you want to add category or not !\nplease either write 'yes' or 'no' !\n");
        }

        if (addProductErrors.length() != 0) {
            throw new CreateProductException.InvalidProductInputInfo("there were some errors in product creation : \n" + addProductErrors);
        }
    }

    public String viewSellerOffs() {
        return ((SellerAccount) GeneralController.currentUser).viewSellerOffs();
    }

    public String viewOffWithId(String offId) throws Exception {
        return Off.getOffWithId(offId).viewMe();
    }

    public EditOffRequest getOffToEdit(String offID, String token) throws Exception {
        Off off = Off.getOffWithId(offID);
        validateOffEditPermission(off, token);
        return new EditOffRequest(off);
    }

    private void validateOffEditPermission(Off off, String token) throws Exception {
        SellerAccount sellerAccount = (SellerAccount) Server.getServer().getTokenInfo(token).getUser();
        if (!sellerAccount.doesSellerHaveOffWithReference(off)) {
            throw new Exception("this off doesn't belong to any of your products !\n");
        }
    }

    public void submitOffEdits(EditOffRequest editOffRequest, String token) throws Exception {
        validateInputEditOffInfo(editOffRequest, token);
        editOffRequest.getOff().setOffStatus(OffStatus.PENDING_EDIT_OFF);
        Request.addRequest(editOffRequest);
    }

    private void validateInputEditOffInfo(EditOffRequest editOffRequest, String token) throws Exception {
        StringBuilder editOffErrors = new StringBuilder();

        if (editOffRequest.getEditedFieldTitles().isEmpty()) {
            editOffErrors.append("you must edit at least one field!\n");
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(editOffRequest.getStartDate());
        } catch (Exception e) {
            editOffErrors.append("start date is invalid :\n").append(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(editOffRequest.getEndDate());
        } catch (Exception e) {
            editOffErrors.append("end date is invalid :\n").append(e.getMessage());
        }
        if (editOffErrors.length() == 0) {
            try {
                DiscountAndOffTypeServiceException.compareStartAndEndDate(editOffRequest.getStartDate(), editOffRequest.getEndDate());
            } catch (Exception e) {
                editOffErrors.append(e.getMessage());
            }
        }
        try {
            DiscountAndOffTypeServiceException.validateInputAmount(editOffRequest.getOffAmount());
        } catch (Exception e) {
            editOffErrors.append(e.getMessage());
        }
        try {
            validateEditOffProductsToBeAdded(editOffRequest, token);
        } catch (Exception e) {
            editOffErrors.append(e.getMessage());
        }
        try {
            validateEditOffProductsToBeRemoved(editOffRequest);
        } catch (Exception e) {
            editOffErrors.append(e.getMessage());
        }

        if (editOffErrors.length() != 0) {
            throw new Exception("there were some errors in editing off : \n" + editOffErrors);
        }

    }


    private void validateEditOffProductsToBeAdded(EditOffRequest editOffRequest, String token) throws Exception {
        StringBuilder invalidIDs = new StringBuilder();

        SellerAccount sellerAccount = (SellerAccount) Server.getServer().getTokenInfo(token).getUser();
        for (String productIDToBeAdded : editOffRequest.getProductIDsToBeAdded()) {
            Product product = null;
            try {
                product = Product.getProductWithId(productIDToBeAdded);
            } catch (Exception e) {
                invalidIDs.append(e.getMessage());
            }
            if (!sellerAccount.doesSellerHaveProductWithReference(product)) {
                invalidIDs.append("product with ID : " + productIDToBeAdded + " doesn't belong to you!\n");
            }
        }
        if (invalidIDs.length() != 0) {
            throw new Exception("there where some errors in adding products : \n" + invalidIDs);
        }
    }

    private void validateEditOffProductsToBeRemoved(EditOffRequest editOffRequest) throws Exception {
        StringBuilder invalidIDs = new StringBuilder();
        for (String productIDToBeRemoved : editOffRequest.getProductIDsToBeRemoved()) {
            try {
                Product product = Product.getProductWithId(productIDToBeRemoved);
                if (!editOffRequest.getOff().isThereProductWithReference(product)) {
                    throw new Exception("There is no product with ID : " + productIDToBeRemoved + " in off's product list !\n");
                }
            } catch (Exception e) {
                invalidIDs.append(e.getMessage());
            }
        }
        if (invalidIDs.length() != 0) {
            throw new Exception("there where some errors in removing products : \n" + invalidIDs);
        }
    }

    public void addOff(ArrayList<String> productIDs, ArrayList<String> offInfo, String token) throws Exception {
        validateOffInputInfo(productIDs, offInfo, token);
        ArrayList<Product> products = extractOffProductsList(productIDs);
        Off off = new Off(products, offInfo.get(0), offInfo.get(1), Double.parseDouble(offInfo.get(2)), (SellerAccount) Server.getServer().getTokenInfo(token).getUser());
        AddOffRequest addOffRequest = new AddOffRequest(off, "Add New Off Request");
        Request.addRequest(addOffRequest);
    }

    private void validateOffInputInfo(ArrayList<String> productIDs, ArrayList<String> offInfo, String token) throws Exception {
        StringBuilder addOffErrors = new StringBuilder();

        try {
            validateAddOffProducts(productIDs);
        } catch (Exception e) {
            addOffErrors.append(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(offInfo.get(0));
        } catch (Exception e) {
            addOffErrors.append("start date is invalid :\n" + e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(offInfo.get(1));
        } catch (Exception e) {
            addOffErrors.append("end date is invalid :\n" + e.getMessage());
        }
        if (addOffErrors.length() == 0) {
            try {
                DiscountAndOffTypeServiceException.compareStartAndEndDate(offInfo.get(0), offInfo.get(1));
            } catch (Exception e) {
                addOffErrors.append(e.getMessage());
            }
        }
        try {
            DiscountAndOffTypeServiceException.validateInputAmount(offInfo.get(2));
        } catch (Exception e) {
            addOffErrors.append(e.getMessage());
        }
        try {
            SellerAccount.getSellerWithUserName(Server.getServer().getTokenInfo(token).getUser().getUserName());
        } catch (Exception e) {
            addOffErrors.append(e.getMessage());
        }

        if (addOffErrors.length() != 0) {
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
        StringBuilder invalidProductsOffErrors = new StringBuilder();

        for (String productID : productIDs) {
            try {
                Product.getProductWithId(productID);
            } catch (Exception e) {
                invalidProductsOffErrors.append(e.getMessage());
            }
        }

        if (invalidProductsOffErrors.length() != 0) {
            throw new Exception("there were some errors in setting off products : \n" + invalidProductsOffErrors);
        }
    }

    public String viewSellerBalance(String token) {
        return ((SellerAccount) Server.getServer().getTokenInfo(token).getUser()).viewBalance();
    }

    public ArrayList<String> getSellerProductNames(String token) {
        ArrayList<String> arrayList = new ArrayList<>();
        SellerAccount sellerAccount = (SellerAccount) Server.getServer().getTokenInfo(token).getUser();
        if (sellerAccount.getProducts().isEmpty()) {
            arrayList.add("no product to show");
        } else {
            for (Product product : sellerAccount.getProducts()) {
                arrayList.add(product.getName() + "(" + product.getProductId() + ")");
            }
        }
        return arrayList;
    }

    public String makeDigestLabel(String productId) {
        try {
            Product product = Product.getProductWithId(productId);
            return "name: " + product.getName() +
                    "\n\tid: " + product.getProductId() +
                    "\n\tdescription: " + product.getDescription() +
                    "\n\tprice: " + product.getPrice() +
                    "\n\toff amount: " + product.makeOffAmount() +
                    "\n\tcategory: " + product.getCategory().getName();
        } catch (Exception e) {
            return null;
        }
    }

    public String viewProductBuyers(String productId) {
        try {
            return Product.getProductWithId(productId).viewBuyers();
        } catch (Exception e) {
            return null;
        }
    }

    public String getProductImagePath(String productId) {
        try {
            return Product.getProductWithId(productId).getImagePath();
        } catch (Exception e) {
            return null;
        }
    }

    public double getProductAverageScore(String productId) {
        try {
            return Product.getProductWithId(productId).getAverageScore();
        } catch (Exception e) {
            return 0;
        }
    }
}
