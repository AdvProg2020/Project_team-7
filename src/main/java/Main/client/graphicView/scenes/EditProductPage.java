package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditProductPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/editProductPage.fxml";
    public static final String TITLE = "Edit Product page";
    private Product product;

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
    private TextField offId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        setProduct();
        String response = SellerRequestBuilder.getProductForProductEditPage(SellerProductPage.productId);
        product = GeneralController.yagsonMapper.fromJson(response, Product.class);
        name.setPromptText(product.getName());
        brand.setPromptText(product.getBrand());
        availability.setPromptText(Integer.toString(product.getAvailability()));
        description.setPromptText(product.getDescription());
        price.setPromptText(Double.toString(product.getPrice()));
        if (product.getOff() != null)
            offId.setPromptText(product.getOff().getOffId());
        else
            offId.setPromptText("-");

    }

//    public void setProduct(){
//        try {
//            product = Product.getProductWithId(SellerProductPage.productId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void submitEdits() {
        ArrayList<String> listOfTitles = new ArrayList<>();
        ArrayList<String> listOfContents = new ArrayList<>();
        if (!name.getText().isEmpty()) {
            listOfTitles.add("name");
            listOfContents.add(name.getText());
        }
        if (!brand.getText().isEmpty()) {
            listOfTitles.add("brand");
            listOfContents.add(brand.getText());
        }
        if (!availability.getText().isEmpty()) {
            listOfTitles.add("availability");
            listOfContents.add(availability.getText());
        }
        if (!description.getText().isEmpty()) {
            listOfTitles.add("description");
            listOfContents.add(description.getText());
        }
        if (!price.getText().isEmpty()) {
            listOfTitles.add("price");
            listOfContents.add(price.getText());
        }
        if (!offId.getText().isEmpty()) {
            listOfTitles.add("off");
            listOfContents.add(offId.getText());
        }
        String response = SellerRequestBuilder.buildEditProductRequest(product.getProductId(), listOfTitles, listOfContents);
        if (response.equals("success")) {
            showInformationAlert("product edited successfully");
        } else {
            String[] splitResponse = response.split("#");
            showErrorAlert(splitResponse[1]);
        }
//        try {
//            EditProductRequest editProductRequest = GraphicMain.sellerController.getProductToEdit(product.getProductId());
//            if(!name.getText().isEmpty()){
//                editProductRequest.addEditedFieldTitle("name");
//                editProductRequest.setName(name.getText());
//            }
//            if(!brand.getText().isEmpty()){
//                editProductRequest.addEditedFieldTitle("brand");
//                editProductRequest.setBrand(brand.getText());
//            }
//            if(!availability.getText().isEmpty()){
//                editProductRequest.addEditedFieldTitle("availability");
//                editProductRequest.setAvailability(availability.getText());
//            }
//            if(!description.getText().isEmpty()){
//                editProductRequest.addEditedFieldTitle("description");
//                editProductRequest.setDescription(description.getText());
//            }
//            if(!price.getText().isEmpty()){
//                editProductRequest.addEditedFieldTitle("price");
//                editProductRequest.setPrice(price.getText());
//            }
//            if(!offId.getText().isEmpty()){
//                editProductRequest.addEditedFieldTitle("off");
//                editProductRequest.setOffID(offId.getText());
//            }
//            GraphicMain.sellerController.submitProductEdits(editProductRequest);
//            showInformationAlert("product edited successfully");
//        } catch (Exception e) {
//            showErrorAlert(e.getMessage());
//        }

    }

    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
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
}
