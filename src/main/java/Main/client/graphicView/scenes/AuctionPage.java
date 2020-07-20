package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.client.requestBuilder.DataRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.Auction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AuctionPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/auction.fxml";
    public static final String TITLE = "Auction Page";
    public javafx.scene.control.TextField increaseAmount;
    public javafx.scene.control.Label informationBox;
    public javafx.scene.control.TextField message;
    public VBox messagePane;
    public AnchorPane pane;
    public Label highestOffer;
    private int messageNo;

//    public void goToUserPanelMenu(MouseEvent mouseEvent) throws IOException {
//        Account account = GeneralController.currentUser;
//        if (account instanceof ManagerAccount) {
//            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
//        } else if (account instanceof SellerAccount) {
//            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
//        } else if (account instanceof BuyerAccount) {
//            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
//        } else {
//            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
//        }
//        //GraphicMain.audioClip.stop();
//        //LoginSignUpPage.mediaPlayer.play();
//    }

    public void goToUserPanelMenu(MouseEvent mouseEvent) throws IOException {
        String response = DataRequestBuilder.buildUserTypeRequest();
        if (response.equals("manager")) {
            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
        } else if (response.equals("seller")) {
            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
        } else if (response.equals("buyer")) {
            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
        } else if (response.equals("loginNeeded")) {
            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
        } else {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        }
        //GraphicMain.audioClip.stop();
        //LoginSignUpPage.mediaPlayer.play();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        String response = GeneralRequestBuilder.buildLogoutRequest();
        if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else {
            GraphicMain.token = "0000";
            //goBack();
            GraphicMain.graphicMain.back();
        }
    }

    public void back() {
        GraphicMain.graphicMain.back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        informationBox.setAlignment(Pos.CENTER);
        informationBox.setLineSpacing(15);
        String response = DataRequestBuilder.buildAuctionRequestWithID(GraphicMain.currentAuctionId);
        if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
            return;
        }
        Auction auction = GeneralController.yagsonMapper.fromJson(response, Auction.class);
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
                initializeMessagePane();
                Timeline update = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        updatePage();
                    }
                }));
                update.setCycleCount(Timeline.INDEFINITE);
                update.play();
            }
        } catch (Exception e) {
            informationBox.setText("the auction is over");
            disablePage();
        }
    }

    private void updatePage() {
        String response = DataRequestBuilder.buildHighestOfferRequest();
        if (response.equals("auctionOver")) {
            GraphicMain.showInformationAlert("auction is over");
            disablePage();
            return;
        } else if (response.equals("tooManyRequests")) {

        } else {
            highestOffer.setText(response);
        }

        String response2 = DataRequestBuilder.buildMessageNoRequest();
        if (response2.equals("auctionOver")) {
            GraphicMain.showInformationAlert("auction is over");
            disablePage();
            return;
        } else if (response2.equals("tooManyRequests")) {

        } else {
            int messageNo = Integer.parseInt(response2);
            if (messageNo != this.messageNo) {
                try {
                    initializeMessagePane();
                } catch (Exception e) {
                    informationBox.setText("the auction is over");
                    disablePage();
                }
            }
        }
    }

    private void initializeMessagePane() throws Exception {
        String response = DataRequestBuilder.buildAuctionRequestWithID(GraphicMain.currentAuctionId);
        if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
            return;
        }
        Auction auction = GeneralController.yagsonMapper.fromJson(response, Auction.class);
        if (auction == null) {
            throw new Exception();
        }
        int i = 0;
        ArrayList<String> allMessages = auction.getAuctionUsage().getAllMessages();
        messageNo = allMessages.size();
        messagePane.getChildren().clear();
        for (String message : allMessages) {
            i++;
            Label label = new Label(message);
            label.getStyleClass().add("messageType" + ((i % 8) + 1));
            label.setPadding(new Insets(40, 40, 40, 40));
            label.setMinHeight(90);
            label.setMinWidth(250);
            label.setMaxWidth(250);
            label.setAlignment(Pos.CENTER);
            label.setWrapText(true);
            label.setTranslateX((Math.pow(-1, i) + 1) * (72));
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
        String address = new ReceiverAddressWindow().getReceiverAddress();
        String response = BuyerRequestBuilder.buildIncreaseAuctionPriceRequest(increaseAmount.getText(), address);
        if (response.startsWith("success")) {
            try {
                highestOffer.setText(Double.parseDouble(response.split("#")[1]) + "");
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
        } else if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        }
    }


    public void send(MouseEvent mouseEvent) {
        String response = BuyerRequestBuilder.buildSendMessageRequest(message.getText());
        if (response.equals("success")) {
            try {
                message.setText("");
                initializeMessagePane();
            } catch (Exception e) {
                informationBox.setText("the auction is over");
                disablePage();
            }
        } else {
            showSendMessageResponse(response);
        }
    }

    private void showSendMessageResponse(String response) {
        if (response.equals("loginNeeded")) {
            informationBox.setText("you must login first !\nyou'r authentication might be expired !");
        } else if (response.equals("auctionOver")) {
            informationBox.setText("sorry auction is over");
            disablePage();
        } else if (response.equals("emptyText")) {
            informationBox.setText("empty messages can't be sent");
        } else if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        }
    }

    private class ReceiverAddressWindow {
        private String address;

        public String getReceiverAddress() {
            //GraphicMain.buttonSound.stop();
            //GraphicMain.buttonSound.play();

            Stage receiverAddress = new Stage();
            receiverAddress.setTitle("Receiver Info");

            VBox vBox = new VBox();
            vBox.setPadding(new Insets(30, 0, 30, 30));
            vBox.setSpacing(50);
            vBox.setAlignment(Pos.CENTER);

            Label label = new Label("insert the address for this product to be sent !");
            TextArea textArea = new TextArea();
            Button button = new Button("submit");

            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!textArea.getText().isEmpty()) {
                        address = textArea.getText();
                        receiverAddress.close();
                    }
                }
            });

            receiverAddress.initModality(Modality.APPLICATION_MODAL);
            receiverAddress.setOnCloseRequest(Event::consume);

            vBox.getChildren().add(label);
            vBox.getChildren().add(textArea);
            vBox.getChildren().add(button);


            Scene scene = new Scene(vBox, 750, 400);
            receiverAddress.setScene(scene);
            receiverAddress.show();

            return address;
        }
    }

}
