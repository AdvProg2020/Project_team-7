package Main.model;

import Main.model.accounts.BuyerAccount;
import Main.model.accounts.SellerAccount;

import java.sql.Time;
import java.util.ArrayList;

public class Product {
    private String productId;
    private String name;
    private String brand;
    private ArrayList<SellerAccount> sellers = new ArrayList<SellerAccount>();
    private int availability;
    private Category category;
    private String description;
    private double averageScore;
    private ArrayList<Comment> comments;
    private static ArrayList<Product> allProducts = new ArrayList<Product>();
    private Time timeCreated;
    private int openFrequency;
    private ProductStatus productStatus;
    private double price;
    private ArrayList<BuyerAccount> buyers;
    private Off off;

    public Product(String productId, String name, String brand, int availability, Category category, String description, double averageScore, ArrayList<Comment> comments, Time timeCreated, int openFrequency, ProductStatus productStatus, double price, ArrayList<BuyerAccount> buyers) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.availability = availability;
        this.category = category;
        this.description = description;
        this.averageScore = averageScore;
        this.comments = comments;
        this.timeCreated = timeCreated;
        this.openFrequency = openFrequency;
        this.productStatus = productStatus;
        this.price = price;
        this.buyers = buyers;
    }

    public String showProductDigest() {
        return null;
    }

    public String showProductAttributes() {
        return null;
    }

    public static Product getProductWithId(String productId) {
        return null;
    }

    private static Product getProductWithName(String name) {
        return null;
    }

    public String compareProductWithProductWithName(String name) {
        return null;
    }

    public void addComment(Comment comment) {

    }

    public void removeComment(Comment comment) {

    }

    public String showComments() {
        return null;
    }

    public void decreaseAvailabilityBy(int number) {

    }

    public static void removeProduct(Product product) {

    }

    public static String showAllProducts() {
        return null;
    }

    public void addRate(Rate rate) {

    }

    public double calculateAverageRate() {
        return 0;
    }

    public void setOff(Off off) {

    }

    public static void addProduct(Product product) {

    }

    public String viewBuyers() {
        return null;
    }

    public void setProductStatus(ProductStatus productStatus) {

    }

    public void increaseVisitFrequencyByOne() {

    }

    public void removeOff(Off off) {

    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public Off getOff() {
        return off;
    }

    public double getProductFinalPriceConsideringOff(){
        return 0;
    }

}
