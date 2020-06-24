package Main.graphicView.scenes.BuyerPanel;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.discountAndOffTypeService.DiscountCode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class MyDiscountsController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ViewDiscountCodes.fxml";
    public static final String TITLE = "My Discounts";

    @FXML
    private ListView discountsList;

    public void initialize() {
        discountsList.getItems().clear();
        discountsList.getItems().addAll(DiscountCode.getDiscountsList());
        discountsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (discountsList.getSelectionModel().getSelectedItem() !=null) {
                    String discountInfo = discountsList.getSelectionModel().getSelectedItem().toString();
                    String code = discountInfo.substring(1,discountInfo.indexOf("\\s"));
                    discountsList.getSelectionModel().clearSelection();
                    DiscountCode discountCode = null;
                    try {
                        discountCode = DiscountCode.getDiscountCodeWithCode(code);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showCodeInfo(discountCode);
                }
            }
        });
    }

    private void showCodeInfo(DiscountCode discountCode){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DISCOUNT INFO");
        alert.setHeaderText(discountCode.viewMeAsBuyer(((BuyerAccount) GeneralController.currentUser)));
        alert.setContentText(null);
        alert.showAndWait();
    }

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
