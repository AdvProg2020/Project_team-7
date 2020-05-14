package Main.controller;

import Main.model.*;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.model.discountAndOffTypeService.Off;
import Main.model.exceptions.AccountsException;
import Main.model.filters.*;
import Main.model.requests.AddCommentRequest;
import Main.model.requests.CreateSellerAccountRequest;
import Main.model.requests.Request;
import Main.model.sorting.ProductsSort;

import java.util.ArrayList;

public class GeneralController {
    public static Account currentUser = null;
    public static Product currentProduct = null;
    public static CartProduct currentCartProduct = null;
    public static Category currentCategory = null;
    public static String currentSort = null;
    public static ArrayList<Filter> currentFilters = new ArrayList<Filter>();
    public static String selectedUsername;

    public String setCurrentProductWithId(String id) throws Exception {
        currentProduct = Product.getProductWithId(id);
        return "Product page opened successfully.";
    }

    public String showProductDigest() {
        return currentProduct.showProductDigest();
    }

    public String showProductAttributes() {
        return currentProduct.showProductAttributes();
    }

    public String compareProductWithProductWithId(String id) throws Exception {
        return currentProduct.compareProductWithProductWithId(id);
    }

    public void addProductToCart() {
        ((BuyerAccount) currentUser).getCart().addCartProduct(currentCartProduct);
    }

    public void selectSellerWithId(String id) throws Exception {
        if(!currentProduct.getProductStatus().equals(ProductStatus.APPROVED_PRODUCT)){
            throw new Exception("this product is not available now!\n");
        }
        CartProduct cartProduct = new CartProduct(currentProduct, SellerAccount.getSellerWithUserName(id), ((BuyerAccount) currentUser).getCart());
        currentCartProduct = cartProduct;
    }

    public void addComment(String title, String content) {
        Comment comment = new Comment(((BuyerAccount) currentUser), currentProduct, title, content, ((BuyerAccount) currentUser).hasBuyerBoughtProduct(currentProduct));
        Request commentRequest = new AddCommentRequest(comment, "Add comment request");
        Request.addRequest(commentRequest);
    }

    public String showCommentsOfProduct() {
        return currentProduct.showComments();
    }

    public String showAllCategories() {
        return Category.showAllCategories();
    }

    public String showAvailableFilters() {
        String availableFilters = "";
        availableFilters = availableFilters.concat("Brand\nPrice\nName\nAvailable\n");
        if (currentCategory == null) {
            availableFilters = availableFilters.concat("Category\n");
        } else {
            for (String specialFeature : currentCategory.getSpecialFeatures()) {
                availableFilters = availableFilters.concat(specialFeature + "\n");
            }
        }
        return availableFilters;
    }

    public String createFilter(String filterType, String filterInput) {
        Filter filter = null;
        if (filterType.equalsIgnoreCase("Brand")) {
            filter = new BrandFilter(filterInput, currentCategory == null ? Product.allProducts : currentCategory.getProducts());
        } else if (filterType.equalsIgnoreCase("Price")) {
            String[] words = filterInput.split("[\\s-,_]");
            filter = new PriceRangeFilter(Double.parseDouble(words[0]), Double.parseDouble(words[1]), currentCategory == null ? Product.allProducts : currentCategory.getProducts());
        } else if (filterType.equalsIgnoreCase("Name")) {
            filter = new ProductNameFilter(filterInput, currentCategory == null ? Product.allProducts : currentCategory.getProducts());
        } else if (filterType.equalsIgnoreCase("Available")) {
            filter = new StockFilter(currentCategory == null ? Product.allProducts : currentCategory.getProducts());
        } else if (currentCategory == null && filterType.equalsIgnoreCase("Category")) {
            filter = new CategoryFilter(filterInput, Product.allProducts);
        } else if (currentCategory != null) {
            for (String specialFeature : currentCategory.getSpecialFeatures()) {
                if (filterType.equalsIgnoreCase(specialFeature))
                    filter = new SpecialFeaturesFilter(specialFeature, filterInput, currentCategory.getProducts());
            }
        }
        if (filter == null)
            return "Wrong filter selected.";
        currentFilters.add(filter);
        return "Filter was created.";
    }

    public String showCurrentFilters() {
        return Filter.showCurrentFilters();
    }

    public String disableFilter(String filterType) {
        boolean disabled = false;
        for (Filter currentFilter : currentFilters) {
            if (currentFilter.getName().equals(filterType)) {
                currentFilters.remove(currentFilter);
                disabled = true;
            }
        }
        if (disabled)
            return "Filter disabled successfully.";
        else
            return "Could not disable filter.";
    }

    public String showAvailableSorts(String listType) {
        if (listType.equalsIgnoreCase("product"))
            return "Sort by name A-Z\nSort by name Z-A\nSort by view\nSort by average score\nSort by price ascending\nSort by price descending\n";
        else if (listType.equalsIgnoreCase("request"))
            return "Sort by type\nSort by Id\n";
        else if (listType.equalsIgnoreCase("user"))
            return "Sort by first name A-Z\nSort by last name A-Z\nSort by first name Z-A\nSort by last name Z-A\n";
        return "";
    }

    public void makeSort(String sortType) {
        currentSort = sortType;
    }

    public String showCurrentSort() {
        return currentSort;
    }

    public String disableSort(){
        currentSort = null;
        return "Sort disabled";
    }

