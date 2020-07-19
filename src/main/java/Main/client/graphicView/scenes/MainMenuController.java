package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.DataRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {
    public static final String FXML_PATH = "src/main/sceneResources/mainMenu.fxml";
    public static final String TITLE = "Main Menu";

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        String response = GeneralRequestBuilder.buildLogoutRequest();
        if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else {
            GraphicMain.token = "0000";
            //goBack();
            GraphicMain.graphicMain.back();
        }
    }

    public void goToUserPanel(MouseEvent mouseEvent) throws IOException {
        String response = DataRequestBuilder.buildUserTypeRequest();
        if (response.equals("manager")) {
            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
        } else if (response.equals("seller")) {
            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
        } else if (response.equals("buyer")) {
            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
        } else if (response.equals("loginNeeded")) {
            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
        } else {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        }
        //GraphicMain.audioClip.stop();
        //LoginSignUpPage.mediaPlayer.play();
    }

    public void goToOffs(MouseEvent mouseEvent) throws IOException {
        GraphicMain.graphicMain.goToPage(OffPage.FXML_PATH, OffPage.TITLE);
    }

    public void goToProducts(MouseEvent mouseEvent) throws IOException {
        GraphicMain.graphicMain.goToPage(ProductsPage.FXML_PATH, ProductsPage.TITLE);
    }

    public void grow(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setFitWidth(imageView.getFitWidth() + imageView.getFitWidth() / 40);
        imageView.setFitHeight(imageView.getFitHeight() + imageView.getFitHeight() / 40);
    }

    public void shrink(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setFitWidth(imageView.getFitWidth() * 40 / 41);
        imageView.setFitHeight(imageView.getFitHeight() * 40 / 41);
    }

    public void goToAuctions(MouseEvent mouseEvent) throws IOException {
        GraphicMain.graphicMain.goToPage(AuctionsPage.FXML_PATH, AuctionsPage.TITLE);
    }

    public void back(MouseEvent mouseEvent) {
        GraphicMain.graphicMain.back();
    }
}
