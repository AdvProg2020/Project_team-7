package Main.server.controller;

import Main.server.model.*;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.server.model.discountAndOffTypeService.DiscountCode;
import Main.server.model.discountAndOffTypeService.Off;
import Main.server.model.exceptions.AccountsException;
import Main.server.model.filters.*;
import Main.server.model.logs.Log;
import Main.server.model.requests.AddCommentRequest;
import Main.server.model.requests.CreateSellerAccountRequest;
import Main.server.model.requests.Request;
import Main.server.model.sorting.ProductsSort;
import Main.server.serverRequestProcessor.Server;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.FileWriter;
import java.util.ArrayList;

public class GeneralController {
    public static Account currentUser = null;
    public static Product currentProduct = null;
    public static CartProduct currentCartProduct = null;
    public static Category currentCategory = null;
    public static String currentProductSort = "";
    public static String currentUserSort = "";
    public static String currentRequestSort = "";
    public static ArrayList<Filter> currentFilters = new ArrayList<>();
    public static String selectedUsername;
    public static YaGson yagsonMapper = new YaGsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    public static JsonReader jsonReader;
    public static FileWriter fileWriter;

    public String setCurrentProductWithId(String id) throws Exception {
        currentProduct = Product.getProductWithId(id);
        return "Product page opened successfully.";
    }

    public void giveDiscountCodeToSpecialBuyers() throws Exception {
        DiscountCode.giveBonusDiscountCodeToSpecialBuyers();
    }

    public String showProductDigest() {
        return currentProduct.showProductDigest();
    }

    public String showProductAttributes() {
        return currentProduct.showProductAttributes();
    }

    public String compareProductWithProductWithId(String firstId, String id) throws Exception {
        return Product.getProductWithId(firstId).compareProductWithProductWithId(id);
    }

    public String addProductToCart(CartProduct cartProduct, String token) {
        ((BuyerAccount) Server.getServer().getTokenInfo(token).getUser()).getCart().addCartProduct(cartProduct);
        return "product added to cart successfully.";
    }

    public CartProduct selectSellerWithUsername(String username, String token, String productId) throws Exception {
        if (!currentProduct.getProductStatus().equals(ProductStatus.APPROVED_PRODUCT)) {
            throw new Exception("this product is not available now!\n");
        }
        return new CartProduct(Product.getProductWithId(productId), SellerAccount.getSellerWithUserName(username),
                ((BuyerAccount) Server.getServer().getTokenInfo(token).getUser()).getCart());
    }

    public void addComment(String title, String content, String token, String id) throws Exception {
        validateInputCommentInfo(token);
        Comment comment = new Comment(((BuyerAccount) Server.getServer().getTokenInfo(token).getUser()), Product.getProductWithId(id), title, content,
                ((BuyerAccount) Server.getServer().getTokenInfo(token).getUser()).hasBuyerBoughtProduct(Product.getProductWithId(id)));
        Request commentRequest = new AddCommentRequest(comment, "Add comment request");
        Request.addRequest(commentRequest);
    }

    private void validateInputCommentInfo(String token) throws Exception {
        if (Server.getServer().getTokenInfo(token).getUser() == null) {
            throw new Exception("you must sign in then comment on a product !\n");
        }
        if (!(Server.getServer().getTokenInfo(token).getUser() instanceof BuyerAccount)) {
            throw new Exception("only buyers can comment on products !\n");
        }
    }

    public String showCommentsOfProduct(String productId) {
        try {
            Product product = Product.getProductWithId(productId);
            return product.showComments();
        } catch (Exception e) {
            return "-";
        }
    }

    public String showAllCategories() {
        return Category.showAllCategories();
    }

    public String showAvailableFilters() {
        String availableFilters = "";
        availableFilters = availableFilters.concat("Brand\nPrice\nName\nAvailable\nSeller\n");
        if (currentCategory == null) {
            availableFilters = availableFilters.concat("Category\n");
        } else {
            for (String specialFeature : currentCategory.getSpecialFeatures()) {
                availableFilters = availableFilters.concat(specialFeature + "\n");
            }
        }
        return availableFilters;
    }

