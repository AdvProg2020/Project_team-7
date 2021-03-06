package Main.client.graphicView.scenes.BuyerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.io.IOException;

public class MyOrdersController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ManageOrders.fxml";
    public static final String TITLE = "My Orders";

    @FXML
    private ListView ordersList;

    public void initialize() {
        ordersList.getItems().clear();
        //ordersList.getItems().addAll(((BuyerAccount) GeneralController.currentUser).buyLogsList());
        String orderData = BuyerRequestBuilder.buildInitializeMyOrdersRequest();
        if (!orderData.equals(""))
            ordersList.getItems().addAll(orderData.split("#"));
        ordersList.setOnMouseClicked(mouseEvent -> {
            if (ordersList.getSelectionModel().getSelectedItem() != null) {
                String logInfo = ordersList.getSelectionModel().getSelectedItem().toString();
                String logId = logInfo.substring(1, logInfo.indexOf(" "));
                ordersList.getSelectionModel().clearSelection();
//                    BuyLog buyLog = null;
//                    try {
//                        buyLog = (BuyLog) Log.getLogWithID(logId);
//                    } catch (Exception e) {
//                        ManagerPanelController.alertError(e.getMessage());
//                    }
                showLogInfo(logId);
            }
        });
    }

    private void showLogInfo(String logId) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ORDER INFO");
        //alert.setHeaderText(buyLog.viewLog());
        alert.setHeaderText(BuyerRequestBuilder.buildGetBuyLogInfo(logId));
        alert.setContentText(null);
        alert.showAndWait();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}
