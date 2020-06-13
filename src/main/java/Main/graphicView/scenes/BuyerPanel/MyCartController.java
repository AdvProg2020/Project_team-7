package Main.graphicView.scenes.BuyerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class MyCartController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ViewCart.fxml";
    public static final String TITLE = "My Cart";

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
