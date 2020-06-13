package Main.graphicView.scenes.ManagerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class ManageUsersController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageUsers.fxml";
    public static final String TITLE = "Manage Users";

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
