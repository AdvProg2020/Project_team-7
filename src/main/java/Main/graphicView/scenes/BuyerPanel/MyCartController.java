package Main.graphicView.scenes.BuyerPanel;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class MyCartController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ViewCart.fxml";
    public static final String TITLE = "My Cart";

    @FXML
    private Label totalPrice;

    public void initialize(){
        totalPrice.setText(Double.toString(((BuyerAccount) GeneralController.currentUser).getCart().getCartTotalPriceConsideringOffs()));
    }

    public void goToPurchase() throws IOException {
        GraphicMain.graphicMain.goToPage(PurchaseController.FXML_PATH, PurchaseController.TITLE);
    }

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
