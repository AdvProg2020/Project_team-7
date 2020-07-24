package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.DataRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.Product;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.util.Arrays.asList;

public class ProductPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/productPage.fxml";
    public static final String TITLE = "Seller Panel";
    //    private Product currentProduct;
    private ImageView productImage = new ImageView(new Image(new File("src/main/java/Main/client/graphicView/resources/images/product.png").toURI().toString()));
    private ArrayList<Product> relatedProducts = new ArrayList<>();

    @FXML
    private ImageView scoreImage;
    @FXML
    private Label scoreLabel;
    @FXML
    private ScrollPane scrollImage;
    @FXML
    private TextArea generalFeatures;
    @FXML
    private TextArea specialFeatures;
    @FXML
    private ScrollPane specialFeaturesPane;
    @FXML
    private VBox commentsVBox;
    @FXML
    private TextField productIdToBeCompared;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
        zoomProperty.addListener(arg0 -> {
            productImage.setFitWidth(zoomProperty.get());
            productImage.setFitHeight(zoomProperty.get());
        });
        scrollImage.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() > 0) {
                zoomProperty.set(zoomProperty.get() * 1.1);
            } else if (event.getDeltaY() < 0) {
                if (zoomProperty.get() != productImage.getX())
                    zoomProperty.set(zoomProperty.get() / 1.1);
            }
        });
//        currentProduct = GeneralController.currentProduct;
//        productImage.setImage(new Image(new File(currentProduct.getImagePath()).toURI().toString()));
//        setScoreImage();
//        scoreLabel.setText(currentProduct.getAverageScore().toString());
//        productImage.preserveRatioProperty().set(true);
//        scrollImage.setContent(productImage);
//        generalFeatures.setText(makeGeneralFeatures());
//        if (currentProduct.getCategory() != null) {
//            specialFeaturesPane.setVisible(true);
//            specialFeatures.setText(currentProduct.showSpecialFeatures());
//        }
//        showComments();
        String[] response = GeneralRequestBuilder.getAllDataForProductPage().split("#");
        productImage.setImage(new Image(new File(response[0]).toURI().toString()));
        setScoreImage(Double.parseDouble(response[1]));
        scoreLabel.setText(response[1]);
        productImage.preserveRatioProperty().set(true);
        scrollImage.setContent(productImage);
        generalFeatures.setText(response[2]);
        if (!response[3].equals("-")) {
            specialFeaturesPane.setVisible(true);
            specialFeatures.setText(response[4]);
        }
        showComments(response[5]);
    }

    public void setScoreImage(double score) {
        if (score == 0)
            scoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score0.png").toURI().toString()));
        else if (score > 0 && score <= 1)
            scoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score1.png").toURI().toString()));
        else if (score > 1 && score <= 2)
            scoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score2.png").toURI().toString()));
        else if (score > 2 && score <= 3)
            scoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score3.png").toURI().toString()));
        else if (score > 3 && score <= 4)
            scoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score4.png").toURI().toString()));
        else if (score > 4 && score <= 5)
            scoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score5.png").toURI().toString()));
    }

    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    public void goToUserPanelPage() throws IOException {
//        Account account = GeneralController.currentUser;
//        if (account instanceof ManagerAccount) {
//            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
//        } else if (account instanceof SellerAccount) {
//            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
//        } else if (account instanceof BuyerAccount) {
//            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
//        } else {
//            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
//        }
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        String response = DataRequestBuilder.buildUserTypeRequest();
        if (response.equals("manager")) {
            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
        } else if (response.equals("seller")) {
            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
        } else if (response.equals("buyer")) {
            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
        } else if (response.equals("supporter")) {
            GraphicMain.graphicMain.goToPage(SupporterPanelController.FXML_PATH, SupporterPanelController.TITLE);
        } else {
            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
        }
    }

