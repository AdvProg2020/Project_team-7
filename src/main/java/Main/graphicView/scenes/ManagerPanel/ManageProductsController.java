package Main.graphicView.scenes.ManagerPanel;

import Main.graphicView.GraphicMain;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ManageProductsController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageAllProducts.fxml";
    public static final String TITLE = "Manage Products";

    @FXML
    private TextArea area;

    public void initialize(){
        area.setText(GraphicMain.generalController.showAllProducts());
    }

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
