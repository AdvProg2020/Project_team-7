package Main.client.graphicView.scenes.ManagerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.requestBuilder.DataRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerPanelController implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManagerPanel.fxml";
    public static final String TITLE = "Manager user panel";
    public Label shopAccountBalance;

    public void goToPersonalInformation() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(ManagerPersonalInfoController.FXML_PATH, ManagerPersonalInfoController.TITLE);
    }

    public void goToManageUsers() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(ManageUsersController.FXML_PATH, ManageUsersController.TITLE);
    }

    public void goToManageRequests() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(ManageRequestsController.FXML_PATH, ManageRequestsController.TITLE);
    }

    public void goToManageDiscounts() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(ManageDiscountsController.FXML_PATH, ManageDiscountsController.TITLE);
    }

    public void goToManageProducts() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(ManageProductsController.FXML_PATH, ManageProductsController.TITLE);
    }

    public void goToManageCategories() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(ManageCategoriesController.FXML_PATH, ManageCategoriesController.TITLE);
    }

    public void goBack() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        //GraphicMain.graphicMain.back();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

    public static void alertError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR!");
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    public static void alertInfo(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        goBack();
        //GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

    public void goToManageBuyLogs(ActionEvent actionEvent) throws IOException {
        GraphicMain.graphicMain.goToPage(ManageOrdersController.FXML_PATH, ManageOrdersController.TITLE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String response2 = DataRequestBuilder.buildAccountBalanceRequest();
        if (response2.equals("failure")) {
            GraphicMain.showInformationAlert("try again");
        } else if (response2.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else if (response2.equals("loginNeeded")) {
            GraphicMain.showInformationAlert("you must login first !\nyou'r authentication might be expired !");
        } else {
            shopAccountBalance.setText(response2);
        }
    }
}
