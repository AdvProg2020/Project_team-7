package Main.client.graphicView.scenes;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AuctionsPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/auctions.fxml";
    public static final String TITLE = "Auctions";
    public ListView auctionsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auctionsList.getItems().clear();

    }

    public void addAuction(MouseEvent mouseEvent) {

    }
}
