package Main.graphicView.scenes.ManagerPanel;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;

public class ManagerPanelController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManagerPanel.fxml";
    public static final String TITLE = "Manager user panel";

    public void goToPersonalInformation() throws IOException {
        GraphicMain.graphicMain.goToPage(ManagerPersonalInfoController.FXML_PATH,ManagerPersonalInfoController.TITLE);
    }

    public void goToManageUsers() throws IOException {
        GraphicMain.graphicMain.goToPage(ManageUsersController.FXML_PATH,ManageUsersController.TITLE);
    }

    public void goToManageRequests() throws IOException {
        GraphicMain.graphicMain.goToPage(ManageRequestsController.FXML_PATH,ManageRequestsController.TITLE);
    }

    public void goToManageDiscounts() throws IOException {
        GraphicMain.graphicMain.goToPage(ManageDiscountsController.FXML_PATH,ManageDiscountsController.TITLE);
    }

    public void goToManageProducts() throws IOException {
        GraphicMain.graphicMain.goToPage(ManageProductsController.FXML_PATH,ManageProductsController.TITLE);
    }

    public void goToManageCategories() throws IOException {
        GraphicMain.graphicMain.goToPage(ManageCategoriesController.FXML_PATH,ManageCategoriesController.TITLE);
    }

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }

    public static void alertError(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR!");
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    public static void alertInfo(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(text);
        alert.showAndWait();
    }
}
