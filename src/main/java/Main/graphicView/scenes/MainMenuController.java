package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import javafx.scene.input.MouseEvent;

public class MainMenuController {
    public static final String FXML_PATH = "src/main/sceneResources/mainMenu.fxml";
    public static final String TITLE = "Main Menu";

    public void goToUserPanelMenu(){

    }

    public void exitProgram(){

    }

    public void goToffsMenu(){

    }

    public void goToProductsMenu(){

    }

    public void exit(MouseEvent mouseEvent) {
        GraphicMain.graphicMain.exitProgram();
    }
}
