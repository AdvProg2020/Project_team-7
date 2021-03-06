package Main.client.graphicView.scenes.ManagerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.ManagerRequestBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.Optional;

public class ManageRequestsController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageRequests.fxml";
    public static final String TITLE = "Manage Requests";

    @FXML
    private ListView requestsList;

    public void initialize() {
        requestsList.getItems().clear();
        //requestsList.getItems().addAll(Request.summaryInfoOfRequests());
        String requestData = ManagerRequestBuilder.buildInitializeManageRequestsRequest();
        if (!requestData.equals(""))
            requestsList.getItems().addAll(requestData.split("#"));
        requestsList.setOnMouseClicked(mouseEvent -> {
            if (requestsList.getSelectionModel().getSelectedItem() != null) {
                String id = requestsList.getSelectionModel().getSelectedItem().toString();
                id = id.substring(1, id.indexOf(' '));
                requestsList.getSelectionModel().clearSelection();
//                    Request request = null;
//                    try {
//                        request = Request.getRequestWithId(id);
//                    } catch (Exception e) {
//                        //ManagerPanelController.alertError(e.getMessage());
//                    }
                try {
                    showRequestMenu(id);
                } catch (Exception e) {
                    //ManagerPanelController.alertError(e.getMessage());
                }
            }
        });
    }

    private void showRequestMenu(String id) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().clear();
        alert.setTitle("Request Menu");
        //alert.setHeaderText(request.showRequest());
        alert.setHeaderText(ManagerRequestBuilder.buildShowRequestWithIdRequest(id));
        alert.setContentText("What do you want to do with this request?");
        ButtonType accept = new ButtonType("Accept");
        ButtonType decline = new ButtonType("Decline");
        ButtonType cancel = new ButtonType("Cancel");
        alert.getButtonTypes().addAll(cancel, accept, decline);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(accept)) {
            //request.accept();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            //alert1.setTitle("Request Accepted");
            alert1.setTitle(ManagerRequestBuilder.buildAcceptRequestWithIdRequest(id));
            alert1.setHeaderText(null);
            //alert1.setContentText(request.getType() + " with id " + request.getRequestId() + " accepted successfully.");
            alert1.showAndWait();
            initialize();
        } else if (option.get().equals(decline)) {
            //request.decline();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            //alert1.setTitle("Request Declined");
            alert1.setTitle(ManagerRequestBuilder.buildDeclineRequestWithIdRequest(id));
            alert1.setHeaderText(null);
            //alert1.setContentText(request.getType() + " request with id " + request.getRequestId() + " declined successfully.");
            alert1.showAndWait();
            initialize();
        }
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }
}
