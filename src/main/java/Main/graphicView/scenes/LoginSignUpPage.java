package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LoginSignUpPage implements Initializable {
    public static final String FXML_PATH = "src/main/java/Main/graphicView/scenes/loginSignUpPage.fxml";
    public static final String TITLE = "Login/Sign Up";
    public static MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media bgMusic  =new Media(Paths.get("src/main/java/Main/graphicView/resources/soundEffects/login-crowd.wav").toUri().toString());
        mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.2);
    }


    public void login(MouseEvent mouseEvent) {
        GraphicMain.buttonSound.play();
    }

    public void switchTab(Event event) {

    }

    public void completeSignUp(MouseEvent mouseEvent) throws IOException {
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(CompleteSignUpPage.FXML_PATH,CompleteSignUpPage.TITLE);
    }
}
