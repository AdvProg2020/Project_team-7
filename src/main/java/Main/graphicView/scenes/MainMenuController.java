package Main.graphicView.scenes;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import Main.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.accounts.SellerAccount;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {
    public static final String FXML_PATH = "src/main/sceneResources/mainMenu.fxml";
    public static final String TITLE = "Main Menu";

    public void exit(MouseEvent mouseEvent) {
        GraphicMain.graphicMain.exitProgram();
    }

    public void goToUserPanel(MouseEvent mouseEvent) throws IOException {
        Account account = GeneralController.currentUser;
        if (account instanceof ManagerAccount) {
            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
        } else if (account instanceof SellerAccount) {
            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
        } else if (account instanceof BuyerAccount) {
            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
        } else {
            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
        }
        GraphicMain.audioClip.stop();
        LoginSignUpPage.mediaPlayer.play();
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
}
