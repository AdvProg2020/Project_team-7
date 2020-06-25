package Main.graphicView.scenes.BuyerPanel;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    public void initialize() {
        try {
            username.setText(GeneralController.currentUser.getUserName());
            password.setText(GeneralController.currentUser.getPassWord());
            email.setText(GeneralController.currentUser.getEmail());
            phoneNumber.setText(GeneralController.currentUser.getPhoneNumber());
            firstName.setText(GeneralController.currentUser.getFirstName());
            lastName.setText(GeneralController.currentUser.getLastName());
        } catch (NullPointerException n) {
            System.out.println("unknown null pointer -_-");
        }
    }

    public void saveChanges() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Edit");
        alert.setHeaderText(null);
        alert.setContentText("are you sure to save the changes?");
        Optional<ButtonType> option = alert.showAndWait();
        if (ButtonType.OK.equals(option.get())) {
            //TODO if there was time, connect this to old edit methods
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
                    System.err.println("error");
                }
                goBack();
            }
        }
    }

    public void goBack() {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}
