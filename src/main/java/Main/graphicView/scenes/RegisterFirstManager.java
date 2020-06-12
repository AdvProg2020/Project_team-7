package Main.graphicView.scenes;

import javafx.fxml.Initializable;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media bgMusic = new Media(Paths.get("src/main/java/Main/graphicView/resources/soundEffects/login-crowd.wav").toUri().toString());
        mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.2);

    }

    public void back(MouseEvent mouseEvent) {
    }

    public void signUp(MouseEvent mouseEvent) {
    }


}
