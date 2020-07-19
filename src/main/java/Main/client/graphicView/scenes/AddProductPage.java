package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.exceptions.CreateProductException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class AddProductPage {

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/addProductPage.fxml";
    public static final String TITLE = "Add Product";

    @FXML
    private CheckBox hasCategory;
    @FXML
    private TextField name;
    @FXML
    private TextField brand;
    @FXML
    private TextField availability;
    @FXML
    private TextField description;
    @FXML
    private TextField price;
    @FXML
    private TextField categoryName;

    public static CreateProductException.GetCategoryFromUser exception;


    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    public void submit() throws IOException {
        ArrayList<String> productInfo = new ArrayList<>();
        productInfo.add(name.getText());
        productInfo.add(brand.getText());
        productInfo.add(availability.getText());
        productInfo.add(description.getText());
        productInfo.add(price.getText());
        if (hasCategory.isSelected()) {
            productInfo.add("yes");
            productInfo.add(categoryName.getText());
        } else {
            productInfo.add("no");
            productInfo.add("-");
        }
        String response = SellerRequestBuilder.buildAddProductRequest(productInfo);
        if (response.equals("success")) {
            showInformationAlert("product created successfully");
        } else if (response.startsWith("error")) {
            String[] splitResponse = response.split("#");
            showErrorAlert(splitResponse[1]);
        } else {
            exception = GeneralController.yagsonMapper.fromJson(response, CreateProductException.GetCategoryFromUser.class);
            GraphicMain.graphicMain.goToPage(AddProductSpecialFeatures.FXML_PATH, AddProductSpecialFeatures.TITLE);
        }
//        try{
//            GraphicMain.sellerController.addProduct(productInfo);
//            showInformationAlert("product created successfully");
//        } catch (CreateProductException.InvalidProductInputInfo e) {
//            showErrorAlert(e.getMessage());
//        } catch (CreateProductException.GetCategoryFromUser e) {
//            exception = e;
//            GraphicMain.graphicMain.goToPage(AddProductSpecialFeatures.FXML_PATH,AddProductSpecialFeatures.TITLE);
//        }
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

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }
}
