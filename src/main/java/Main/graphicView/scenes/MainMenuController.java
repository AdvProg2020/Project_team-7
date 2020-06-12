package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import javafx.scene.image.ImageView;
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

    public void goToUserPanel(MouseEvent mouseEvent) {
    }

    public void goToOffs(MouseEvent mouseEvent) {
    }

    public void goToProducts(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
    }

    public void grow(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setFitWidth(imageView.getFitWidth()+imageView.getFitWidth()/40);
        imageView.setFitHeight(imageView.getFitHeight()+imageView.getFitHeight()/40);
    }

    public void shrink(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setFitWidth(imageView.getFitWidth()*40/41);
        imageView.setFitHeight(imageView.getFitHeight()*40/41);
    }
}
