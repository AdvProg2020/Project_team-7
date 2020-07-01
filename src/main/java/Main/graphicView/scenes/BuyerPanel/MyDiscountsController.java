package Main.graphicView.scenes.BuyerPanel;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import Main.graphicView.scenes.MainMenuController;
import Main.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.discountAndOffTypeService.DiscountCode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MyDiscountsController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ViewDiscountCodes.fxml";
    public static final String TITLE = "My Discounts";

    @FXML
    private ListView discountsList;

    public void initialize() {
        discountsList.getItems().clear();
        discountsList.getItems().addAll(((BuyerAccount) GeneralController.currentUser).getDiscountsList());
        discountsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (discountsList.getSelectionModel().getSelectedItem() != null) {
                    String discountInfo = discountsList.getSelectionModel().getSelectedItem().toString();
                    String code = discountInfo.substring(1, discountInfo.indexOf(" "));
                    discountsList.getSelectionModel().clearSelection();
                    DiscountCode discountCode = null;
                    try {
                        discountCode = DiscountCode.getDiscountCodeWithCode(code);
                        showCodeInfo(discountCode);
                    } catch (Exception e) {
                        ManagerPanelController.alertError(e.getMessage());
                    }
                }
            }
        });
    }
    public void logout() throws IOException{
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    private void showCodeInfo(DiscountCode discountCode) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DISCOUNT INFO");
        alert.setHeaderText(discountCode.viewMeAsBuyer(((BuyerAccount) GeneralController.currentUser)));
        alert.setContentText(null);
        alert.showAndWait();
    }

    public void goBack() {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}
