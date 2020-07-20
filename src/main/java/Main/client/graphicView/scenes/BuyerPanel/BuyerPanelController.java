package Main.client.graphicView.scenes.BuyerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.WalletPage;
import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class BuyerPanelController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/BuyerPanel.fxml";
    public static final String TITLE = "Buyer user panel";

//    public void initialize() {
//
//        balance.setText(BuyerRequestBuilder.buildInitializeBuyerPanelRequest());
//        //balance.setText(Double.toString(((BuyerAccount) GeneralController.currentUser).getBalance()));
//    }

    public void goToPersonalInformation() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(BuyerPersonalInfoController.FXML_PATH, BuyerPersonalInfoController.TITLE);
    }

    public void goToMyOrders() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(MyOrdersController.FXML_PATH, MyOrdersController.TITLE);
    }

    public void goToMyDiscounts() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(MyDiscountsController.FXML_PATH, MyDiscountsController.TITLE);
    }

    public void goToMyCart() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(MyCartController.FXML_PATH, MyCartController.TITLE);
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

    public void goToHelpCenter() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(HelpCenterController.FXML_PATH, HelpCenterController.TITLE);
    }

    public void goToWalletPage() throws IOException {
        GraphicMain.graphicMain.goToPage(WalletPage.FXML_PATH, WalletPage.TITLE);
    }
}
