package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.Auction;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuctionPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/auction.fxml";
    public static final String TITLE = "Auction Page";

    public void goToUserPanelMenu(MouseEvent mouseEvent) throws IOException {
        Account account = GeneralController.currentUser;
        if (account instanceof ManagerAccount) {
            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
        } else if (account instanceof SellerAccount) {
            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
        } else if (account instanceof BuyerAccount) {
            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
        } else {
            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
        }
        //GraphicMain.audioClip.stop();
        //LoginSignUpPage.mediaPlayer.play();
    }

    public void logout() throws IOException{
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        //goBack();
        GraphicMain.graphicMain.exitProgram();
    }

    public void back(){
        GraphicMain.graphicMain.back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Auction auction = Auction.getAuctionById(GraphicMain.currentAuctionId);
//        if(auction==null||auction.isAuctionOver()){
//            Label label = new Label("the auction is over");
//
//        }
    }
}
