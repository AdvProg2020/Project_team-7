package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.Auction;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class AuctionPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/auction.fxml";
    public static final String TITLE = "Auction Page";
    public javafx.scene.control.TextField increaseAmount;
    public javafx.scene.control.Label informationBox;
    public javafx.scene.control.TextField message;
    public VBox messagePane;
    public AnchorPane pane;
    public Label highestPrice;

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

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.exitProgram();
    }

    public void back() {
        GraphicMain.graphicMain.back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        informationBox.setAlignment(Pos.CENTER);
        informationBox.setLineSpacing(15);
        Auction auction = Auction.getAuctionById(GraphicMain.currentAuctionId);
        try {
            if (auction == null || auction.isAuctionOver()) {
                informationBox.setText("the auction is over");
                disablePage();
            } else if (!auction.hasAuctionBeenStarted()) {
                disablePage();
                informationBox.setText("the auction will start at \n" + auction.getAuctionUsage().getStartDate());
            } else {
                informationBox.setText("auction is underway !\ntake part !");
                highestPrice.setText("" + auction.getAuctionUsage().getHighestPrice());
                //TODO messagePane
            }
        } catch (Exception e) {
            GraphicMain.showInformationAlert("the auction is over");
            try {
                sleep(4);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            initialize(location, resources);
        }
    }

    private void disablePage() {
        for (Node child : pane.getChildren()) {
            child.setDisable(true);
        }
        informationBox.setDisable(false);
    }

    public void increasePrice(MouseEvent mouseEvent) {
        String response = BuyerRequestBuilder.buildIncreaseAuctionPriceRequest(increaseAmount.getText());
        if (response.equals("success")) {
            highestPrice.setText("" + Double.parseDouble(highestPrice.getText()) + Double.parseDouble(increaseAmount.getText()));
        }else{
            showIncrementResponseMessage(response);
        }
    }

    private void showIncrementResponseMessage(String response) {
        if(response.equals("invalidNo")){
            GraphicMain.showInformationAlert("increase amount must be\n of positive double type");
        }else if(response.equals("loginNeeded")){
            GraphicMain.showInformationAlert("you must login first !\nyou'r authentication might be expired !");
        }else if(response.equals("insufficientBalance")){
            GraphicMain.showInformationAlert("your balance isn't enough");
        }
    }


    public void send(MouseEvent mouseEvent) {
    }
}
