package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.DataRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.Auction;
import Main.server.model.Product;
import Main.server.model.exceptions.DiscountAndOffTypeServiceException;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.util.Arrays.asList;

public class AuctionsPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/auctions.fxml";
    public static final String TITLE = "Auctions";
    public ListView auctionsList;
    private ArrayList<Auction> allAuctions = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        SellerAccount sellerAccount = new SellerAccount("username", "firstname", "lastname"
//                , "example@exp.exp", "09000000000", "00000000", "companyName"
//                ,"this company is great!", 100,null);
//        Product product = new Product("apple", "b", 10, "d", 10,sellerAccount);
//        Product product2 = new Product("laptop", "b", 10, "d", 10, sellerAccount);
//        product.addProduct(product);
//        product2.addProduct(product2);
//        SellerAccount.addSeller(sellerAccount);
//        Auction auction1 = new Auction(product, "1200/02/02 12:12:12", "2022/02/02 12:12:12",sellerAccount);
//        Auction auction2 = new Auction(product2, "1300/02/02 12:12:12", "2032/02/02 12:12:12",sellerAccount);
//        Auction.addAuction(auction1);
//        Auction.addAuction(auction2);

        String allAuctionsData = DataRequestBuilder.buildAllAuctionsRequest();
        if (allAuctionsData.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
            return;
        }
        readAllAuctionsData(allAuctionsData);

        auctionsList.getItems().clear();

        for (int i = 0; i < allAuctions.size(); i++) {
            Auction auction3 = allAuctions.get(i);
            try {
                auctionsList.getItems().add(auction3.getAuctionUsage().viewSummary());
            } catch (Exception e) {
                if (i > 0) {
                    i--;
                }
            }
        }

        auctionsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (auctionsList.getSelectionModel().getSelectedItem() != null) {
                    String id = auctionsList.getSelectionModel().getSelectedItem().toString();
                    id = id.substring(1, id.indexOf(' '));
                    GraphicMain.currentAuctionId = id;
                    String response = DataRequestBuilder.buildAuctionRequestWithID(GraphicMain.currentAuctionId);
                    if (response.equals("tooManyRequests")) {
                        GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
                    } else {
                        Auction auction = GeneralController.yagsonMapper.fromJson(response, Auction.class);
                        auctionsList.getSelectionModel().clearSelection();
                        try {
                            GraphicMain.currentAuctionId = auction.getAuctionUsage().getId();
                            System.out.println("*");
                            GraphicMain.graphicMain.goToPage(AuctionPage.FXML_PATH, AuctionPage.TITLE);
                        } catch (Exception e) {
                            // GraphicMain.showInformationAlert(e.getMessage());
                            initialize(location, resources);
                        }
                    }
                }
            }
        });

    }

    private void readAllAuctionsData(String allAuctionsResponse) {
        GeneralController.jsonReader = new JsonReader(new StringReader(allAuctionsResponse));
        Auction[] allAuc = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Auction[].class);
        allAuctions = (allAuc == null) ? new ArrayList<>() : new ArrayList<>(asList(allAuc));
    }

    public void addAuction(MouseEvent mouseEvent) {
        String response = DataRequestBuilder.buildAllProductsForAuctionRequest();

        if (response.equals("loginNeeded")) {
            GraphicMain.showInformationAlert("you must login first !\nyou'r authentication might be expired !");
        } else if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else {
            GeneralController.jsonReader = new JsonReader(new StringReader(response));
            Product[] allPro = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Product[].class);
            ArrayList<Product> allProducts = (allPro == null) ? new ArrayList<>() : new ArrayList<>(asList(allPro));

            showSellersAllProducts(allProducts);
        }
    }

    private void showSellersAllProducts(ArrayList<Product> allProducts) {

        Stage addAuction = new Stage();
        addAuction.setTitle("Add Auction");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(30, 0, 30, 30));
        vBox.setSpacing(50);
        vBox.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(vBox);

        Label label = new Label("choose a product to start an auction");

        vBox.getChildren().add(label);

        for (Product product : allProducts) {
            VBox productBox = product.createProductBoxForCreateAuction();
            productBox.setOnMouseClicked(event -> getAuctionInfo(product));
            vBox.getChildren().add(productBox);
        }

        Scene scene = new Scene(scrollPane, 750, 400);
        addAuction.setScene(scene);
        addAuction.show();
    }

    private void getAuctionInfo(Product product) {
        Stage getInfo = new Stage();
        getInfo.setTitle("Complete Auction Info");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(30, 0, 30, 30));
        vBox.setSpacing(50);
        vBox.setAlignment(Pos.CENTER);

        Label errorMessage = new Label("");
        errorMessage.setTextFill(Color.RED);
        Label label = new Label("insert start and end date");
        TextField startDate = new TextField();
        startDate.setPromptText("start date");
        TextField endDate = new TextField();
        endDate.setPromptText("end date");
        Button button = new Button("submit");

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (startDate.getText().equals("") || endDate.getText().equals("")) {
                    errorMessage.setText("fields cannot be empty");
                } else {
                    try {
                        DiscountAndOffTypeServiceException.validateInputDate(startDate.getText());
                        DiscountAndOffTypeServiceException.validateInputDate(endDate.getText());
                    } catch (Exception e) {
                        errorMessage.setText(e.getMessage());
                    }
                    String response = SellerRequestBuilder.buildCreateAuctionRequest(startDate.getText(), endDate.getText(), product.getProductId());
                    if (response.equals("success")) {
                        getInfo.close();
                        GraphicMain.showInformationAlert("auction added successfully");
                    } else if (response.equals("tooManyRequests")) {
                        GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
                    } else {
                        errorMessage.setText("you must login first");
                    }
                }
            }
        });

        vBox.getChildren().add(label);
        vBox.getChildren().add(startDate);
        vBox.getChildren().add(endDate);

        Scene scene = new Scene(vBox, 750, 400);
        getInfo.setScene(scene);
        getInfo.show();
    }

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
        } else if (response.equals("supporter")) {
            GraphicMain.graphicMain.goToPage(SupporterPanelController.FXML_PATH, SupporterPanelController.TITLE);
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
}
