package Main.graphicView.scenes.BuyerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class MyOrdersController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ManageOrders.fxml";
    public static final String TITLE = "My Orders";

    public void goBack() throws IOException {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void goToRateProduct() throws IOException {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(RateController.FXML_PATH, RateController.TITLE);
    }
}
