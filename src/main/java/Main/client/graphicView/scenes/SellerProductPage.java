package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerProductPage implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/sellerProductPage.fxml";
    public static final String TITLE = "Seller Product page";
    public static String productId;

    @FXML
    private ImageView productImage;
    @FXML
    private ImageView averageScoreImage;
    @FXML
    private Label averageScore;
    @FXML
    private Label digestLabel;
    @FXML
    private Label buyersList;


    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productId = SellerProductsPage.selectedProduct;
//        try {
//            Product product = Product.getProductWithId(productId);
//            productImage.setImage(new Image(new File(product.getImagePath()).toURI().toString()));
//            digestLabel.setText(makeDigestLabel(product));
//            setScoreImage(product);
//            averageScore.setText((product.getAverageScore()).toString());
//            buyersList.setText(product.viewBuyers());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String[] response = SellerRequestBuilder.getAllProductDataForSellerProductPage(productId).split("#");
        productImage.setImage(new Image(new File(response[1]).toURI().toString()));
        digestLabel.setText(response[0]);
        setScoreImage(Double.parseDouble(response[2]));
        averageScore.setText(response[2]);
        buyersList.setText(response[3]);
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

//    public String makeDigestLabel(Product product){
//        return "name: " + product.getName() +
//                "\n\tid: " + product.getProductId() +
//                "\n\tdescription: " + product.getDescription() +
//                "\n\tprice: " + product.getPrice() +
//                "\n\toff amount: " + product.makeOffAmount() +
//                "\n\tcategory: " + product.getCategory().getName();
//    }

    public void setScoreImage(double score) {
        if (score == 0)
            averageScoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score0.png").toURI().toString()));
        else if (score > 0 && score <= 1)
            averageScoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score1.png").toURI().toString()));
        else if (score > 1 && score <= 2)
            averageScoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score2.png").toURI().toString()));
        else if (score > 2 && score <= 3)
            averageScoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score3.png").toURI().toString()));
        else if (score > 3 && score <= 4)
            averageScoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score4.png").toURI().toString()));
        else if (score > 4 && score <= 5)
            averageScoreImage.setImage(new Image(new File("src/main/java/Main/client/graphicView/resources/images/score5.png").toURI().toString()));

    }

    public void goToEditProductPage() throws IOException {
        GraphicMain.graphicMain.goToPage(EditProductPage.FXML_PATH, EditProductPage.TITLE);
    }

    public void removeProduct() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setContentText("Are you sure you want to remove this product?");
        Optional<ButtonType> selectedButton = alert.showAndWait();
        if (ButtonType.OK.equals(selectedButton.get())) {
            String response = SellerRequestBuilder.buildRemoveProductRequest(productId);
            if (response.equals("success")) {
                goBack();
            } else if (response.startsWith("error")) {
                showErrorAlert(response.split("#")[1]);
            }
//            try {
//                GraphicMain.sellerController.removeProductWithID(productId);
//                goBack();
//            } catch (Exception e) {
//                showErrorAlert(e.getMessage());
//            }
        }
    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
