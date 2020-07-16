package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.Auction;
import Main.server.model.Product;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AuctionsPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/auctions.fxml";
    public static final String TITLE = "Auctions";
    public ListView auctionsList;
    private ArrayList<Auction> allAuctions = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Product product = new Product("apple", "b", 10, "d", 10, null);
        Product product2 = new Product("laptop", "b", 10, "d", 10, null);
        Auction auction1 = new Auction(product, "1200/02/02 12:12:12", "2022/02/02 12:12:12");
        Auction auction2 = new Auction(product2, "1300/02/02 12:12:12", "2032/02/02 12:12:12");
        Auction.addAuction(auction1);
        Auction.addAuction(auction2);

        auctionsList.getItems().clear();
        allAuctions = Auction.getAllAuctions();
        for (Auction auction : allAuctions) {
            try {
                Auction.AuctionUsage auctionUsage = auction.getAuctionUsage();
                auctionsList.getItems().add(auctionUsage.viewSummary());

            } catch (Exception e) {
            }
        }

        auctionsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (auctionsList.getSelectionModel().getSelectedItem() != null) {
                    String id = auctionsList.getSelectionModel().getSelectedItem().toString();
                    id = id.substring(1, id.indexOf(' '));
                    Auction auction = Auction.getAuctionById(id);
                    auctionsList.getSelectionModel().clearSelection();
                    try {
                        Auction.AuctionUsage auctionUsage = auction.getAuctionUsage();
                        GraphicMain.currentAuctionId = auctionUsage.getId();
                        GraphicMain.graphicMain.goToPage(AuctionPage.FXML_PATH,AuctionPage.TITLE);
                    } catch (Exception e) {
                        GraphicMain.showInformationAlert(e.getMessage());
                        initialize(location,resources);
                    }
                }
            }
        });

    }

    public void addAuction(MouseEvent mouseEvent) {

    }

    public void goToUserPanel(MouseEvent mouseEvent) throws IOException {
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
}
