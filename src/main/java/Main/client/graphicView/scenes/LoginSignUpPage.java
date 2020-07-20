package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.DataRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginSignUpPage implements Initializable {
    private static final String NO_USERNAME_GIVEN = "enter a username!";
    private static final String NO_PASSWORD_GIVEN = "enter a password!";
    public static final String FXML_PATH = "src/main/sceneResources/loginSignUp/loginSignUpPage.fxml";
    public static final String TITLE = "Login/Sign Up";
    //public static MediaPlayer mediaPlayer;
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
        //Media bgMusic = new Media(Paths.get("src/main/java/Main/client/graphicView/resources/soundEffects/login-crowd.wav").toUri().toString());
        //mediaPlayer = new MediaPlayer(bgMusic);
        //mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        //mediaPlayer.setVolume(0.2);
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


//    private boolean areLoginTextFieldsValid() throws Exception {
//        if (!Account.isThereUserWithUserName(loginUsername.getText())) {
//            loginErrorMessage.setText("there is no account with this username !");
//            loginUsername.setStyle("-fx-border-color : RED; -fx-text-fill : #6e0113;");
//            return false;
//        }
//        if (!Account.getUserWithUserName(loginUsername.getText()).isPassWordCorrect(loginPassword.getText())) {
//            loginPassword.setStyle("-fx-border-color : RED; -fx-text-fill : #6e0113;");
//            loginErrorMessage.setText("password is incorrect !");
//            return false;
//        }
//        return true;
//    }

//    private boolean areSignUpTextFieldsValid() throws Exception {
//        if (Account.isThereUserWithUserName(signUpUsername.getText())) {
//            signUpErrorMessage.setText("this username is already token !");
//            signUpUsername.setStyle("-fx-border-color : RED; -fx-text-fill : #6e0113;");
//            return false;
//        }
//        try {
//            AccountsException.validateUserName(signUpUsername.getText());
//            try {
//                AccountsException.validateUsernameUniqueness(signUpUsername.getText());
//            } catch (AccountsException e) {
//                signUpUsername.setText(e.getErrorMessage());
//                signUpUsername.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
//                return false;
//            }
//        } catch (AccountsException.invalidUserNameException e) {
//            signUpUsername.setText(e.getErrorMessage());
//            signUpUsername.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
//            return false;
//        }
//        /*
//        if (!Account.getUserWithUserName(loginUsername.getText()).isPassWordCorrect(loginPassword.getText())) {
//            loginErrorMessage.setText("password is incorrect !");
//            return false;
//        }
//        password
//         */
//        return true;
//    }

    public void back(MouseEvent mouseEvent) {
        GraphicMain.graphicMain.back();
        //mediaPlayer.stop();
        //GraphicMain.audioClip.play();
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        if (areTextFieldsFilled(loginUsername, loginPassword)) {
            String response = GeneralRequestBuilder.buildLoginRequest(loginUsername.getText(), loginPassword.getText());
            if (response.startsWith("success")) {
                GraphicMain.token = response.split("#")[1];
                //mediaPlayer.stop();
                GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
                //GraphicMain.audioClip.play();
            } else {
                showLoginResponseMessage(response);
            }
        }
    }

    private void showLoginResponseMessage(String response) {
        if (response.startsWith("invalidCharacter")) {
            loginErrorMessage.setText(response.split("#")[1]);
            loginUsername.setStyle("-fx-border-color : RED; -fx-text-fill : #6e0113;");
        } else if (response.equals("userNameNotFound")) {
            loginErrorMessage.setText("there is no account with this username !");
            loginUsername.setStyle("-fx-border-color : RED; -fx-text-fill : #6e0113;");
        } else if (response.equals("passwordWrong")) {
            loginPassword.setStyle("-fx-border-color : RED; -fx-text-fill : #6e0113;");
            loginErrorMessage.setText("password is incorrect !");
        } else if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else {
            loginErrorMessage.setText("unknown problem connecting the server ! please try again a few moments later !");
        }
    }

//    public void login(MouseEvent mouseEvent) throws Exception {
//        GraphicMain.buttonSound.stop();
//        GraphicMain.buttonSound.play();
//        if (areTextFieldsFilled(loginUsername, loginPassword) && areLoginTextFieldsValid()) {
//            GeneralRequestBuilder.buildLoginRequest(loginUsername.getText(),loginPassword.getText());
//            //GeneralController.currentUser = Account.getUserWithUserName(loginUsername.getText());
//            mediaPlayer.stop();
//            GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
//            GraphicMain.audioClip.play();
//        }
//    }

    public void completeSignUp(MouseEvent mouseEvent) throws Exception {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        signUpInputPassword = signUpPassword.getText();
        getSignUpInputUsername = signUpUsername.getText();
        if (areTextFieldsFilled(signUpUsername, signUpPassword)) {
            String response = GeneralRequestBuilder.buildSignUpRequest(signUpUsername.getText(), signUpPassword.getText());
            if (response.equals("success")) {
                GraphicMain.graphicMain.goToPage(CompleteSignUpPage.FXML_PATH, CompleteSignUpPage.TITLE);
            } else {
                showSignUpResponseMessage(response);
            }
        }
    }

    private void showSignUpResponseMessage(String response) {
        if (response.equals("duplicateUserName")) {
            signUpUsername.setText("this username is already token");
            signUpUsername.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        } else if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else {
            loginErrorMessage.setText("unknown problem connecting the server ! please try again a few moments later !");
        }
    }

//    public void completeSignUp(MouseEvent mouseEvent) throws Exception {
//        GraphicMain.buttonSound.stop();
//        GraphicMain.buttonSound.play();
//        signUpInputPassword = signUpPassword.getText();
//        getSignUpInputUsername = signUpUsername.getText();
//        if (areTextFieldsFilled(signUpUsername, signUpPassword) && areSignUpTextFieldsValid()) {
//            GraphicMain.graphicMain.goToPage(CompleteSignUpPage.FXML_PATH, CompleteSignUpPage.TITLE);
//        }
//    }

    public void resetTextFields(MouseEvent mouseEvent) {
        TextField textField = (TextField) mouseEvent.getSource();
        textField.setStyle("-fx-border-color: #230038;-fx-prompt-text-fill : #4d4254;");
        textField.setText("");
    }
}
