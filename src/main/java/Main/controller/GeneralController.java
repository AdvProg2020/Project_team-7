package Main.controller;

import Main.Main;
import Main.model.*;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.model.discountAndOffTypeService.Off;
import Main.model.filters.Filter;
import Main.model.requests.AddCommentRequest;
import Main.model.requests.CreateSellerAccountRequest;
import Main.model.requests.Request;
import Main.model.sorting.ProductsSort;
import java.util.ArrayList;

public class GeneralController {
    public static Account currentUser = null;
    public static Product currentProduct = null;
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
        //CartProduct cartProduct = new CartProduct(currentProduct, , ((BuyerAccount) currentUser).getCart());
    }

    public void selectSellerWithId(String id) {

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
        return null;
    }

    public void createFilter(String filterType) {
        Filter filter;
        /*if (filterType.equalsIgnoreCase("Brand"))
            filter = new B*/
    }

    public String showCurrentFilters() {
        return Filter.showCurrentFilters();
    }

    public String disableFilter(String filterType) {
        for (Filter currentFilter : currentFilters) {
            if (currentFilter.getName().equals(filterType)) {
                currentFilters.remove(currentFilter);
                return "Filter disabled successfully.";
            }
        }
        return "Could not disable filter.";
    }

    public String showAvailableSorts() {
        return null;
    }

    public void makeSort(String sortType) {
        currentSort = sortType;
    }

    public String showCurrentSort() {
        return currentSort;
    }

    public String showFilteredAndSortedProducts() {
        String filtered = "";
        ArrayList<Product> sorted = Filter.applyFilter(Product.allProducts);
        if (currentSort.equals("Name A-Z"))
            sorted.sort(new ProductsSort.productSortByNameAscending());
        else if (currentSort.equals("Name Z-A"))
            sorted.sort(new ProductsSort.productSortByNameDescending());
        else if (currentSort.equals("Views"))
            sorted.sort(new ProductsSort.productSortByView());
        else if (currentSort.equals("Product Score"))
            sorted.sort(new ProductsSort.productSortByRate());
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
        ManagerAccount managerAccount = new ManagerAccount(userName,
                managerInfo.get(1),
                managerInfo.get(2),
                managerInfo.get(3),
                managerInfo.get(4),
                managerInfo.get(0));
        ManagerAccount.addManager(managerAccount);
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

    public String viewPersonalInfo(){
        return currentUser.viewMe();
    }

    public void logout(){
        currentUser = null;
    }
}