    public String showFilteredAndSortedProducts() {
        String filtered = "";
        ArrayList<Product> sorted = Filter.applyFilter(Product.allProducts);
        if (currentSort.contains("name A-Z"))
            sorted.sort(new ProductsSort.productSortByNameAscending());
        else if (currentSort.contains("name Z-A"))
            sorted.sort(new ProductsSort.productSortByNameDescending());
        else if (currentSort.contains("view"))
            sorted.sort(new ProductsSort.productSortByView());
        else if (currentSort.contains("average Score"))
            sorted.sort(new ProductsSort.productSortByRate());
        else if (currentSort.contains("price ascending"))
            sorted.sort(new ProductsSort.productSortByPriceAscendingly());
        else if (currentSort.contains("price descending"))
            sorted.sort(new ProductsSort.productSortByPriceDescendingly());
        else return "wrong sort input.";
        for (Product product : sorted) {
            filtered = filtered.concat(product.showProductDigest());
            filtered = filtered.concat("\n");
        }
        return filtered;
    }

    public String createAccount(String type, String userName) {
        if (type.equalsIgnoreCase("Manager") && !ManagerAccount.getAllManagers().isEmpty())
            return "you can not create more than one manager account directly.";
        if (Account.isThereUserWithUserName(userName))
            return "this userName is already taken.";
        else return "account Created.";
    }

    public void getBuyerInformation(ArrayList<String> buyerInfo, String userName) throws Exception {
        validateInputAccountInfo(buyerInfo, userName);
        BuyerAccount buyerAccount = new BuyerAccount(userName,
                buyerInfo.get(1),
                buyerInfo.get(2),
                buyerInfo.get(3),
                buyerInfo.get(4),
                buyerInfo.get(0),
                Double.parseDouble(buyerInfo.get(5)));
        BuyerAccount.addBuyer(buyerAccount);
    }

    public void getSellerInformation(ArrayList<String> sellerInfo, String userName) throws Exception {
        validateInputAccountInfo(sellerInfo, userName);
        SellerAccount sellerAccount = new SellerAccount(userName,
                sellerInfo.get(1),
                sellerInfo.get(2),
                sellerInfo.get(3),
                sellerInfo.get(4),
                sellerInfo.get(0),
                sellerInfo.get(5),
                sellerInfo.get(6),
                0);
        CreateSellerAccountRequest createSellerAccountRequest = new CreateSellerAccountRequest(sellerAccount, "create seller account");
        Request.addRequest(createSellerAccountRequest);
    }

    public void getManagerInformation(ArrayList<String> managerInfo, String userName) throws Exception {
        validateInputAccountInfo(managerInfo, userName);
        ManagerAccount managerAccount = new ManagerAccount(userName,
                managerInfo.get(1),
                managerInfo.get(2),
                managerInfo.get(3),
                managerInfo.get(4),
                managerInfo.get(0));
        ManagerAccount.addManager(managerAccount);
    }

    public static void validateInputAccountInfo(ArrayList<String> accountInfo, String userName) throws Exception {
        String accountCreationErrors = new String();
        try {
            AccountsException.validateUserName(userName);
        } catch (AccountsException.invalidUserNameException e) {
            accountCreationErrors.concat(e.getErrorMessage());
        }
        try {
            AccountsException.validateUsernameUniqueness(userName);
        } catch (AccountsException e) {
            accountCreationErrors.concat(e.getErrorMessage());
        }
        try {
            AccountsException.validateNameTypeInfo("first name", accountInfo.get(1));
        } catch (AccountsException e) {
            accountCreationErrors.concat(e.getErrorMessage());
        }
        try {
            AccountsException.validateNameTypeInfo("last name", accountInfo.get(2));
        } catch (AccountsException e) {
            accountCreationErrors.concat(e.getErrorMessage());
        }
        try {
            AccountsException.validateEmail(accountInfo.get(3));
        } catch (AccountsException e) {
            accountCreationErrors.concat(e.getErrorMessage());
        }
        try {
            AccountsException.validatePhoneNumber(accountInfo.get(4));
        } catch (AccountsException e) {
            accountCreationErrors.concat(e.getErrorMessage());
        }
        try {
            AccountsException.validatePassWord(accountInfo.get(0));
        } catch (AccountsException e) {
            accountCreationErrors.concat(e.getErrorMessage());
        }
        if (accountCreationErrors.isEmpty()) {
            throw new Exception("sorry there where some errors in account creation : \n" + accountCreationErrors);
        }
    }

    public String login(String userName, String password) throws Exception {
        if (!Account.isThereUserWithUserName(userName))
            return "There is not any user with this user name.";
        else if (!Account.getUserWithUserName(userName).isPassWordCorrect(password))
            return "Password is not correct.";
        else {
            currentUser = Account.getUserWithUserName(userName);
            return "Logged in successfully.";
        }
    }

    public String editPersonalInfo(String field, String newContent) {
        return currentUser.editPersonalInfo(field, newContent);
    }

    public String showAllProducts() {
        String allProducts = "";
        for (Product allProduct : Product.allProducts) {
            allProducts = allProducts.concat(allProduct.showProductDigest());
            allProducts = allProducts.concat("\n");
        }
        return allProducts;
    }

    public String showAllOffProducts() {
        String offProducts = "";
        for (Off allOff : Off.allOffs) {
            if (allOff.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
                allOff.removeOff();
            } else {
                for (Product product : allOff.getProducts()) {
                    offProducts = offProducts.concat(product.showProductDigest());
                    offProducts = offProducts.concat("\n");
                }
            }
        }
        return offProducts;
    }

    public String viewPersonalInfo() {
        return currentUser.viewMe();
    }

    public void logout() {
        currentUser = null;
    }
}
