package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
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
    public static final String TITLE = "Seller Products Page";
    public static String selectedProduct;

    @FXML
    private ListView list;

    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.getItems().clear();
        String[] names = SellerRequestBuilder.getSellerProductsList().split("#");
        for (int i = 1; i < names.length; i++) {
            list.getItems().add(names[i]);
        }
//        list.getItems().addAll(GraphicMain.sellerController.getSellerProductNames(null));
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String string = (String) list.getSelectionModel().getSelectedItem();
                selectedProduct = string.substring(string.indexOf("(") + 1, string.indexOf(")"));
                list.getSelectionModel().clearSelection();
                try {
                    GraphicMain.graphicMain.goToPage(SellerProductPage.FXML_PATH, SellerProductPage.TITLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }
}
