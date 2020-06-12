package Main.graphicView.scenes;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import Main.model.accounts.Account;
import Main.model.accounts.ManagerAccount;
import Main.model.exceptions.AccountsException;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class RegisterFirstManager implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/loginSignUp/registerFirstManager.fxml";
    public static final String TITLE = "Register Chief Manager";
    public static MediaPlayer mediaPlayer;
    public TextField phoneNumber;
    public TextField email;
    public TextField lastName;
    public TextField firstName;
    public PasswordField password;
    public TextField username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media bgMusic = new Media(Paths.get("src/main/java/Main/graphicView/resources/soundEffects/login-crowd.wav").toUri().toString());
        mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.2);
    }

    private boolean areTextFieldsFilled() {
        boolean isInfoCorrect = true;
        if (username.getText().equals("")) {
            username.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (password.getText().equals("")) {
            password.setStyle("-fx-border-color :RED;");
            isInfoCorrect = false;
        }
        if (email.getText().equals("")) {
            email.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (phoneNumber.getText().equals("")) {
            phoneNumber.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (firstName.getText().equals("")) {
            firstName.setStyle("-fx-border-color :RED;");
            isInfoCorrect = false;
        }
        if (lastName.getText().equals("")) {
            lastName.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }

        return isInfoCorrect;
    }

    private boolean areTextFieldsValid(){
        boolean isInfoValid=true;
        try {
            AccountsException.validateUserName(username.getText());
            try {
                AccountsException.validateUsernameUniqueness(username.getText());
            } catch (AccountsException e) {
                username.setText(e.getErrorMessage());
                username.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
                isInfoValid = false;
            }
        } catch (AccountsException.invalidUserNameException e) {
            username.setText(e.getErrorMessage());
            username.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            isInfoValid=false;
        }
        try {
            AccountsException.validateNameTypeInfo("first name", firstName.getText());
        } catch (AccountsException e) {
            firstName.setText(e.getErrorMessage());
            firstName.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            isInfoValid=false;
        }
        try {
            AccountsException.validateNameTypeInfo("last name", lastName.getText());
        } catch (AccountsException e) {
            lastName.setText(e.getErrorMessage());
            lastName.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            isInfoValid=false;
        }
        try {
            AccountsException.validateEmail(email.getText());
        } catch (AccountsException e) {
            email.setText(e.getErrorMessage());
            email.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            isInfoValid=false;
        }
        try {
            AccountsException.validatePhoneNumber(phoneNumber.getText());
        } catch (AccountsException e) {
            phoneNumber.setText(e.getErrorMessage());
            phoneNumber.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            isInfoValid=false;
        }
        /*
        try {
            AccountsException.validatePassWord(accountInfo.get(0));
        } catch (AccountsException e) {
            accountCreationErrors.append(e.getErrorMessage());
        }
        */
        return isInfoValid;
    }

    public void back(MouseEvent mouseEvent) {
        GraphicMain.graphicMain.back();
    }

    public void signUp(MouseEvent mouseEvent) throws Exception {
        if(areTextFieldsFilled()&&areTextFieldsValid()){
            ManagerAccount managerAccount = new ManagerAccount(username.getText(),firstName.getText(),lastName.getText(),
                    email.getText(),phoneNumber.getText(),password.getText());
            ManagerAccount.addManager(managerAccount);
        }
        GeneralController.currentUser = Account.getUserWithUserName(username.getText());
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
        LoginSignUpPage.mediaPlayer.stop();
        //TODO when back music doest start
    }

    public void resetTextField(MouseEvent mouseEvent) {
        TextField textField = (TextField) mouseEvent.getSource();
        textField.setStyle("-fx-border-color: #230038;-fx-prompt-text-fill : #4d4254;");
        textField.setText("");
    }
}
