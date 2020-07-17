package Main.client.graphicView.scenes.BuyerPanel;

import Main.client.ClientMain;
import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.client.requestBuilder.Client;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.server.model.accounts.BuyerAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class BuyerPanelController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/BuyerPanel.fxml";
    public static final String TITLE = "Buyer user panel";

    @FXML
    private Label balance;

    public void initialize() {

        balance.setText(BuyerRequestBuilder.buildInitializeBuyerPanelRequest());
        //balance.setText(Double.toString(((BuyerAccount) GeneralController.currentUser).getBalance()));
    }

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

    public void logout() throws IOException{
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    public void goToHelpCenter(ActionEvent actionEvent) throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(HelpCenterController.FXML_PATH, HelpCenterController.TITLE);
    }
}
