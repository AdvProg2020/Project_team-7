package Main.client.graphicView.scenes.ManagerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.MyOrdersController;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.ProductPage;
import Main.server.controller.GeneralController;
import Main.server.model.Product;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.logs.BuyLog;
import Main.server.model.logs.DeliveryStatus;
import Main.server.model.logs.Log;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class ManageOrdersController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageAllOrders.fxml";
    public static final String TITLE = "Manager Orders";

    @FXML
    private ListView ordersList;

    public void initialize() {
        ordersList.getItems().clear();
        for (BuyerAccount buyer : BuyerAccount.getAllBuyers()) {
            ordersList.getItems().addAll(buyer.buyLogsList());
        }
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
        if (buyLog.getDeliveryStatus().equals(DeliveryStatus.PENDING_DELIVERY)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Log Info");
            alert.setHeaderText(buyLog.viewLog());
            alert.setContentText("what do you want to do with this log?");
            alert.getButtonTypes().clear();
            ButtonType cancel = new ButtonType("Cancel");
            alert.getButtonTypes().add(cancel);
            ButtonType mark_as_delivered = new ButtonType("Mark As Delivered");
            alert.getButtonTypes().add(mark_as_delivered);
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(mark_as_delivered)) {
                buyLog.markDelivered();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("BuyLog Delivery Status");
                alert1.setHeaderText(null);
                alert1.setContentText("BuyLog marked as delivered successfully.");
                alert1.showAndWait();
                initialize();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Log Info");
            alert.setHeaderText(buyLog.viewLog());
            alert.setContentText("what do you want to do with this log?");
            alert.getButtonTypes().clear();
            ButtonType cancel = new ButtonType("Cancel");
            alert.getButtonTypes().add(cancel);
            Optional<ButtonType> option = alert.showAndWait();
        }
    }



    public void logout() throws IOException {
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

    public void goBack() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}
