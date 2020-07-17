package Main.client.graphicView.scenes;

import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.server.model.accounts.SellerAccount;
import Main.server.serverRequestProcessor.SellerRequestProcessor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditSellerPersonalInformationPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/editSellerPersonalInformationPage.fxml";
    public static final String TITLE = "Edit Personal Information";

    @FXML
    private Label allowedFields;
    @FXML
    private TextField fieldToEdit;
    @FXML
    private TextField newContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allowedFields.setText("Allowed fields to edit:\nfirst name\nlast name\nemail\nphone number\npassword" +
                "\ncompany name\ncompany extra information");
    }

    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    public void editPersonalInfo(){
        String response = SellerRequestBuilder.buildEditPersonalInformationRequest(fieldToEdit.getText(), newContent.getText());
        showInformationAlert(response);
//        String message = GraphicMain.generalController.editPersonalInfo(fieldToEdit.getText(),newContent.getText());
//        showInformationAlert(message);
    }

    public void showInformationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
