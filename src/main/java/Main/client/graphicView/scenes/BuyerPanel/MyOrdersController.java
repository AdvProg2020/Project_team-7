package Main.client.graphicView.scenes.BuyerPanel;

import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.logs.BuyLog;
import Main.server.model.logs.Log;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MyOrdersController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ManageOrders.fxml";
    public static final String TITLE = "My Orders";

    @FXML
    private ListView ordersList;

    public void initialize() {
        ordersList.getItems().clear();
        ordersList.getItems().addAll(((BuyerAccount) GeneralController.currentUser).buyLogsList());
        ordersList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (ordersList.getSelectionModel().getSelectedItem() != null) {
                    String logInfo = ordersList.getSelectionModel().getSelectedItem().toString();
                    String logId = logInfo.substring(1, logInfo.indexOf(" "));
                    ordersList.getSelectionModel().clearSelection();
                    BuyLog buyLog = null;
                    try {
                        buyLog = (BuyLog) Log.getLogWithID(logId);
                    } catch (Exception e) {
                        ManagerPanelController.alertError(e.getMessage());
                    }
                    showLogInfo(buyLog);
                }
            }
        });
    }

    private void showLogInfo(BuyLog buyLog) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ORDER INFO");
        alert.setHeaderText(buyLog.viewLog());
        alert.setContentText(null);
        alert.showAndWait();
    }

    public void logout() throws IOException{
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    public void goBack() {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}