//    public String makeGeneralFeatures() {
//        if (currentProduct.getOff() != null && currentProduct.getOff().getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
//            currentProduct.getOff().removeOff();
//        }
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("name: " + currentProduct.getName() + "\n");
//        stringBuilder.append("brand:" + currentProduct.getBrand() + "\n");
//        stringBuilder.append("description: " + currentProduct.getDescription() + "\n");
//        stringBuilder.append("price: " + currentProduct.getProductFinalPriceConsideringOff() + "\n");
//        stringBuilder.append("off amount: " + currentProduct.makeOffAmount() + "\n");
//        if (currentProduct.getCategory() != null)
//            stringBuilder.append("category: " + currentProduct.getCategory().getName() + "\n");
//        stringBuilder.append("seller(s): " + currentProduct.makeSellersList());
//        return stringBuilder.toString();
//    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

    public void goToSelectSellerPage() throws IOException {
//        if (GeneralController.currentUser == null || GeneralController.currentUser instanceof SellerAccount ||
//                GeneralController.currentUser instanceof ManagerAccount) {
//            showErrorAlert("You have not logged in yet!");
//            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
        String userType = DataRequestBuilder.buildUserTypeRequest();
        if (userType.equals("loginNeeded") || !userType.equals("buyer")) {
            showErrorAlert("You have not logged in yet!");
            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
        } else {
            Stage selectSellerBox = new Stage();
            selectSellerBox.setTitle("Select seller");
            TextField textField = new TextField();
            textField.setPromptText("seller username");
            Button submit = new Button("submit");
            VBox vBox = new VBox();
            vBox.getChildren().addAll(textField, submit);
            Scene scene = new Scene(vBox, 750, 400);
            selectSellerBox.setScene(scene);
            selectSellerBox.show();
            submit.setOnAction(event -> {
//                    try {
//                        GraphicMain.generalController.selectSellerWithUsername(textField.getText());
//                        showInformationAlert(GraphicMain.generalController.addProductToCart());
//                        selectSellerBox.close();
//                    } catch (Exception e) {
//                        showErrorAlert(e.getMessage());
//                    }
                String response = GeneralRequestBuilder.selectSeller(textField.getText());
                if (response.startsWith("success")) {
                    showInformationAlert(response.split("#")[1]);
                    selectSellerBox.close();
                } else if (response.startsWith("error")) {
                    showErrorAlert(response.split("#")[1]);
                }
            });
        }
    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void rateProduct() {
//        if ((!(GeneralController.currentUser instanceof BuyerAccount)) || GeneralController.currentUser == null) {
//            showErrorAlert("you must login first");
//        }
        String userType = DataRequestBuilder.buildUserTypeRequest();
        if (userType.equals("loginNeeded") || !userType.equals("buyer")) {
            showErrorAlert("you must login first");
        } else {
            String response = GeneralRequestBuilder.buildRateProductPermissionRequest();
            if (response.startsWith("you")) {
                showErrorAlert("you should have bought this product");
            } else if (response.equals("success")) {
                Stage stage = new Stage();
                stage.setTitle("Rate product");
                TextField textField = new TextField();
                textField.setPromptText("insert your score from 1 to 5");
                Button submit = new Button("submit");
                VBox vBox = new VBox();
                vBox.getChildren().addAll(textField, submit);
                Scene scene = new Scene(vBox, 750, 400);
                stage.setScene(scene);
                stage.show();
                submit.setOnAction(event -> {
//                        try {
//                            GraphicMain.buyerController.rateProductWithId(currentProduct.getProductId(), textField.getText());
//                            stage.close();
//                        } catch (Exception e) {
//                            showErrorAlert(e.getMessage());
//                        }
                    String response1 = GeneralRequestBuilder.buildRateProductRequest(textField.getText());
                    if (response1.equals("success")) {
                        stage.close();
                    } else if (response1.startsWith("error")) {
                        showErrorAlert(response1.split("#")[1]);
                    }
                });
            }
//            BuyerAccount buyer = (BuyerAccount) GeneralController.currentUser;
//            if (!(buyer.hasBuyerBoughtProduct(currentProduct))) {
//                showErrorAlert("you should have bought this product");
        }

    }

    public void showComments(String comments) {
        TextArea textArea = new TextArea();
        textArea.setText(comments);
//        textArea.setText(currentProduct.showComments());
        textArea.setFont(Font.font(30));
        commentsVBox.getChildren().add(textArea);
    }

    public void addComment() throws IOException {
        GraphicMain.graphicMain.goToPage(AddCommentPage.FXML_PATH, AddCommentPage.TITLE);
    }

    public void compareProduct() {
        String id = productIdToBeCompared.getText();
        String response = GeneralRequestBuilder.buildCompareProductRequest(id);
        if (response.startsWith("success")) {
            showInformationAlert(response.split("#")[1]);
        } else if (response.startsWith("error")) {
            showErrorAlert(response.split("#")[1]);
        }
//        try {
//            String compare = GraphicMain.generalController.compareProductWithProductWithId(id);
//            showInformationAlert(compare);
//        } catch (Exception e) {
//            showErrorAlert(e.getMessage());
//        }
    }

    public void viewRelatedProducts(MouseEvent mouseEvent) {
        String response = DataRequestBuilder.buildCategoryProductsRequest(GraphicMain.currentProductId);
        if (response.equals("nullCategory")) {
            GraphicMain.showInformationAlert("no related products found !");
        } else if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else {
            GeneralController.jsonReader = new JsonReader(new StringReader(response));
            Product[] allPro = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Product[].class);
            relatedProducts = (allPro == null) ? new ArrayList<>() : new ArrayList<>(asList(allPro));
            showRelatedProducts(relatedProducts);
        }
    }


    private void showRelatedProducts(ArrayList<Product> allProducts) {
        Stage addAuction = new Stage();
        addAuction.setTitle("Related Products");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(30, 0, 30, 30));
        vBox.setSpacing(50);
        vBox.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(vBox);

        for (Product product : allProducts) {
            VBox productBox = product.createProductBoxForUI();
            vBox.getChildren().add(productBox);
        }

        Scene scene = new Scene(scrollPane, 750, 400);
        addAuction.setScene(scene);
        addAuction.show();
    }
}
