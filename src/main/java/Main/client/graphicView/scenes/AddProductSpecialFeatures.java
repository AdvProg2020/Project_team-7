package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProductSpecialFeatures implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/addProductSpecialFeatures.fxml";
    public static final String TITLE = "special features";
    private ArrayList<TextField> specialFeaturesTextFields = new ArrayList<>();

    @FXML
    private VBox box;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addSpecialFeatures(AddProductPage.exception.getCategory().getName());
    }

    public void addSpecialFeatures(String categoryName) {
        ArrayList<String> features = SellerRequestBuilder.buildGetCategorySpecialFeatures(categoryName);
        for (String feature : features) {
            TextField textField = new TextField();
            textField.setPromptText(feature);
            box.getChildren().add(textField);
            specialFeaturesTextFields.add(textField);
        }
    }

    public void submitFeatures() {
        ArrayList<String> specialFeatures = new ArrayList<>();
        for (TextField textField : specialFeaturesTextFields) {
            specialFeatures.add(textField.getText());
        }
        String response = SellerRequestBuilder.buildAddSpecialFeaturesRequest(specialFeatures, AddProductPage.exception.getProduct().getProductId());
        if (response.equals("success")) {
            showInformationAlert("product created successfully");
        } else {
            showErrorAlert("failure");
        }
//        GraphicMain.sellerController.setSpecialFeatures(AddProductPage.exception.getProduct(),specialFeatures);
//        showInformationAlert("product created successfully");
    }

    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    public void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

}
