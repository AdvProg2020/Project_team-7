package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellerProductsPage implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/sellerProductsPage.fxml";
    public static final String TITLE = "Seller Products page";
    public static String selectedProduct;

    @FXML
    private ListView list;

    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.getItems().addAll(GraphicMain.sellerController.getSellerProductNames());
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String string = (String) list.getSelectionModel().getSelectedItem();
                selectedProduct = string.substring(string.indexOf("(")+1 , string.indexOf(")"));
                try {
                    GraphicMain.graphicMain.goToPage(SellerProductPage.FXML_PATH,SellerProductPage.TITLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