    public String createFilter(String filterType, String filterInput) throws Exception {
        Filter filter = null;
        if (filterType.equalsIgnoreCase("Brand")) {
            filter = new BrandFilter(filterInput, currentCategory == null ? Product.allProducts : currentCategory.getProducts());
        } else if (filterType.equalsIgnoreCase("Price")) {
            String[] words = filterInput.split("[\\s-,_]");
            filter = new PriceRangeFilter(Double.parseDouble(words[0]), Double.parseDouble(words[1]), currentCategory == null ? Product.allProducts : currentCategory.getProducts());
        } else if (filterType.equalsIgnoreCase("Name")) {
            filter = new ProductNameFilter(filterInput, currentCategory == null ? Product.allProducts : currentCategory.getProducts());
        } else if (filterType.equalsIgnoreCase("Seller")) {
            filter = new SellerFilter(filterInput, currentCategory == null ? Product.allProducts : currentCategory.getProducts());
        } else if (filterType.equalsIgnoreCase("Available")) {
            filter = new StockFilter(currentCategory == null ? Product.allProducts : currentCategory.getProducts());
        } else if (currentCategory == null && filterType.equalsIgnoreCase("Category")) {
            filter = new CategoryFilter(filterInput, Product.allProducts);
            currentCategory = Category.getCategoryWithName(filterInput);
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
        if (filterType.equalsIgnoreCase("category"))
            currentCategory = null;
        for (Filter currentFilter : currentFilters) {
            if (currentFilter.getName().equalsIgnoreCase(filterType)) {
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
            return "Sort by: name A-Z\nSort by: name Z-A\nSort by: view\nSort by: average score\nSort by: price ascending\nSort by: price descending\n";
        else if (listType.equalsIgnoreCase("request"))
            return "Sort by: type\nSort by: Id\n";
        else if (listType.equalsIgnoreCase("user"))
            return "Sort by: first name A-Z\nSort by: last name A-Z\nSort by: first name Z-A\nSort by: last name Z-A\n";
        return "";
    }

    public String makeSort(String sortType, String listType) {
        if ((listType.equalsIgnoreCase("product")) &&
                (sortType.equalsIgnoreCase("name A-Z") ||
                        sortType.equalsIgnoreCase("name Z-A") ||
                        sortType.equalsIgnoreCase("view") ||
                        sortType.equalsIgnoreCase("average score") ||
                        sortType.equalsIgnoreCase("price ascending") ||
                        sortType.equalsIgnoreCase("price descending"))) {
            currentProductSort = sortType;
        } else if ((listType.equalsIgnoreCase("request")) &&
                (sortType.equalsIgnoreCase("type") ||
                        sortType.equalsIgnoreCase("id"))) {
            currentRequestSort = sortType;
        } else if ((listType.equalsIgnoreCase("user")) &&
                (sortType.equalsIgnoreCase("first name A-Z") ||
                        sortType.equalsIgnoreCase("first name Z-A") ||
                        sortType.equalsIgnoreCase("last name A-Z") ||
                        sortType.equalsIgnoreCase("last name Z-A"))) {
            currentUserSort = sortType;
        } else return "wrong sort type!";
        return "sort applied successfully.";
    }

    public String showCurrentProductSort() {
        if (currentProductSort.equals(""))
            return "No sorts selected";
        return currentProductSort;
    }

    public String showCurrentUserSort() {
        if (currentUserSort.equals(""))
            return "No sorts selected";
        return currentUserSort;
    }

    public String showCurrentRequestSort() {
        if (currentRequestSort.equals(""))
            return "No sorts selected";
        return currentRequestSort;
    }

    public String disableSort() {
        currentProductSort = "";
        currentRequestSort = "";
        currentUserSort = "";
        return "Sort disabled";
    }

    public String showFilteredAndSortedProducts() throws Exception {
        String filtered = "";
        ArrayList<Product> sorted = Filter.applyFilter(Product.allProducts);
        sortProducts(sorted);
        if (!currentProductSort.equals("") && !sortProducts(sorted))
            return "wrong sort input.";
        for (Product product : sorted) {
            filtered = filtered.concat(product.showProductDigest());
            filtered = filtered.concat("\n\n");
        }
        if (filtered.equals("") && currentFilters.isEmpty())
            return showAllProducts();
        if (filtered.equals("") && !currentFilters.isEmpty())
            return "No products to show!";
        return filtered;
    }

    public boolean sortProducts(ArrayList<Product> sorted) {
        if (currentProductSort.equalsIgnoreCase("name A-Z"))
            sorted.sort(new ProductsSort.productSortByNameAscending());
        else if (currentProductSort.equalsIgnoreCase("name Z-A"))
            sorted.sort(new ProductsSort.productSortByNameDescending());
        else if (currentProductSort.equalsIgnoreCase("view"))
            sorted.sort(new ProductsSort.productSortByView());
        else if (currentProductSort.equalsIgnoreCase("average Score"))
            sorted.sort(new ProductsSort.productSortByRate());
        else if (currentProductSort.equalsIgnoreCase("price ascending"))
            sorted.sort(new ProductsSort.productSortByPriceAscendingly());
        else if (currentProductSort.equalsIgnoreCase("price descending"))
            sorted.sort(new ProductsSort.productSortByPriceDescendingly());
        else return false;
        return true;
    }

    public String createAccount(String type, String userName) {
        if (type.equalsIgnoreCase("Manager") && !ManagerAccount.getAllManagers().isEmpty())
            return "you can not create more than one manager account directly.";
        if (Account.isThereUserWithUserName(userName))
            return "this userName is already taken.";
        if (Account.isThereReservedUserName(userName))
            return "this userName is already reserved.";
        else if (type.equalsIgnoreCase("seller"))
            return "your creating account request was sent to manager successfully.";
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
                Double.parseDouble(buyerInfo.get(5)),null);
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
                0,null);
        CreateSellerAccountRequest createSellerAccountRequest = new CreateSellerAccountRequest(sellerAccount, "create seller account");
        Request.addRequest(createSellerAccountRequest);
        Account.getReservedUserNames().add(userName);
    }

    public void getManagerInformation(ArrayList<String> managerInfo, String userName) throws Exception {
        validateInputAccountInfo(managerInfo, userName);
        ManagerAccount managerAccount = new ManagerAccount(userName,
                managerInfo.get(1),
                managerInfo.get(2),
                managerInfo.get(3),
                managerInfo.get(4),
                managerInfo.get(0),null);
        ManagerAccount.addManager(managerAccount);
    }

    public static void validateInputAccountInfo(ArrayList<String> accountInfo, String userName) throws Exception {
        StringBuilder accountCreationErrors = new StringBuilder();
        try {
            AccountsException.validateUserName(userName);
        } catch (AccountsException.invalidUserNameException e) {
            accountCreationErrors.append(e.getErrorMessage());
        }
        try {
            AccountsException.validateUsernameUniqueness(userName);
        } catch (AccountsException e) {
            accountCreationErrors.append(e.getErrorMessage());
        }
        try {
            AccountsException.validateNameTypeInfo("first name", accountInfo.get(1));
        } catch (AccountsException e) {
            accountCreationErrors.append(e.getErrorMessage());
        }
        try {
            AccountsException.validateNameTypeInfo("last name", accountInfo.get(2));
        } catch (AccountsException e) {
            accountCreationErrors.append(e.getErrorMessage());
        }
        try {
            AccountsException.validateEmail(accountInfo.get(3));
        } catch (AccountsException e) {
            accountCreationErrors.append(e.getErrorMessage());
        }
        try {
            AccountsException.validatePhoneNumber(accountInfo.get(4));
        } catch (AccountsException e) {
            accountCreationErrors.append(e.getErrorMessage());
        }
        try {
            AccountsException.validatePassWord(accountInfo.get(0));
        } catch (AccountsException e) {
            accountCreationErrors.append(e.getErrorMessage());
        }
        if (accountCreationErrors.length() != 0) {
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
            if (currentUser instanceof BuyerAccount) {
                BuyerController.setBuyerController();
            }
            return "Logged in successfully.";
        }
    }

    public String editPersonalInfo(String field, String newContent, String token) {
        return Server.getServer().getTokenInfo(token).getUser().editPersonalInfo(field,newContent);
    }

    public String showAllProducts() {
        String allProducts = "";
        sortProducts(Product.allProducts);
        if (!currentProductSort.equals("") && !sortProducts(Product.allProducts))
            return "wrong sort input.";
        for (Product allProduct : Product.allProducts) {
            allProducts = allProducts.concat(allProduct.showProductDigest());
            allProducts = allProducts.concat("\n\n");
        }
        if (allProducts.equals(""))
            return "No Product to show!";
        return allProducts;
    }

    public String showAllOffProducts() {
        String offProducts = "";
        ArrayList<Product> sorted = new ArrayList<>();
        for (Off allOff : Off.allOffs) {
            if (allOff.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
                allOff.removeOff();
            } else {
                for (Product product : allOff.getProducts()) {
                    sorted.add(product);
                }
            }
        }
        sortProducts(sorted);
        if (!currentProductSort.equals("") && !sortProducts(sorted))
            return "wrong sort input.";
        for (Off allOff : Off.allOffs) {
            for (Product product : allOff.getProducts()) {
                offProducts = offProducts.concat(product.showProductDigest());
                offProducts = offProducts.concat("\n\n");
            }
        }
        if (offProducts.equals(""))
            return "No products with off to show";
        return offProducts;
    }

    public String makeGeneralFeatures(String productId) {
        try {
            Product product = Product.getProductWithId(productId);
            if (product.getOff() != null && product.getOff().getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
                product.getOff().removeOff();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("name: " + product.getName() + "\n");
            stringBuilder.append("brand:" + product.getBrand() + "\n");
            stringBuilder.append("description: " + product.getDescription() + "\n");
            stringBuilder.append("price: " + product.getProductFinalPriceConsideringOff() + "\n");
            stringBuilder.append("off amount: " + product.makeOffAmount() + "\n");
            if (product.getCategory() != null)
                stringBuilder.append("category: " + product.getCategory().getName() + "\n");
            stringBuilder.append("seller(s): " + product.makeSellersList());
            return stringBuilder.toString();
        }catch (Exception e){
            return null;
        }
    }

    public String getProductCategoryInfo(String productId){
        try {
            Product product = Product.getProductWithId(productId);
            if(product.getCategory() != null){
                return product.getCategory().getName();
            }else{
                return "-";
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String getProductSpecialFeaturesInfo(String productId){
        try {
            Product product = Product.getProductWithId(productId);
            if(product.getCategory() != null){
                return product.showSpecialFeatures();
            }else{
                return "-";
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String viewPersonalInfo(String token) {
        return Server.getServer().getTokenInfo(token).getUser().viewMe();
    }

    public String getProfileImagePath(String token){
        return Server.getServer().getTokenInfo(token).getUser().getProfileImagePath();
    }

    public void logout() {
        currentUser = null;
    }

    public String showProductSellers() {
        String sellers = "";
        for (SellerAccount seller : currentProduct.getSellers()) {
            sellers = sellers.concat("seller user name : " + seller.getUserName() +
                    "\nseller full name: " + seller.getFirstName() +
                    " " + seller.getLastName() +
                    "\nseller company name: " + seller.getCompanyName() + "\n\n");
        }
        return sellers;
    }

    public String showSummaryOfProducts() {
        StringBuilder list = new StringBuilder();
        for (Product product : Product.allProducts) {
            if (!product.equals(currentProduct))
                list.append(product.showSummaryOfProductData()).append("\n");
        }
        return list.toString();
    }

    public void initializeIDs() {
        DiscountCode.setLastUsedCodeID();
        Off.setLastUsedOffID();
        Product.setLastUsedProductID();
        Request.setLastUsedRequestID();
        Log.setLastUsedLogID();
        Auction.setLastUsedAuctionID();
    }


    public static String readDataAndSetStringRecordObjects() {
        String functionSuccessFailMessage = readData();
        setStringRecordObjects();
        setImagePaths();
        return functionSuccessFailMessage;
    }

    private static String readData() {
        return Product.readData() +
                "\n" + Category.readData() +
                "\n" + DiscountCode.readData() +
                "\n" + Off.readData() +
                "\n" + Request.readData() +
                "\n" + BuyerAccount.readData() +
                "\n" + SellerAccount.readData() +
                "\n" + ManagerAccount.readData() +
                "\n" + Auction.readData() +
                "\n";
    }

    private static void setStringRecordObjects() {
        BuyerAccount.setStringRecordObjects();
        SellerAccount.setStringRecordObjects();
        DiscountCode.setStringRecordObjects();
        Off.setStringRecordObjects();
        Log.setStringRecordObjects();
        Category.setStringRecordObjects();
        Product.setStringRecordObjects();
        Auction.setStringRecords();
    }

    public static void setImagePaths(){
        for (Product product : Product.allProducts) {
            if(product.getCategory()==null||product.getCategory().getImagePath().equals("")) {
                product.setImagePath("src/main/java/Main/client/graphicView/resources/images/product.png");
            }else{
                product.setImagePath(product.getCategory().getImagePath());
            }
        }
        for (Account account : Account.getAllAccounts()) {
            if(account.getProfileImagePath().equals("")){
                account.setProfileImagePath("src/main/java/Main/client/graphicView/resources/images/avatars/1.png");
            }
        }
    }

    public static String writeDataAndGetObjectStringRecords() {
        getObjectStringRecords();
        return writeData();
    }

    private static void getObjectStringRecords() {
        BuyerAccount.getObjectStringRecords();
        SellerAccount.getObjectStringRecords();
        DiscountCode.getObjectStringRecords();
        Off.getObjectStringRecords();
        Log.getObjectStringRecords();
        Category.getObjectStringRecords();
        Product.getObjectStringRecords();
    }

    private static String writeData() {
        return Product.writeData() +
                "\n" + Category.writeData() +
                "\n" + DiscountCode.writeData() +
                "\n" + Off.writeData() +
                "\n" + Request.writeData() +
                "\n" + BuyerAccount.writeData() +
                "\n" + SellerAccount.writeData() +
                "\n" + ManagerAccount.writeData() +
                "\n" + Auction.writeData() +
                "\n";
    }
}
