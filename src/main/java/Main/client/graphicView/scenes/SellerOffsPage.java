package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerOffsPage implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/sellerOffsPage.fxml";
    public static final String TITLE = "Seller Offs page";
    public static String selectedOff;

    @FXML
    private ListView list;

    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.getItems().clear();
//        SellerAccount sellerAccount = (SellerAccount)GeneralController.currentUser;
//        list.getItems().addAll(sellerAccount.getOffIds());
        String[] response = SellerRequestBuilder.getSellerOffList().split("#");
        for (int i = 1; i < response.length; i++) {
            list.getItems().add(response[i]);
        }
        list.setOnMouseClicked(event -> {
            selectedOff = (String) list.getSelectionModel().getSelectedItem();
            list.getSelectionModel().clearSelection();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().clear();
            alert.setTitle("off information");
            alert.setHeaderText(null);
//                alert.setContentText(sellerAccount.getOffWithId(selectedOff).viewMe());
            alert.setContentText(SellerRequestBuilder.getOffDetails(selectedOff));
            ButtonType cancel = new ButtonType("cancel");
            ButtonType edit = new ButtonType("edit");
            alert.getButtonTypes().addAll(cancel, edit);
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(edit)) {
                try {
                    GraphicMain.graphicMain.goToPage(OffEditPage.FXML_PATH, OffEditPage.TITLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
