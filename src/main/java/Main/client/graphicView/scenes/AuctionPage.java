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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    public Label highestOffer;

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
                highestOffer.setText("" + auction.getAuctionUsage().getHighestOffer());
                //TODO messagePane
            }
            initializeMessagePane();
        } catch (Exception e) {
            GraphicMain.showInformationAlert("the auction is over");
            disablePage();
        }
    }

    private void initializeMessagePane() throws Exception {
        Auction auction = Auction.getAuctionById(GraphicMain.currentAuctionId);
        if (auction == null) {
            throw new Exception();
        }
        int i = 0;
        ArrayList<String> allMessages = auction.getAuctionUsage().getAllMessages();
        allMessages.add("hi");
        allMessages.add("how's it goin");
        allMessages.add("hi");
        allMessages.add("how's it goin");
        allMessages.add("hi");
        allMessages.add("how's it goin");
        allMessages.add("hi");
        allMessages.add("how's it goin");
        for (String message : allMessages) {
            i++;
            Label label = new Label(message);
            label.getStyleClass().add("messageType" + i%9);
            messagePane.getChildren().add(label);
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
            try {
                highestOffer.setText(Auction.getAuctionById(GraphicMain.currentAuctionId).getAuctionUsage().getHighestOffer()+"");
            } catch (Exception e) {
                informationBox.setText(e.getMessage());
            }
        } else {
            showIncrementResponseMessage(response);
        }
    }

    private void showIncrementResponseMessage(String response) {
        if (response.equals("invalidNo")) {
            informationBox.setText("increase amount must be\n of positive double type");
        } else if (response.equals("loginNeeded")) {
            informationBox.setText("you must login first !\nyou'r authentication might be expired !");
        } else if (response.equals("insufficientBalance")) {
            informationBox.setText("your balance isn't enough");
        } else if (response.equals("auctionOver")) {
            informationBox.setText("sorry auction is over");
            disablePage();
        } else if (response.equals("alreadyOnAuction")) {
            informationBox.setText("you have an offer in another auction");
        }
    }


    public void send(MouseEvent mouseEvent) {
    }
}
