package Main.graphicView;

import Main.controller.BuyerController;
import Main.controller.GeneralController;
import Main.controller.ManagerController;
import Main.controller.SellerController;
import Main.graphicView.scenes.LoginSignUpPage;
import Main.graphicView.scenes.MainMenuController;
import Main.graphicView.scenes.ProductsPage;
import Main.graphicView.scenes.RegisterManager;
import Main.model.accounts.ManagerAccount;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
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
    public static ArrayList<String> titleTrace = new ArrayList<>();
    public static ArrayList<Scene> sceneTrace = new ArrayList<>();
    public static Stage stage;
    public static GraphicMain graphicMain = new GraphicMain();
    public static BuyerController buyerController = new BuyerController();
    public static ManagerController managerController = new ManagerController();
    public static GeneralController generalController = new GeneralController();
    public static SellerController sellerController = new SellerController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AudioClip audioClip = new AudioClip(new File("src/main/java/Main/graphicView/resources/soundEffects/backgroundMusic.mp3").toURI().toString());
        audioClip.setCycleCount(AudioClip.INDEFINITE);
        audioClip.play();
        System.out.println(GeneralController.readDataAndSetStringRecordObjects());
        generalController.initializeIDs();
        generalController.giveDiscountCodeToSpecialBuyers();
        stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(new File(LoginSignUpPage.FXML_PATH).toURI().toURL());
        //FXMLLoader fxmlLoader = new FXMLLoader(new File(MainMenuController.FXML_PATH).toURI().toURL());
        Parent root = fxmlLoader.load();
        stage.setTitle(LoginSignUpPage.TITLE);
        if (!ManagerAccount.isThereAChiefManager()) {
            root = FXMLLoader.load(new File((RegisterManager.FXML_PATH)).toURI().toURL());
            stage.setTitle(RegisterManager.TITLE);
        }
        Scene scene = new Scene(root);
        sceneTrace.add(scene);
        titleTrace.add(stage.getTitle());
        stage.setScene(scene);
        LoginSignUpPage.mediaPlayer.play();
        primaryStage.show();
    }

    public void exitProgram() {
        System.out.println(GeneralController.writeDataAndGetObjectStringRecords());
        System.exit(123);
    }

    public void back() {
        if (sceneTrace.size() != 1) {
            sceneTrace.remove(sceneTrace.size() - 1);
            titleTrace.remove(titleTrace.size() - 1);
            stage.setScene(sceneTrace.get(sceneTrace.size() - 1));
            stage.setTitle(titleTrace.get(titleTrace.size() - 1));
        } else {
            exitProgram();
        }
    }

    public Parent goToPage(String fxmlPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(fxmlPath).toURI().toURL());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        sceneTrace.add(scene);
        titleTrace.add(stage.getTitle());
        GraphicMain.stage.setTitle(title);
        GraphicMain.stage.setScene(scene);
        return root;
    }

    public Object getController(String fxmlName) throws MalformedURLException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(fxmlName).toURI().toURL());
        return fxmlLoader.getController();
    }
}
