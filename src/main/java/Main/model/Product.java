package Main.model;

import Main.model.accounts.BuyerAccount;
import Main.model.accounts.SellerAccount;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private static StringBuilder lastUsedProductID = new StringBuilder("@");
    private String productId;
    private String name;
    private String brand;
    private ArrayList<SellerAccount> sellers = new ArrayList<SellerAccount>();
    private int availability;
    private Category category;
    private String description;
    private double averageScore;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    public static ArrayList<Product> allProducts = new ArrayList<Product>();
    private int openFrequency;
    private ProductStatus productStatus;
    private double price;
    private ArrayList<BuyerAccount> buyers = new ArrayList<BuyerAccount>();
    private Off off;
    private ArrayList<Rate> rates = new ArrayList<Rate>();
    private HashMap<String, String> specialFeatures = new HashMap<String, String>();

    public Product(String name, String brand, int availability, Category category, String description,
                   ArrayList<Comment> comments, double price, ArrayList<String> specialFeatures) {
        this.productId = IDGenerator.getNewID(lastUsedProductID);
        this.name = name;
        this.brand = brand;
        this.availability = availability;
        setCategory(category);
        this.description = description;
        this.averageScore = 0;
        this.comments = comments;
        this.openFrequency = 0;
        this.productStatus = ProductStatus.PENDING_CREATION_PRODUCT;
        this.price = price;
        if(specialFeatures!=null) {
            for (int i = 0; i < specialFeatures.size(); i++)
                this.specialFeatures.put(category.getSpecialFeatures().get(i), specialFeatures.get(i));
        }
    }

    private void setCategory(Category category) {
        this.category = category;
        if(category!=null) {
            category.addProduct(this);
        }
    }

    public String showProductDigest() {
        return
                "description: " + description +
                        "\nprice: " + price +
                        "\noff amount: " + makeOffAmount() +
                        "\ncategory: " + category.getName() +
                        "\nseller(s): " + makeSellersList() +
                        "\naverage score: " + calculateAverageScore();
    }

    public String makeOffAmount() {
        if (off == null)
            return "0%";
        else
            return off.getOffAmount() + "%";
    }

    public String makeSellersList() {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < sellers.size() - 1; i++)
            list.append(sellers.get(i).getCompanyName() + ", ");
        list.append(sellers.get(sellers.size() - 1).getCompanyName());
        return list.toString();
    }

    public String showProductAttributes() {
        return
                "general attributes: \n" + showGeneralAttributes() +
                        "category: " + category.getName() +
                        showSpecialFeatures();
    }

    public String showGeneralAttributes() {
        return
                "name: " + name +
                        "\nbrand: " + brand +
                        "\nprice: " + price +
                        "\nseller(s): " + makeSellersList() +
                        "\navailability: " + showAvailability() +
                        "\naverage score: " + getAverageScore();

    }

    public String showSpecialFeatures() {
        StringBuilder features = new StringBuilder();
        for (String key : specialFeatures.keySet()) {
            features.append("\n" + key + ": " + specialFeatures.get(key));
        }
        return features.toString();
    }

    public String showAvailability() {
        if (availability == 0)
            return "unavailable";
        else
            return "available";
    }

    public static Product getProductWithId(String productId) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId))
                return product;
        }
        return null;
        //TODO invalid id exception
    }

    private static Product getProductWithName(String name) {
        for (Product product : allProducts) {
            if (product.getName().equals(name))
                return product;
        }
        return null;
        //TODO invalid id exception
    }

    public String compareProductWithProductWithId(String id) {
        Product productToBeCompared = getProductWithId(id);
        if (productToBeCompared.getCategory().equals(category) && category != null) {
            return compareProductsInSameCategory(productToBeCompared);
        } else
            return "Products are not in the Same Category. Comparison is invalid!";
    }

    public String compareProductsInSameCategory(Product product) {
        StringBuilder string = new StringBuilder();
        string.append(compareProductsWithGeneralFeatures(product));
        for (String key : specialFeatures.keySet()) {
            string.append("\n" + key + ": \n1." + specialFeatures.get(key) + "\n2." + product.specialFeatures.get(key));
        }
        return string.toString();
    }

    public String compareProductsWithGeneralFeatures(Product product) {
        return
                "name: \n" +
                        "1." + name + "\n" +
                        "2." + product.getName() + "\n" +
                        "brand: \n" +
                        "1." + brand + "\n" +
                        "2." + product.getBrand() + "\n" +
                        "price: \n" +
                        "1." + price + "\n" +
                        "2." + product.getPrice() + "\n" +
                        "availability: \n" +
                        "1." + showAvailability() + "\n" +
                        "2." + product.showAvailability() + "\n" +
                        "average score: \n" +
                        "1." + getAverageScore() + "\n" +
                        "2." + product.getAverageScore();
    }


    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public String showComments() {
        StringBuilder allComments = new StringBuilder();
        if (comments.isEmpty())
            return "No comment has been recorded for this product.";
        else {
            allComments.append("comments:");
            for (Comment comment : comments) {
                allComments.append("\nTitle: " + comment.getTitle() + "\nContent: " + comment.getContent());
            }
            return allComments.toString();
        }
    }

    public void decreaseAvailabilityBy(int number) {
        availability -= number;
    }

    public void removeProduct() {
        allProducts.remove(this);
        for (SellerAccount seller : this.sellers) {
            seller.removeProduct(this);
        }
        this.category.removeProduct(this);
        this.off.removeProduct(this);
    }

    public static String showAllProducts() {
        if (allProducts.isEmpty())
            return "No product is available!";
        else {
            StringBuilder string = new StringBuilder();
            string.append("All products:");
            for (Product product : allProducts) {
                string.append("\n" + product.getName() + "    Id: " + product.getProductId());
            }
            return string.toString();
        }
    }

    public void addRate(Rate rate) {
        rates.add(rate);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public String viewBuyers() {
        StringBuilder allBuyers = new StringBuilder();
        if (buyers.isEmpty())
            return "Nobody has bought this product yet!";
        else {
            allBuyers.append("Buyers: ");
            for (BuyerAccount buyer : buyers) {
                allBuyers.append("\n" + buyer.getUserName());
            }
            return allBuyers.toString();
        }
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public void increaseVisitFrequencyByOne() {
        this.openFrequency++;
    }

    public void removeOff(Off off) {
        setOff(null);
    }

    private double calculateAverageScore() {
        double sum = 0;
        for (Rate rate : rates) {
            sum += rate.getScore();
        }
        return sum / (double) rates.size();
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

    public Double getAverageScore() {
        return calculateAverageScore();
    }

    public Category getCategory() {
        return category;
    }

    public double getProductFinalPriceConsideringOff() {
        if (off == null)
            return price;
        else
            return price * ((double) 1 - (off.getOffAmount() / (double) 100));
    }

    public void setOff(Off off) {
        this.off = off;
    }

    public Integer getOpenFrequency() {
        return openFrequency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailability() {
        return availability;
    }

    public HashMap<String, String> getSpecialFeatures() {
        return specialFeatures;
    }

    public void removeSeller(SellerAccount sellerAccount) {
        sellers.remove(sellerAccount);
    }

    public void emptySpecialFeatures() {
        specialFeatures.clear();
    }

    public void removeCategory() {
        category = null;
    }
}
