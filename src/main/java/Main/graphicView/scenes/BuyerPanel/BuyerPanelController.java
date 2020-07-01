package Main.graphicView.scenes.BuyerPanel;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import Main.graphicView.scenes.MainMenuController;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class BuyerPanelController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/BuyerPanel.fxml";
    public static final String TITLE = "Buyer user panel";

    @FXML
    private Label balance;

    public void initialize() {
        balance.setText(Double.toString(((BuyerAccount) GeneralController.currentUser).getBalance()));
    }

    public void goToPersonalInformation() throws IOException {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(BuyerPersonalInfoController.FXML_PATH, BuyerPersonalInfoController.TITLE);
    }

    public void goToMyOrders() throws IOException {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(MyOrdersController.FXML_PATH, MyOrdersController.TITLE);
    }

    public void goToMyDiscounts() throws IOException {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(MyDiscountsController.FXML_PATH, MyDiscountsController.TITLE);
    }

    public void goToMyCart() throws IOException {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(MyCartController.FXML_PATH, MyCartController.TITLE);
    }

    public void goBack() {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void logout() throws IOException{
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }
}
