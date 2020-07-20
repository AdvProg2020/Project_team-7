package Main.server.model;

import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.ProductPage;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.server.model.discountAndOffTypeService.Off;
import Main.server.model.discountAndOffTypeService.OffStatus;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Arrays.asList;

public class Product implements Serializable{
    private static StringBuilder lastUsedProductID;
    private String productId;
    private String name;
    private String brand;
    private ArrayList<SellerAccount> sellers = new ArrayList<>();
    private int availability;
    private Category category;
    private String description;
    private double averageScore;
    private ArrayList<Comment> comments = new ArrayList<>();
    public static ArrayList<Product> allProducts = new ArrayList<>();
    private int openFrequency;
    private ProductStatus productStatus;
    private double price;
    public ArrayList<BuyerAccount> buyers = new ArrayList<>();
    private Off off;
    private ArrayList<Rate> rates = new ArrayList<>();
    private HashMap<String, String> specialFeatures = new HashMap<>();
    private String imagePath;
    private static ArrayList<String> allBrands = new ArrayList<>();
    private ArrayList<String> sellersStringRecord = new ArrayList<>();
    private String categoryStringRecord;
    private ArrayList<String> buyersStringRecord = new ArrayList<>();
    private int tempNumberOfProduct;
    private double tempTotalPrice;
    private CartProduct tempCartProduct;
    public boolean isOnAuction;

    public Product(String name, String brand, int availability, String description, double price, SellerAccount sellerAccount) {
        this.productId = IDGenerator.getNewID(lastUsedProductID);
        this.name = name;
        this.brand = brand;
        this.availability = availability;
        this.description = description;
        this.averageScore = 0;
        this.openFrequency = 0;
        sellers.add(sellerAccount);
        this.productStatus = ProductStatus.PENDING_CREATION_PRODUCT;
        this.price = price;
        this.averageScore = 0;
        this.off = null;
        this.imagePath = "src/main/java/Main/client/graphicView/resources/images/product.png";
    }

    public CartProduct getTempCartProduct() {
        return tempCartProduct;
    }

    public void setTempCartProduct(CartProduct tempCartProduct) {
        this.tempCartProduct = tempCartProduct;
    }

    public void setTempNumberOfProduct(int tempNumberOfProduct) {
        this.tempNumberOfProduct = tempNumberOfProduct;
    }

    public static ObservableList<Product> getCartProductsAsPro(BuyerAccount buyerAccount) {
        ObservableList<Product> cartProducts = FXCollections.observableArrayList();
        cartProducts.addAll((buyerAccount.getCart().getCartsProductList()));
        return cartProducts;
    }

    public int getTempNumberOfProduct() { //it is not unused :)
        return tempNumberOfProduct;
    }

    public double getTempTotalPrice() { //it is not unused :)
        return tempTotalPrice;
    }

    public void setTempTotalPrice(double tempTotalPrice) {
        this.tempTotalPrice = tempTotalPrice;
    }

    public static ArrayList<String> summaryProductInfo() {
        ArrayList<String> summaryInfos = new ArrayList<>();
        for (Product product : allProducts) {
            String summary = "@" + product.productId + " " + product.name + "\nbrand:" + product.brand + "\t\t\t\t" + product.price + "Dollars";
            summaryInfos.add(summary);
        }
        return summaryInfos;
    }

    public void setSpecialFeatures(ArrayList<String> specialFeatures) {
        for (int i = 0; i < specialFeatures.size(); i++) {
            this.specialFeatures.put(category.getSpecialFeatures().get(i), specialFeatures.get(i));
        }
    }

    public void setCategory(Category category) {
        this.category = category;
        this.imagePath = category.getImagePath();
    }

    public String showProductDigest() {
        if (off != null && off.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
            off.removeOff();
        }
        return
                "name: " + name +
                        "\n\tid: " + productId +
                        "\n\tdescription: " + description +
                        "\n\tprice: " + price +
                        "\n\toff amount: " + makeOffAmount() +
                        "\n\tcategory: " + category.getName() +
                        "\n\tseller(s): " + makeSellersList() +
                        "\n\taverage score: " + calculateAverageScore();
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
            list.append(sellers.get(i).getCompanyName()).append(", ");
        list.append(sellers.get(sellers.size() - 1).getCompanyName());
        return list.toString();
    }

