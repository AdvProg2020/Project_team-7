package Main.graphicView.scenes.ManagerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class ManageCategoriesController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageCategories.fxml";
    public static final String TITLE = "Manage Categories";

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
