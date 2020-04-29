package Main.controller;

import Main.model.Category;
import Main.model.Product;
import Main.model.accounts.Account;
import Main.model.filters.Filter;

import java.util.ArrayList;

public class GeneralController {
    public Account currentUser = null;
    public Product currentProduct = null;
    public Category currentCategory = null;
    public String currentSort = null;
    public ArrayList<Filter> currentFilters = new ArrayList<Filter>();

    public void setCurrentProductWithId(String id) {

    }

    public String showProductDigest() {
        return null;
    }

    public String showProductAttributes() {
        return null;
    }

    public String compareProductWithProductWithName(String name) {
        return null;
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
        return null;
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

    public void login(String userName, String password) {

    }

    public int recognizeUserType() {
        return 0;
    }

    public void editPersonalInfo(String field, String newContent) {

    }
}