    public String showProductAttributes() {
        return
                "general attributes: \n" + showGeneralAttributes() +
                        "\tcategory: " + category.getName() +
                        showSpecialFeatures();
    }

    public String showGeneralAttributes() {
        return
                "name: " + name +
                        "\n\tbrand: " + brand +
                        "\n\tprice: " + price +
                        "\n\tseller(s): " + makeSellersList() +
                        "\n\tavailability: " + showAvailability() +
                        "\n\taverage score: " + getAverageScore();

    }

    public String showSpecialFeatures() {
        StringBuilder features = new StringBuilder();
        for (String key : specialFeatures.keySet()) {
            features.append("\n\t").append(key).append(": ").append(specialFeatures.get(key));
        }
        return features.toString();
    }

    public String showAvailability() {
        if (availability == 0)
            return "unavailable";
        else
            return availability + " of this product is available.";
    }

    public static Product getProductWithId(String productId) throws Exception {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productId))
                return product;
        }
        throw new Exception("There is no product with ID : " + productId + "\n");
    }

    private static Product getProductWithName(String name) throws Exception {
        for (Product product : allProducts) {
            if (product.getName().equals(name))
                return product;
        }
        throw new Exception("There is no product with this name !\n");
    }

    public String compareProductWithProductWithId(String id) throws Exception {
        Product productToBeCompared = getProductWithId(id);
        Category productToBeComparedCategory = productToBeCompared.getCategory();
        if (category != null && productToBeComparedCategory != null && productToBeComparedCategory == category) {
            return compareProductsInSameCategory(productToBeCompared);
        } else
            return "Products are not in the Same Category. Comparison is invalid!";
    }

    public String compareProductsInSameCategory(Product product) {
        StringBuilder string = new StringBuilder();
        string.append(compareProductsWithGeneralFeatures(product));
        for (String key : specialFeatures.keySet()) {
            string.append("\n").append(key).append(": \n1.").append(specialFeatures.get(key)).append("\n2.").append(product.specialFeatures.get(key));
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
                allComments.append("\nTitle: ").append(comment.getTitle()).append("\nContent: ").append(comment.getContent());
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
        if (this.category != null)
            this.category.removeProduct(this);
        if (this.off != null)
            this.off.removeProduct(this);
    }

    public static String showAllProducts() {
        if (allProducts.isEmpty())
            return "No product is available!";
        else {
            StringBuilder string = new StringBuilder();
            string.append("All products:");
            for (Product product : allProducts) {
                string.append("\n").append(product.getName()).append("    Id: ").append(product.getProductId());
            }
            return string.toString();
        }
    }

    public void addRate(Rate rate) {
        rates.add(rate);
    }

    public void addProduct(Product product) {
        allProducts.add(product);
        if (!allBrands.contains(product.getBrand())) {
            allBrands.add(product.getBrand());
        }
        if (category != null) {
            category.addProduct(this);
        }
        for (SellerAccount seller : sellers) {
            seller.addProduct(this);
        }
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
    //TODO  : where to apply this 0_0 !!! in view?

    public void removeOff(Off off) {
        setOff(null);
    }

    private double calculateAverageScore() {
        double sum = 0;
        for (Rate rate : rates) {
            sum += rate.getScore();
        }
        if (rates.isEmpty())
            return 0;
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

    public Double getProductFinalPriceConsideringOff() {
        if (off != null && off.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
            off.removeOff();
        }
        if (off != null && off.getOffStatus().equals(OffStatus.APPROVED_OFF)) {
            return price * ((double) 1 - (off.getOffAmount() / (double) 100));
        } else return price;
    }

    public String showSummaryOfProductData() {
        return "Product name: " + name + "\tProduct id " + productId;
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
        specialFeatures = null;
    }

    public void addSpecialFeature(String specialFeature, String specialFeatureValue) {
        if (!specialFeatures.containsKey(specialFeature)) {
            specialFeatures.put(specialFeature, specialFeatureValue);
        }
    }

    public void removeSpecialFeature(String specialFeature) {
        specialFeatures.remove(specialFeature);
    }

    public String getDescription() {
        return description;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public ArrayList<SellerAccount> getSellers() {
        return sellers;
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/products.json")));
            Product[] allPro = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Product[].class);
            allProducts = (allPro == null) ? new ArrayList<>() : new ArrayList<>(asList(allPro));
            setLastUsedProductID();
            setAllBrands();
            return "Read Products Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from product.json";
        }
    }

    private static void setAllBrands() {
        for (Product product : allProducts) {
            if (!allBrands.contains(product.getBrand())) {
                allBrands.add(product.getBrand());
            }
        }
    }

    public static void setLastUsedProductID() {
        if (allProducts.size() == 0) {
            lastUsedProductID = new StringBuilder("@");
        } else {
            lastUsedProductID = new StringBuilder(allProducts.get(allProducts.size() - 1).getProductId());
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/products.json");
            Product[] allPro = new Product[allProducts.size()];
            allPro = allProducts.toArray(allPro);
            GeneralController.yagsonMapper.toJson(allPro, Product[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Products Data Successfully.";
        } catch (IOException e) {
            return "Problem saving products data.";
        }
    }


    public static void setStringRecordObjects() {
        try {
            setStringRecordBuyers();
            setStringRecordCategory();
            setStringRecordSellers();
        } catch (Exception e) {
        }
    }

    private static void setStringRecordSellers() throws Exception {
        for (Product product : allProducts) {
            product.sellers.clear();
            for (String sellerUserName : product.sellersStringRecord) {
                product.sellers.add(SellerAccount.getSellerWithUserName(sellerUserName));
            }
        }
    }

    private static void setStringRecordBuyers() throws Exception {
        for (Product product : allProducts) {
            product.buyers.clear();
            for (String buyerUserName : product.buyersStringRecord) {
                product.buyers.add(BuyerAccount.getBuyerWithUserName(buyerUserName));
            }
        }
    }

    private static void setStringRecordCategory() throws Exception {
        for (Product product : allProducts) {
            product.category = (product.categoryStringRecord == null ? null : Category.getCategoryWithName(product.categoryStringRecord));
        }
    }

    public static void getObjectStringRecords() {
        getBuyersStringRecord();
        getSellersStringRecord();
        getCategoriesStringRecord();
    }

    private static void getSellersStringRecord() {
        for (Product product : allProducts) {
            product.buyersStringRecord.clear();
            for (BuyerAccount buyer : product.buyers) {
                product.buyersStringRecord.add(buyer.getUserName());
            }
        }
    }

    private static void getBuyersStringRecord() {
        for (Product product : allProducts) {
            product.sellersStringRecord.clear();
            for (SellerAccount seller : product.sellers) {
                product.sellersStringRecord.add(seller.getUserName());
            }
        }
    }

    private static void getCategoriesStringRecord() {
        for (Product product : allProducts) {
            product.categoryStringRecord = (product.category == null ? null : product.category.getName());
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public VBox createProductBoxForUI() {
        VBox productBox = new VBox();
        productBox.setMinHeight(160);
        productBox.setMinWidth(200);
        productBox.setAlignment(Pos.TOP_CENTER);
        productBox.setId(productId);
        productBox.getStyleClass().add("productBox");
        productBox.setOnMouseClicked(event -> {
            GeneralController.currentProduct = this;
            try {
                GraphicMain.graphicMain.goToPage(ProductPage.FXML_PATH, ProductPage.TITLE);
            } catch (IOException e) {
            }
        });
        productBox.setOnMouseEntered(event -> {
            productBox.setCursor(Cursor.HAND);
            productBox.getStyleClass().remove("productBox");
            productBox.getStyleClass().add("productBoxHover");
        });
        productBox.setOnMouseExited(event -> {
            productBox.getStyleClass().remove("productBoxHover");
            productBox.getStyleClass().add("productBox");
        });

        setProductImage(productBox);
        setProductInfoLabels(productBox);
        setProductRateStars(productBox);

        return productBox;
    }

    public VBox createProductBoxForCreateAuction() {
        VBox productBox = new VBox();
        productBox.setMinHeight(160);
        productBox.setMinWidth(200);
        productBox.setAlignment(Pos.TOP_CENTER);
        productBox.setId(productId);
        productBox.getStyleClass().add("productBox");
        productBox.setOnMouseEntered(event -> {
            productBox.setCursor(Cursor.HAND);
            productBox.getStyleClass().remove("productBox");
            productBox.getStyleClass().add("productBoxHover");
        });
        productBox.setOnMouseExited(event -> {
            productBox.getStyleClass().remove("productBoxHover");
            productBox.getStyleClass().add("productBox");
        });

        setProductImage(productBox);
        setProductInfoLabels(productBox);
        setProductRateStars(productBox);

        return productBox;
    }

    private void setProductInfoLabels(VBox productBox) {
        productBox.getChildren().add(new Label(name));
        productBox.getChildren().add(new Label(brand));
        Label productPrice = new Label("price : " + price);
        productBox.getChildren().add(productPrice);
        if (getProductFinalPriceConsideringOff() != price) {
            productPrice.getStyleClass().add("strikethrough");
            productBox.getChildren().add(new Label("price considering off : " + getProductFinalPriceConsideringOff()));
        }
        if (availability == 0) {
            Label availability = new Label("unavailable !");
            availability.getStyleClass().add("strikethrough");
            productBox.getChildren().add(availability);
        } else {
            productBox.getChildren().add(new Label(availability + " available !"));
        }
    }

    private void setProductRateStars(VBox productBox) {
        int rate = getAverageScore().intValue();
        ImageView rateStars = new ImageView(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score" + rate + ".png").toURI().toString()));
        rateStars.setFitHeight(30);
        rateStars.setFitWidth(150);
        productBox.getChildren().add(rateStars);
    }

    private void setProductImage(VBox productBox) {
        ImageView productImage = new ImageView(new Image(new File(imagePath).toURI().toString()));
        productImage.setFitWidth(80);
        productImage.setFitHeight(80);
        productBox.getChildren().add(productImage);
    }

    public static ArrayList<String> getAllBrands() {
        return allBrands;
    }

    public HBox createProductBoxForAdPane() {
        HBox productBox = new HBox();
        productBox.setMinHeight(160);
        productBox.setMinWidth(300);
        productBox.setAlignment(Pos.TOP_CENTER);
        productBox.setId(productId);
        productBox.setOnMouseClicked(event -> {
            GeneralController.currentProduct = this;
            try {
                GraphicMain.graphicMain.goToPage(ProductPage.FXML_PATH, ProductPage.TITLE);
            } catch (IOException e) {
            }
        });

        setProductAdImage(productBox);
        setProductAdLabels(productBox);

        return productBox;
    }

    private void setProductAdLabels(HBox productBox) {
        VBox labelPane = new VBox();
        labelPane.setPadding(new Insets(15, 0, 0, 15));
        labelPane.getChildren().add(new Label(name));
        labelPane.getChildren().add(new Label(brand));
        Label productPrice = new Label("price : " + price);
        labelPane.getStyleClass().add("adLabel");
        labelPane.getChildren().add(productPrice);
        if (getProductFinalPriceConsideringOff() != price) {
            productPrice.setStyle("-fx-strikethrough : true;");
            labelPane.getChildren().add(new Label("price considering off : " + getProductFinalPriceConsideringOff()));
        }
        productBox.getChildren().add(labelPane);
    }


    private void setProductAdImage(HBox productBox) {
        ImageView productImage = new ImageView(new Image(new File(imagePath).toURI().toString()));
        productImage.setFitWidth(150);
        productImage.setFitHeight(150);
        productBox.getChildren().add(productImage);
    }

    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }
}
