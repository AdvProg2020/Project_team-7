package Main.graphicView.scenes.ManagerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class ManageRequestsController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageRequests.fxml";
    public static final String TITLE = "Manage Requests";

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
