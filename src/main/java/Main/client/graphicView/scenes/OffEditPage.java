package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.server.model.requests.EditOffRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class OffEditPage {


    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/offEditPage.fxml";
    public static final String TITLE = "Off Edit Page";

    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private TextField offAmount;
    @FXML
    private TextField productIdToBeAdded;
    @FXML
    private TextField productIdToBeRemoved;

    public void submitEdits(){
        try {
            EditOffRequest editOffRequest = GraphicMain.sellerController.getOffToEdit(SellerOffsPage.selectedOff);
            if(!startDate.getText().isEmpty()){
                editOffRequest.addEditedFieldTitle("start date");
                editOffRequest.setStartDate(startDate.getText());
            }
            if(!endDate.getText().isEmpty()){
                editOffRequest.addEditedFieldTitle("end date");
                editOffRequest.setEndDate(endDate.getText());
            }
            if(!offAmount.getText().isEmpty()){
                editOffRequest.addEditedFieldTitle("off amount");
                editOffRequest.setOffAmount(offAmount.getText());
            }
            if(!productIdToBeAdded.getText().isEmpty()){
                editOffRequest.addEditedFieldTitle("add product");
                editOffRequest.addProductIDToBeAdded(productIdToBeAdded.getText());
            }
            if(!productIdToBeRemoved.getText().isEmpty()){
                editOffRequest.addEditedFieldTitle("remove product");
                editOffRequest.addProductIDToBeRemoved(productIdToBeRemoved.getText());
            }
            GraphicMain.sellerController.submitOffEdits(editOffRequest);
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    public void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }
}
