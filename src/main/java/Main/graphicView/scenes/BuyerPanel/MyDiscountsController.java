package Main.graphicView.scenes.BuyerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class MyDiscountsController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ViewDiscountCodes.fxml";
    public static final String TITLE = "My Discounts";

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
