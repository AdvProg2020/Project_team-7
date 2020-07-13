package Main.client.graphicView.scenes.ManagerPanel;

import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.ProductPage;
import Main.server.model.Product;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class ManageProductsController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageAllProducts.fxml";
    public static final String TITLE = "Manage Products";

    @FXML
    private ListView productList;

    public void initialize() {
        productList.getItems().clear();
        productList.getItems().addAll(Product.summaryProductInfo());
        productList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (productList.getSelectionModel().getSelectedItem() != null) {
                    String id = productList.getSelectionModel().getSelectedItem().toString();
                    id = id.substring(1, id.indexOf(' '));
                    productList.getSelectionModel().clearSelection();
                    Product product = null;
                    try {
                        product = Product.getProductWithId(id);
                    } catch (Exception e) {
                        ManagerPanelController.alertError(e.getMessage());
                    }
                    try {
                        goToProductOrDelete(product);
                    } catch (Exception e) {
                        ManagerPanelController.alertError(e.getMessage());
                    }
                }
            }
        });
    }

    private void goToProductOrDelete(Product product) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Product Information");
        alert.setHeaderText(product.showProductDigest());
        alert.setContentText("what do you want to do with this product?");
        ButtonType done = new ButtonType("Done!");
        ButtonType delete = new ButtonType("Delete");
        ButtonType goToPage = new ButtonType("Go To Product Page");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(done, delete, goToPage);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(delete)) {
            GraphicMain.managerController.removeProductWithId(product.getProductId());
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Product Deleted");
            alert1.setHeaderText(null);
            alert1.setContentText("Product deleted successfully.");
            alert1.showAndWait();
            initialize();
        } else if (option.get().equals(goToPage)) {
            GeneralController.currentProduct = product;
            GraphicMain.graphicMain.goToPage(ProductPage.FXML_PATH, ProductPage.TITLE);
        }
    }

    public void logout() throws IOException{
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}