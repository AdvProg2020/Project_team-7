package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
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
        list.getItems().clear();
        list.getItems().addAll(GraphicMain.sellerController.getSellerProductNames());
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String string = (String) list.getSelectionModel().getSelectedItem();
                selectedProduct = string.substring(string.indexOf("(")+1 , string.indexOf(")"));
                list.getSelectionModel().clearSelection();
                try {
                    GraphicMain.graphicMain.goToPage(SellerProductPage.FXML_PATH,SellerProductPage.TITLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logout() throws IOException{
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }
}
