package Main.graphicView;

import Main.graphicView.scenes.LoginSignUpPage;
import Main.graphicView.scenes.MainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GraphicMain extends Application {

    public static MediaPlayer buttonSound = new MediaPlayer(new Media(Paths.get("src/main/java/Main/graphicView/resources/soundEffects/buttonSound.wav").toUri().toString()));
    private static ArrayList<Scene> sceneTrace = new ArrayList<>();
    private static Stage stage;
    public static GraphicMain graphicMain = new GraphicMain();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(new File(LoginSignUpPage.FXML_PATH).toURI().toURL());
        Scene scene = new Scene(root);
        sceneTrace.add(scene);
        stage.setTitle(LoginSignUpPage.TITLE);
        stage.setScene(scene);
        LoginSignUpPage.mediaPlayer.play();
        primaryStage.show();
    }

    public void back() {
        if (sceneTrace.size() != 1) {
            sceneTrace.remove(sceneTrace.size() - 1);
            stage.setScene(sceneTrace.get(sceneTrace.size() - 1));
        }
    }

    public void goToPage(String fxmlPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(fxmlPath).toURI().toURL());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        sceneTrace.add(scene);
        stage.setTitle(title);
        stage.setScene(scene);
    }

    public Object getController(String fxmlName) throws MalformedURLException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(fxmlName).toURI().toURL());
        return fxmlLoader.getController();
    }
}
