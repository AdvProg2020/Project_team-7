package Main.graphicView.scenes.BuyerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class RateController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/RateProduct.fxml";
    public static final String TITLE = "Rate a product";

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
