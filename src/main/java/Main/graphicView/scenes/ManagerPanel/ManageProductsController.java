package Main.graphicView.scenes.ManagerPanel;

import Main.graphicView.GraphicMain;

import java.io.IOException;

public class ManageProductsController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageAllProducts.fxml";
    public static final String TITLE = "Manage Products";

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
