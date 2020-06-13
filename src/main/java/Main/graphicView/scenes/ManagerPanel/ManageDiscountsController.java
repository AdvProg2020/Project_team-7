package Main.graphicView.scenes.ManagerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class ManageDiscountsController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageDiscountCodes.fxml";
    public static final String TITLE = "Manage Discounts";

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
