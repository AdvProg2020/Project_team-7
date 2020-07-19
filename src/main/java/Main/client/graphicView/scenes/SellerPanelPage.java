package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellerPanelPage implements Initializable {

    @FXML
    private Label personalInfoLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Pane pane;

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/sellerPanelPage.fxml";
    public static final String TITLE = "Seller Panel";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        personalInfoLabel.setText("role: seller\n" + GraphicMain.generalController.viewPersonalInfo());
//        ImageView imageView = new ImageView(new Image(new File(GeneralController.currentUser.getProfileImagePath()).toURI().toString()));
        String[] response = SellerRequestBuilder.getSellerPersonalInformation().split("#");
        personalInfoLabel.setText("role: seller\n" + response[0]);
        ImageView imageView = new ImageView(new Image(new File(response[1]).toURI().toString()));
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);
        pane.getChildren().add(imageView);
    }

    public void goToEditInfoPage() throws IOException {
        GraphicMain.graphicMain.goToPage(EditSellerPersonalInformationPage.FXML_PATH,
                EditSellerPersonalInformationPage.TITLE);
    }

    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    public void viewCompanyInformation() {
        titleLabel.setText("company information");
        personalInfoLabel.setText(SellerRequestBuilder.getSellerCompanyInformation());
//        personalInfoLabel.setText(GraphicMain.sellerController.viewCompanyInformation());
    }

    public void goToSalesHistoryPage() throws IOException {
        GraphicMain.graphicMain.goToPage(SalesHistoryPage.FXML_PATH, SalesHistoryPage.TITLE);
    }

    public void viewBalance() {
//        showInformationAlert(GraphicMain.sellerController.viewSellerBalance());
        showInformationAlert(SellerRequestBuilder.getSellerBalance());
    }

    public void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void goToSellerProductsPage() throws IOException {
        GraphicMain.graphicMain.goToPage(SellerProductsPage.FXML_PATH, SellerProductsPage.TITLE);
    }

    public void goToAddProductPage() throws IOException {
        GraphicMain.graphicMain.goToPage(AddProductPage.FXML_PATH, AddProductPage.TITLE);
    }

    public void showCategories() {
//        String categories = GraphicMain.generalController.showAllCategories();
//        showInformationAlert(categories);
        showInformationAlert(SellerRequestBuilder.getSellerCategories());
    }

    public void goToSellerOffsPage() throws IOException {
        GraphicMain.graphicMain.goToPage(SellerOffsPage.FXML_PATH, SellerOffsPage.TITLE);
    }

    public void goToAddOffPage() throws IOException {
        GraphicMain.graphicMain.goToPage(AddOffPage.FXML_PATH, AddOffPage.TITLE);
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        goBack();
    }
}
