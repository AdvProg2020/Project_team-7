package Main.client.graphicView.scenes.BuyerPanel;

import Main.client.graphicView.GraphicMain;

public class RateController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/RateProduct.fxml";
    public static final String TITLE = "Rate a product";

    public void goBack() {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}
