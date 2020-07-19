package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import Main.server.model.requests.EditOffRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

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

    public void submitEdits() {
        ArrayList<String> listOfTitles = new ArrayList<>();
        ArrayList<String> listOfContents = new ArrayList<>();
        if (!startDate.getText().isEmpty()) {
            listOfTitles.add("start date");
            listOfContents.add(startDate.getText());
        }
        if (!endDate.getText().isEmpty()) {
            listOfTitles.add("end date");
            listOfContents.add(endDate.getText());
        }
        if (!offAmount.getText().isEmpty()) {
            listOfTitles.add("off amount");
            listOfContents.add(offAmount.getText());
        }
        if (!productIdToBeAdded.getText().isEmpty()) {
            listOfTitles.add("add product");
            listOfContents.add(productIdToBeAdded.getText());
        }
        if (!productIdToBeRemoved.getText().isEmpty()) {
            listOfTitles.add("remove product");
            listOfContents.add(productIdToBeRemoved.getText());
        }
        String response = SellerRequestBuilder.buildEditOffRequest(SellerOffsPage.selectedOff, listOfTitles, listOfContents);
        if (response.equals("success")) {
            showInformationAlert("off edited successfully");
        } else {
            String[] splitResponse = response.split("#");
            showErrorAlert(splitResponse[1]);
        }
//        try {
//            EditOffRequest editOffRequest = GraphicMain.sellerController.getOffToEdit(SellerOffsPage.selectedOff);
//            if(!startDate.getText().isEmpty()){
//                editOffRequest.addEditedFieldTitle("start date");
//                editOffRequest.setStartDate(startDate.getText());
//            }
//            if(!endDate.getText().isEmpty()){
//                editOffRequest.addEditedFieldTitle("end date");
//                editOffRequest.setEndDate(endDate.getText());
//            }
//            if(!offAmount.getText().isEmpty()){
//                editOffRequest.addEditedFieldTitle("off amount");
//                editOffRequest.setOffAmount(offAmount.getText());
//            }
//            if(!productIdToBeAdded.getText().isEmpty()){
//                editOffRequest.addEditedFieldTitle("add product");
//                editOffRequest.addProductIDToBeAdded(productIdToBeAdded.getText());
//            }
//            if(!productIdToBeRemoved.getText().isEmpty()){
//                editOffRequest.addEditedFieldTitle("remove product");
//                editOffRequest.addProductIDToBeRemoved(productIdToBeRemoved.getText());
//            }
//            GraphicMain.sellerController.submitOffEdits(editOffRequest);
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
