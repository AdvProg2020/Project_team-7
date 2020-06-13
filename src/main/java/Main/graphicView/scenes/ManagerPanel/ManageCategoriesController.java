package Main.graphicView.scenes.ManagerPanel;

import Main.graphicView.GraphicMain;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ManageCategoriesController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageCategories.fxml";
    public static final String TITLE = "Manage Categories";

    @FXML
    private TextArea area;

    public void initialize(){
        area.setText(GraphicMain.generalController.showAllCategories());
    }

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
