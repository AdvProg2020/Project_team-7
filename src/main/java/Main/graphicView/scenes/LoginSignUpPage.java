package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import javax.xml.soap.Text;
import java.io.IOException;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media bgMusic = new Media(Paths.get("src/main/java/Main/graphicView/resources/soundEffects/login-crowd.wav").toUri().toString());
        mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.2);
    }

    private boolean areLoginTextFieldsFilled(TextField userName, TextField passWord) {
        boolean isInfoCorrect = true;
        if (userName.getText().equals("")) {
            userName.setText(NO_USERNAME_GIVEN);
            userName.setStyle("-fx-text-fill : RED;-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (passWord.getText().equals("")) {
            passWord.setText(NO_PASSWORD_GIVEN);
            passWord.setStyle("-fx-text-fill : RED;-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        return isInfoCorrect;
    }


    public void login(MouseEvent mouseEvent) {
        GraphicMain.buttonSound.play();
        areLoginTextFieldsFilled(loginUsername, loginPassword);
    }

    public void switchTab(Event event) {

    }

    public void completeSignUp(MouseEvent mouseEvent) throws IOException {
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(CompleteSignUpPage.FXML_PATH, CompleteSignUpPage.TITLE);
    }

    public void resetTextFields(MouseEvent mouseEvent) {
        Object eventResource = mouseEvent.getSource();
        if (eventResource instanceof PasswordField) {
            PasswordField passwordField = (PasswordField) eventResource;
            passwordField.setText("");
            passwordField.setStyle("-fx-text-fill : #4d4254; -fx-border-color : #ff9500;");
        } else {
            TextField textField = (TextField) eventResource;
            textField.setText("");
            textField.setStyle("-fx-text-fill : #4d4254; -fx-border-color : #ff9500;");
        }
    }
}
