package Main.controller;

import Main.model.Category;
import Main.model.Comment;
import Main.model.Product;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.filters.Filter;
import Main.model.requests.AddCommentRequest;
import Main.model.requests.Request;

import java.util.ArrayList;

public class GeneralController {
    public static Account currentUser = null;
    public static Product currentProduct = null;
    public static Category currentCategory = null;
    public static String currentSort = null;
    public static ArrayList<Filter> currentFilters = new ArrayList<Filter>();

    public String setCurrentProductWithId(String id) {
        //TODO wrong product id
        currentProduct = Product.getProductWithId(id);
        return "Product page opened successfully.";
    }

    public String showProductDigest() {
        return currentProduct.showProductDigest();
    }

    public String showProductAttributes() {
        return currentProduct.showProductAttributes();
    }

    public String compareProductWithProductWithName(String id) {
        return currentProduct.compareProductWithProductWithId(id);
    }

    public void addProductToCart() {

    }

    public void selectSellerWithName(String name) {

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
        //TODO implementation?
    }

    public String showCurrentSort() {
        return currentSort;
    }

    public String showFilteredAndSortedProducts() {
        String filtered = "";
        for (Product product : Filter.applyFilter(Product.allProducts)) {
            filtered = filtered.concat(product.showProductDigest());
            filtered = filtered.concat("\n");
        }
        return filtered;
    }

    public void createAccount(String type, String userName) {

    }

    public void getBuyerInformation(ArrayList<String> buyerInfo) {

    }

    public void getSellerInformation(ArrayList<String> sellerInfo) {

    }

    public void getManagerInformation(ArrayList<String> managerInfo) {

    }

    public String login(String userName, String password) {
        if (!Account.isThereUserWithUserName(userName))
            return "There is not any user with this user name.";
        else if (!Account.getUserWithUserName(userName).isPassWordCorrect(password))
            return "Password is not correct.";
        else {
            currentUser = Account.getUserWithUserName(userName);
            return "Logged in successfully.";
        }
    }

    public int recognizeUserType() {
        return 0;
    }

    public void editPersonalInfo(String field, String newContent) {

    }
}
