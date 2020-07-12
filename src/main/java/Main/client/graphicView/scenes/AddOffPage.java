package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddOffPage implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/addOffPage.fxml";
    public static final String TITLE = "Add Off";
    private ArrayList<String> productIdList = new ArrayList<>();
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private TextField offAmount;
    @FXML
    private ListView list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.getItems().addAll(GraphicMain.sellerController.getSellerProductNames());
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String string = (String) list.getSelectionModel().getSelectedItem();
                String id = string.substring(string.indexOf("(")+1 , string.indexOf(")"));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle(null);
                alert.setContentText("Are you sure you want to add this product?");
                Optional<ButtonType> selectedButton = alert.showAndWait();
                if(ButtonType.OK.equals(selectedButton.get())){
                    productIdList.add(id);
                    showInformationAlert("product added successfully");
                }
            }
        });
    }

    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    public void submit(){
        ArrayList<String> offInfo = new ArrayList<>();
        offInfo.add(startDate.getText());
        offInfo.add(endDate.getText());
        offInfo.add(offAmount.getText());
        try {
            GraphicMain.sellerController.addOff(productIdList,offInfo);
            showInformationAlert("off created successfully");
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }

    }
    public void showInformationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    public void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }

    public void logout() throws IOException{
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }
}
