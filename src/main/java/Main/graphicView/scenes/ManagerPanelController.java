package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class ManagerPanelController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManagerPanel.fxml";
    public static final String TITLE = "Manager user panel";

    public void goToPersonalInformation() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/ManagerPanel/ViewPersonalInformation.fxml","My Personal Information");
    }

    public void goToManageUsers() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/ManagerPanel/ManageUsers.fxml","Manage Users");
    }

    public void goToManageRequests() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/ManagerPanel/ManageRequests.fxml","Manage Requests");
    }

    public void goToManageDiscounts() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/ManagerPanel/ManageDiscountCodes.fxml","Manage Discounts");
    }

    public void goToManageProducts() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/ManagerPanel/ManageAllProducts.fxml","Manage Products");
    }

    public void goToManageCategories() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/ManagerPanel/ManageCategories.fxml","Manage Categories");
    }

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
