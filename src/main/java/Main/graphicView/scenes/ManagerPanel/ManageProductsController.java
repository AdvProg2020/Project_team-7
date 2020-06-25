package Main.graphicView.scenes.ManagerPanel;

import Main.controller.ManagerController;
import Main.graphicView.GraphicMain;
import Main.model.Product;
import Main.model.accounts.Account;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class ManageProductsController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageAllProducts.fxml";
    public static final String TITLE = "Manage Products";

    @FXML
    private ListView productList;

    public void initialize(){
        productList.getItems().clear();
        productList.getItems().addAll(Product.summaryProductInfo());
        productList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (productList.getSelectionModel().getSelectedItem()!=null) {
                    String id = productList.getSelectionModel().getSelectedItem().toString();
                    id = id.substring(1, id.indexOf(' '));
                    productList.getSelectionModel().clearSelection();
                    Product product = null;
                    try {
                        product = Product.getProductWithId(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        goToProductOrDelete(product);
                    } catch (Exception e) {
                        e.printStackTrace();
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
        alert.getButtonTypes().addAll(done,delete,goToPage);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(delete)){
            GraphicMain.managerController.removeProductWithId(product.getProductId());
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Product Deleted");
            alert1.setHeaderText(null);
            alert1.setContentText("Product deleted successfully.");
            alert1.showAndWait();
            initialize();
        } else if (option.get().equals(goToPage)){
            //TODO really go to product page
            System.out.println("safhe mahsoul :)");
        }
    }

    public void goBack() throws IOException {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}
