package Main.client.graphicView.scenes;

import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.server.model.accounts.Account;
import Main.server.model.exceptions.AccountsException;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LoginSignUpPage implements Initializable {
    private static final String NO_USERNAME_GIVEN = "enter a username!";
    private static final String NO_PASSWORD_GIVEN = "enter a password!";
    public static final String FXML_PATH = "src/main/sceneResources/loginSignUp/loginSignUpPage.fxml";
    public static final String TITLE = "Login/Sign Up";
    public static MediaPlayer mediaPlayer;
    public TextField signUpUsername;
    public PasswordField signUpPassword;
    public PasswordField loginPassword;
    public TextField loginUsername;
    public Label loginErrorMessage;
    public Label signUpErrorMessage;
    public static String signUpInputPassword;
    public static String getSignUpInputUsername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Media bgMusic = new Media(Paths.get("src/main/java/Main/graphicView/resources/soundEffects/login-crowd.wav").toUri().toString());
        mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.2);
    }

    private boolean areTextFieldsFilled(TextField userName, PasswordField passWord) {
        boolean isInfoCorrect = true;
        if (userName.getText().equals("")) {
            userName.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (passWord.getText().equals("")) {
            passWord.setStyle("-fx-border-color :RED;");
            isInfoCorrect = false;
        }
        return isInfoCorrect;
    }


    private boolean areLoginTextFieldsValid() throws Exception {
        if (!Account.isThereUserWithUserName(loginUsername.getText())) {
            loginErrorMessage.setText("there is no account with this username !");
            loginUsername.setStyle("-fx-border-color : RED; -fx-text-fill : #6e0113;");
            return false;
        }
        if (!Account.getUserWithUserName(loginUsername.getText()).isPassWordCorrect(loginPassword.getText())) {
            loginPassword.setStyle("-fx-border-color : RED; -fx-text-fill : #6e0113;");
            loginErrorMessage.setText("password is incorrect !");
            return false;
        }
        return true;
    }

    private boolean areSignUpTextFieldsValid() throws Exception {
        if (Account.isThereUserWithUserName(signUpUsername.getText())) {
            signUpErrorMessage.setText("this username is already token !");
            signUpUsername.setStyle("-fx-border-color : RED; -fx-text-fill : #6e0113;");
            return false;
        }
        try {
            AccountsException.validateUserName(signUpUsername.getText());
            try {
                AccountsException.validateUsernameUniqueness(signUpUsername.getText());
            } catch (AccountsException e) {
                signUpUsername.setText(e.getErrorMessage());
                signUpUsername.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
                return false;
            }
        } catch (AccountsException.invalidUserNameException e) {
            signUpUsername.setText(e.getErrorMessage());
            signUpUsername.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            return false;
        }
        /*
        if (!Account.getUserWithUserName(loginUsername.getText()).isPassWordCorrect(loginPassword.getText())) {
            loginErrorMessage.setText("password is incorrect !");
            return false;
        }
        password
         */
        return true;
    }

    public void back(MouseEvent mouseEvent) {
        GraphicMain.graphicMain.back();
        mediaPlayer.stop();
        GraphicMain.audioClip.play();
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        if (areTextFieldsFilled(loginUsername, loginPassword) && areLoginTextFieldsValid()) {
            GeneralController.currentUser = Account.getUserWithUserName(loginUsername.getText());
            mediaPlayer.stop();
            GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
            GraphicMain.audioClip.play();
        }
    }

    public void switchTab(Event event) {
        //TODO sound effect :)
    }

    public void completeSignUp(MouseEvent mouseEvent) throws Exception {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        signUpInputPassword = signUpPassword.getText();
        getSignUpInputUsername = signUpUsername.getText();
        if (areTextFieldsFilled(signUpUsername, signUpPassword) && areSignUpTextFieldsValid()) {
            GraphicMain.graphicMain.goToPage(CompleteSignUpPage.FXML_PATH, CompleteSignUpPage.TITLE);
        }
    }

    public void resetTextFields(MouseEvent mouseEvent) {
        TextField textField = (TextField) mouseEvent.getSource();
        textField.setStyle("-fx-border-color: #230038;-fx-prompt-text-fill : #4d4254;");
        textField.setText("");
    }

    public TextField getSignUpUsername() {
        return signUpUsername;
    }

    public PasswordField getSignUpPassword() {
        return signUpPassword;
    }
}
