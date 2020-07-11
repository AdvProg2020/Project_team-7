package Main.graphicView;

import Main.server.controller.BuyerController;
import Main.server.controller.GeneralController;
import Main.server.controller.ManagerController;
import Main.server.controller.SellerController;
import Main.graphicView.scenes.*;
import Main.server.model.accounts.ManagerAccount;
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
    public static ArrayList<String> sceneTrace = new ArrayList<>();
    public static Stage stage;
    public static GraphicMain graphicMain = new GraphicMain();
    public static BuyerController buyerController = new BuyerController();
    public static ManagerController managerController = new ManagerController();
    public static GeneralController generalController = new GeneralController();
    public static SellerController sellerController = new SellerController();
    public static AudioClip audioClip;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        audioClip = new AudioClip(new File("src/main/java/Main/graphicView/resources/soundEffects/backgroundMusic.wav").toURI().toString());
        audioClip.setCycleCount(AudioClip.INDEFINITE);

        System.out.println(GeneralController.readDataAndSetStringRecordObjects());
        generalController.initializeIDs();
        generalController.giveDiscountCodeToSpecialBuyers();

        stage = primaryStage;
        stage.setOnCloseRequest(e -> exitProgram());
        Parent root;

        if (!ManagerAccount.isThereAChiefManager()) {
            root = FXMLLoader.load(new File((RegisterManager.FXML_PATH)).toURI().toURL());

            FXMLLoader loginPageLoader = new FXMLLoader(new File(LoginSignUpPage.FXML_PATH).toURI().toURL());
            loginPageLoader.load();
            LoginSignUpPage.mediaPlayer.play();

            stage.setTitle(RegisterManager.TITLE);
            sceneTrace.add(RegisterManager.FXML_PATH);
        } else {
            root = FXMLLoader.load(new File(MainMenuController.FXML_PATH).toURI().toURL());

            audioClip.play();

            stage.setTitle(MainMenuController.TITLE);
            sceneTrace.add(MainMenuController.FXML_PATH);
        }

        titleTrace.add(stage.getTitle());

        Scene scene = new Scene(root);
        stage.setScene(scene);
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
            try {
                goToPage(sceneTrace.get(sceneTrace.size() - 1), titleTrace.get(titleTrace.size() - 1));
            } catch (IOException e) {
            }
        } else {
            exitProgram();
        }
    }

    public Parent goToPage(String fxmlPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(fxmlPath).toURI().toURL());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        if (!fxmlPath.equals(sceneTrace.get(sceneTrace.size() - 1))) {
            sceneTrace.add(fxmlPath);
            titleTrace.add(title);
        }
        GraphicMain.stage.setTitle(title);
        GraphicMain.stage.setScene(scene);
        return root;
    }

    public Object getController(String fxmlName) throws MalformedURLException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(fxmlName).toURI().toURL());
        return fxmlLoader.getController();
    }
}
