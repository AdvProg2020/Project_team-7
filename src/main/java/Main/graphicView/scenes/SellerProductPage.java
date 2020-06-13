package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerProductPage implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/sellerProductPage.fxml";
    public static final String TITLE = "Seller Product page";
    public String productId;

    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productId = SellerProductsPage.selectedProduct;

    }
}
