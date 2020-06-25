package Main.graphicView.scenes.ManagerPanel;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

public class ManagerPersonalInfoController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ViewPersonalInformation.fxml";
    public static final String TITLE = "Manager Personal Information";

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

    public void initialize() {
        try {
            username.setText(GeneralController.currentUser.getUserName());
            password.setText(GeneralController.currentUser.getPassWord());
            email.setText(GeneralController.currentUser.getEmail());
            phoneNumber.setText(GeneralController.currentUser.getPhoneNumber());
            firstName.setText(GeneralController.currentUser.getFirstName());
            lastName.setText(GeneralController.currentUser.getLastName());
        } catch (NullPointerException n){
            System.err.println("null");
        }
    }

    public void saveChanges() {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Edit");
        alert.setHeaderText(null);
        alert.setContentText("are you sure to save the changes?");
        Optional<ButtonType> option = alert.showAndWait();
        if (ButtonType.OK.equals(option.get())) {
            try {
                //TODO if there was time, connect this to old edit methods
                GeneralController.currentUser.setEmail(email.getText());
                GeneralController.currentUser.setFirstName(firstName.getText());
                GeneralController.currentUser.setLastName(lastName.getText());
                GeneralController.currentUser.setPhoneNumber(phoneNumber.getText());
                GeneralController.currentUser.setPassWord(password.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            goBack();
        }
    }

    public void goBack() {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}
