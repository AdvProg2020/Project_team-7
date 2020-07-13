package Main.client.graphicView.scenes.BuyerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;

import java.io.IOException;

public class HelpCenterController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/HelpCenter.fxml";
    public static final String TITLE = "Help Center";

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
    public void logout() throws IOException {
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }
}
