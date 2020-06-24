package Main.graphicView.scenes;

import Main.controller.SellerController;
import Main.graphicView.GraphicMain;
import Main.model.Product;
import Main.model.requests.EditProductRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProductPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/editProductPage.fxml";
    public static final String TITLE = "Edit Product page";
    private Product product;

    @FXML
    private TextField name;
    @FXML
    private TextField brand;
    @FXML
    private TextField availability;
    @FXML
    private TextField description;
    @FXML
    private TextField price;
    @FXML
    private TextField offId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setProduct();
        name.setPromptText(product.getName());
        brand.setPromptText(product.getBrand());
        availability.setPromptText(Integer.toString(product.getAvailability()));
        description.setPromptText(product.getDescription());
        price.setPromptText(Double.toString(product.getPrice()));
        if(product.getOff() !=null)
            offId.setPromptText(product.getOff().getOffId());
        else
            offId.setPromptText("-");

    }

    public void setProduct(){
        try {
            product = Product.getProductWithId(SellerProductPage.productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitEdits(){
        try {
            EditProductRequest editProductRequest = GraphicMain.sellerController.getProductToEdit(product.getProductId());
            if(!name.getText().equals("")){
                editProductRequest.addEditedFieldTitle("name");
                editProductRequest.setName(name.getText());
            }
            if(!brand.getText().equals("")){
                editProductRequest.addEditedFieldTitle("brand");
                editProductRequest.setName(brand.getText());
            }
            if(!availability.getText().equals("")){
                editProductRequest.addEditedFieldTitle("availability");
                editProductRequest.setName(availability.getText());
            }
            if(!name.getText().equals("")){
                editProductRequest.addEditedFieldTitle("description");
                editProductRequest.setName(description.getText());
            }
            if(!name.getText().equals("")){
                editProductRequest.addEditedFieldTitle("price");
                editProductRequest.setName(price.getText());
            }
            if(!name.getText().equals("")){
                editProductRequest.addEditedFieldTitle("off");
                editProductRequest.setName(offId.getText());
            }
            GraphicMain.sellerController.submitProductEdits(editProductRequest);
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }

    }

    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    public void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }
}
