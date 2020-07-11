package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import Main.server.model.exceptions.CreateProductException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class AddProductPage{

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/addProductPage.fxml";
    public static final String TITLE = "Add Product";

    @FXML
    private CheckBox hasCategory;
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
    private TextField categoryName;

    public static CreateProductException.GetCategoryFromUser exception;


    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    public void submit() throws IOException {
        ArrayList<String> productInfo = new ArrayList<>();
        productInfo.add(name.getText());
        productInfo.add(brand.getText());
        productInfo.add(availability.getText());
        productInfo.add(description.getText());
        productInfo.add(price.getText());
        if(hasCategory.isSelected()){
            productInfo.add("yes");
            productInfo.add(categoryName.getText());
        } else{
            productInfo.add("no");
            productInfo.add("-");
        }
        try{
            GraphicMain.sellerController.addProduct(productInfo);
            showInformationAlert("product created successfully");
        } catch (CreateProductException.InvalidProductInputInfo e) {
            showErrorAlert(e.getMessage());
        } catch (CreateProductException.GetCategoryFromUser e) {
            exception = e;
            GraphicMain.graphicMain.goToPage(AddProductSpecialFeatures.FXML_PATH,AddProductSpecialFeatures.TITLE);
        }
    }

    public void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }

    public void showInformationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void logout() throws IOException{
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }
}
