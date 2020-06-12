package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellerPanelPage implements Initializable{

    @FXML
    private Label personalInfoLabel;

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/sellerPanelPage.fxml";
    public static final String TITLE = "Seller Panel";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        personalInfoLabel.setText(GraphicMain.generalController.viewPersonalInfo());
    }

    public void goToEditInfoPage() throws IOException {
        GraphicMain.graphicMain.goToPage(EditSellerPersonalInformationPage.FXML_PATH,
                EditSellerPersonalInformationPage.TITLE);
    }

    public void goBack(){
        GraphicMain.graphicMain.back();
    }
}
