package Main.client.graphicView.scenes;

import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.server.model.Product;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.DiscountAndOffStat;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/productPage.fxml";
    public static final String TITLE = "Seller Panel";
    private Product currentProduct;
    private ImageView productImage = new ImageView(new Image(new File("src/main/java/Main/client/graphicView/resources/images/product.png").toURI().toString()));

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
        currentProduct = GeneralController.currentProduct;
        productImage.setImage(new Image(new File(currentProduct.getImagePath()).toURI().toString()));

        setScoreImage();
        scoreLabel.setText(currentProduct.getAverageScore().toString());
        productImage.preserveRatioProperty().set(true);
        scrollImage.setContent(productImage);
        generalFeatures.setText(makeGeneralFeatures());
        if (currentProduct.getCategory() != null) {
            specialFeaturesPane.setVisible(true);
            specialFeatures.setText(currentProduct.showSpecialFeatures());
        }
        showComments();


    }

    public void setScoreImage() {
        double score = currentProduct.getAverageScore();
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
        Account account = GeneralController.currentUser;
        if (account instanceof ManagerAccount) {
            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
        } else if (account instanceof SellerAccount) {
            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
        } else if (account instanceof BuyerAccount) {
            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
        } else {
            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
        }
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
    }

    public String makeGeneralFeatures() {
        if (currentProduct.getOff() != null && currentProduct.getOff().getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
            currentProduct.getOff().removeOff();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name: " + currentProduct.getName() + "\n");
        stringBuilder.append("brand:" + currentProduct.getBrand() + "\n");
        stringBuilder.append("description: " + currentProduct.getDescription() + "\n");
        stringBuilder.append("price: " + currentProduct.getProductFinalPriceConsideringOff() + "\n");
        stringBuilder.append("off amount: " + currentProduct.makeOffAmount() + "\n");
        if (currentProduct.getCategory() != null)
            stringBuilder.append("category: " + currentProduct.getCategory().getName() + "\n");
        stringBuilder.append("seller(s): " + currentProduct.makeSellersList());
        return stringBuilder.toString();
    }

    public void logout() throws IOException{
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    public void goToSelectSellerPage() throws IOException {
        if (GeneralController.currentUser == null || GeneralController.currentUser instanceof SellerAccount ||
                GeneralController.currentUser instanceof ManagerAccount) {
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
            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        GraphicMain.generalController.selectSellerWithUsername(textField.getText());
                        showInformationAlert(GraphicMain.generalController.addProductToCart());
                        selectSellerBox.close();
                    } catch (Exception e) {
                        showErrorAlert(e.getMessage());
                    }
                }
            });
        }
    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }

    public void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void rateProduct() {
        if ((!(GeneralController.currentUser instanceof BuyerAccount)) || GeneralController.currentUser == null) {
            showErrorAlert("you must login first");
        } else {
            BuyerAccount buyer = (BuyerAccount) GeneralController.currentUser;
            if (!(buyer.hasBuyerBoughtProduct(currentProduct))) {
                showErrorAlert("you should have bought this product");
            } else {
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
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            GraphicMain.buyerController.rateProductWithId(currentProduct.getProductId(), textField.getText());
                            stage.close();
                        } catch (Exception e) {
                            showErrorAlert(e.getMessage());
                        }
                    }
                });
            }
        }

    }

    public void showComments() {
        TextArea textArea = new TextArea();
        textArea.setText(currentProduct.showComments());
        textArea.setFont(Font.font(30));
        commentsVBox.getChildren().add(textArea);
    }

    public void addComment() throws IOException {
        GraphicMain.graphicMain.goToPage(AddCommentPage.FXML_PATH, AddCommentPage.TITLE);
    }

    public void compareProduct() {
        String id = productIdToBeCompared.getText();
        try {
            String compare = GraphicMain.generalController.compareProductWithProductWithId(id);
            showInformationAlert(compare);
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

}
