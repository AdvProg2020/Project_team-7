package Main.graphicView.scenes.BuyerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class BuyerPanelController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/BuyerPanel.fxml";
    public static final String TITLE = "Buyer user panel";

    public void goToPersonalInformation() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/BuyerPanel/ViewPersonalInformation.fxml", "Buyer Personal Information");
    }

    public void goToMyOrders() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/BuyerPanel/ManageOrders.fxml", "My Orders");
    }

    public void goToMyDiscounts() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/BuyerPanel/ViewDiscountCodes.fxml","My Discounts");
    }

    public void goToMyCart() throws IOException {
        GraphicMain.graphicMain.goToPage("src/main/sceneResources/BuyerPanel/ViewCart.fxml","My Cart");
    }

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
