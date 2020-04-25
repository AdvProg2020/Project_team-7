package Main.model;

import Main.model.accounts.BuyerAccount;
import Main.model.accounts.SellerAccount;

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
    private int openFrequency;
    private ProductStatus productStatus;
    private double price;
    private ArrayList<BuyerAccount> buyers;
    private Off off;
    private ArrayList<Rate> rates=new ArrayList<Rate>();

    public Product(String productId, String name, String brand, int availability, Category category, String description, ArrayList<Comment> comments, double price) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.availability = availability;
        this.category = category;
        this.description = description;
        this.averageScore =0;
        this.comments = comments;
        this.openFrequency =0;
        this.productStatus = ProductStatus.PENDING_CREATION_PRODUCT;
        this.price = price;
    }

    public String showProductDigest() {
        return
                "description: "+description +
                "\nprice: " + price +
                "\noff amount: " + makeOffAmount() +
                "\ncategory: " + category.getName() +
                "\nseller(s): " + makeSellersList() +
                "\naverage score: " + calculateAverageScore();
    }

    public String makeOffAmount(){
        if(off==null)
            return "0%";
        else
            return off.getOffAmount()+"%";
    }

    public String makeSellersList(){
        StringBuilder list = new StringBuilder();
        for(int i=0;i<sellers.size()-1;i++)
            list.append(sellers.get(i).getCompanyName()+", ");
        list.append(sellers.get(sellers.size()-1).getCompanyName());
        return list.toString();
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

    private double calculateAverageScore(){
        double sum=0;
        for (Rate rate : rates) {
            sum+=rate.getScore();
        }
        return sum/(double) rates.size();
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

    public double getAverageScore() {
        return calculateAverageScore();
    }
    public double getProductFinalPriceConsideringOff(){
        return 0;
    }

}
