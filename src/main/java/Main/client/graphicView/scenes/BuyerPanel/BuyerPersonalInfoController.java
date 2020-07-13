package Main.client.graphicView.scenes.BuyerPanel;

import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.server.serverRequestProcessor.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class BuyerPersonalInfoController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ViewPersonalInformation.fxml";
    public static final String TITLE = "Buyer Personal Information";

    @FXML
    private Label username;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private Pane pane;

    public void initialize() {
        try {
//            username.setText(GeneralController.currentUser.getUserName());
//            password.setText(GeneralController.currentUser.getPassWord());
//            email.setText(GeneralController.currentUser.getEmail());
//            phoneNumber.setText(GeneralController.currentUser.getPhoneNumber());
//            firstName.setText(GeneralController.currentUser.getFirstName());
//            lastName.setText(GeneralController.currentUser.getLastName());
//            ImageView profileImage = new ImageView(new Image(new File(GeneralController.currentUser.getProfileImagePath()).toURI().toString()));
            String[] userInfo = BuyerRequestBuilder.buildBuyerPersonalInformationRequest().split("#");
            String firstNameData = userInfo[0];
            String lastNameData = userInfo[1];
            String userNameData = userInfo[2];
            String emailData = userInfo[3];
            String phoneNumberData = userInfo[4];
            String passWordData = userInfo[5];
            String imagePathData = userInfo[6];
            username.setText(userNameData);
            password.setText(passWordData);
            email.setText(emailData);
            phoneNumber.setText(phoneNumberData);
            firstName.setText(firstNameData);
            lastName.setText(lastNameData);
            ImageView profileImage = new ImageView(new Image(new File(imagePathData).toURI().toString()));
            profileImage.setFitWidth(120);
            profileImage.setFitHeight(120);
            pane.getChildren().add(profileImage);
        } catch (NullPointerException n) {
            ManagerPanelController.alertError("Sorry! an error occurred.");
        }
    }

    public void saveChanges() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Edit");
        alert.setHeaderText(null);
        alert.setContentText("are you sure to save the changes?");
        Optional<ButtonType> option = alert.showAndWait();
        if (ButtonType.OK.equals(option.get())) {
            if (email.getText().isEmpty())
                email.setStyle("-fx-border-color:red; -fx-border-width: 3;");
            else
                email.setStyle("-fx-border-width: 0;");
            if (firstName.getText().isEmpty())
                firstName.setStyle("-fx-border-color:red; -fx-border-width: 3;");
            else
                firstName.setStyle("-fx-border-width: 0;");
            if (lastName.getText().isEmpty())
                lastName.setStyle("-fx-border-color:red; -fx-border-width: 3;");
            else
                lastName.setStyle("-fx-border-width: 0;");
            if (phoneNumber.getText().isEmpty())
                phoneNumber.setStyle("-fx-border-color:red; -fx-border-width: 3;");
            else
                phoneNumber.setStyle("-fx-border-width: 0;");
            if (password.getText().isEmpty())
                password.setStyle("-fx-border-color:red; -fx-border-width: 3;");
            else
                password.setStyle("-fx-border-width: 0;");
            if (!email.getText().isEmpty() && !firstName.getText().isEmpty() && !lastName.getText().isEmpty() && !phoneNumber.getText().isEmpty() && !password.getText().isEmpty()) {
                try {
                    GeneralController.currentUser.setEmail(email.getText());
                    GeneralController.currentUser.setFirstName(firstName.getText());
                    GeneralController.currentUser.setLastName(lastName.getText());
                    GeneralController.currentUser.setPhoneNumber(phoneNumber.getText());
                    GeneralController.currentUser.setPassWord(password.getText());
                } catch (Exception e) {
                    ManagerPanelController.alertError("Sorry! An error occurred.");
                }
                goBack();
            }
        }
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
    public void logout() throws IOException {
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }
}
