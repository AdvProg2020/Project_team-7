package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.exceptions.CreateProductException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
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
    @FXML
    private CheckBox isFile;
    @FXML
    private Pane pane;
    @FXML
    private Button selectFile;
    @FXML
    private Pane notFilePane;
    @FXML
    private Label fileName;
    @FXML
    private Label uploading;

    public static CreateProductException.GetCategoryFromUser exception;
    private File file;

    public void isProductFile(ActionEvent actionEvent) {
        if (isFile.isSelected()) {
            pane.setVisible(true);
            notFilePane.setVisible(false);
        } else {
            pane.setVisible(false);
            notFilePane.setVisible(true);
        }
    }

    public void select(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(GraphicMain.stage);
        fileName.setText(file.getName());
        name.setText(file.getName()+"___FILEPRODUCT");
        name.setDisable(true);
        description.setText(file.getPath());
        description.setDisable(true);
    }

    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    public void initialize() {
        pane.setVisible(false);
    }

    public void submit() throws IOException {
        ArrayList<String> productInfo = new ArrayList<>();
        productInfo.add(name.getText());
        productInfo.add(brand.getText());
        if (isFile.isSelected())
            productInfo.add("100");
        else
            productInfo.add(availability.getText());
        productInfo.add(description.getText());
        productInfo.add(price.getText());
        if (isFile.isSelected()) {
            productInfo.add("no");
            productInfo.add("-");
        } else {
            if (hasCategory.isSelected()) {
                productInfo.add("yes");
                productInfo.add(categoryName.getText());
            } else {
                productInfo.add("no");
                productInfo.add("-");
            }
        }
        String response;
        if (isFile.isSelected())
            response = SellerRequestBuilder.buildAddFileRequest(productInfo, file);
        else
            response = SellerRequestBuilder.buildAddProductRequest(productInfo);
        System.out.println("\n\n"+response+"\n\n");
        if (response.startsWith("success")) {
            showInformationAlert("product created successfully\n"+response);
            GraphicMain.graphicMain.back();
        } else if (response.startsWith("error")) {
            String[] splitResponse = response.split("#");
            showErrorAlert(splitResponse[1]);
            GraphicMain.graphicMain.back();
        } else {
            if (hasCategory.isSelected()) {
                exception = GeneralController.yagsonMapper.fromJson(response, CreateProductException.GetCategoryFromUser.class);
                GraphicMain.graphicMain.goToPage(AddProductSpecialFeatures.FXML_PATH, AddProductSpecialFeatures.TITLE);
            } else {
                GraphicMain.graphicMain.back();
            }
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
        alert.showAndWait();
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
