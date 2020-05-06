package Main.controller;

import Main.model.Category;
import Main.model.Product;
import Main.model.accounts.Account;
import Main.model.filters.Filter;

import java.util.ArrayList;

public class GeneralController {
    public static Account currentUser = null;
    public static Product currentProduct = null;
    public static Category currentCategory = null;
    public static String currentSort = null;
    public static ArrayList<Filter> currentFilters = new ArrayList<Filter>();

    public void setCurrentProductWithId(String id) {

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

    }

    public String showCommentsOfProduct() {
        return null;
    }

    public String showAllCategories() {
        return null;
    }

    public String showAvailableFilters() {
        return null;
    }

    public void createFilter(String filterType) {

    }

    public String showCurrentFilters() {
        return Filter.showCurrentFilters();
    }

    public void disableFilter(String filterType) {

    }

    public String showAvailableSorts() {
        return null;
    }

    public void makeSort(String sortType) {

    }

    public String showCurrentSort() {
        return null;
    }

    public String showFilteredAndSortedProducts() {
        return null;
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
