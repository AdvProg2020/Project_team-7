package Main.client.graphicView;

import Main.client.ClientMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.RegisterManager;
import Main.client.graphicView.scenes.WalletPage;
import Main.client.requestBuilder.DataRequestBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class GraphicMain extends Application {

    //public static MediaPlayer buttonSound ;
    //public static AudioClip audioClip;
    public static ArrayList<String> titleTrace = new ArrayList<>();
    public static ArrayList<String> sceneTrace = new ArrayList<>();
    public static Stage stage;
    public static GraphicMain graphicMain = new GraphicMain();
    //    public static BuyerController buyerController = new BuyerController();
//    public static ManagerController managerController = new ManagerController();
//    public static GeneralController generalController = new GeneralController();
//    public static SellerController sellerController = new SellerController();
    public static String token = "0000";
    public static String currentProductId = "0000";
    public static String currentAuctionId = "0000";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //buttonSound = new MediaPlayer(new Media(Paths.get("src/main/java/Main/client/graphicView/resources/soundEffects/buttonSound.wav").toUri().toString()));
        //audioClip = new AudioClip(new File("src/main/java/Main/client/graphicView/resources/soundEffects/backgroundMusic.wav").toURI().toString());
        //audioClip.setCycleCount(AudioClip.INDEFINITE);

//        System.out.println(GeneralController.readDataAndSetStringRecordObjects());
//        generalController.initializeIDs();
//        generalController.giveDiscountCodeToSpecialBuyers();

        stage = primaryStage;
        stage.setOnCloseRequest(e -> exitProgram());
        Parent root;

//        if (!ManagerAccount.isThereAChiefManager()) {
//            root = FXMLLoader.load(new File((RegisterManager.FXML_PATH)).toURI().toURL());
//
//            FXMLLoader loginPageLoader = new FXMLLoader(new File(LoginSignUpPage.FXML_PATH).toURI().toURL());
//            loginPageLoader.load();
//            //LoginSignUpPage.mediaPlayer.play();
//
//            stage.setTitle(RegisterManager.TITLE);
//            sceneTrace.add(RegisterManager.FXML_PATH);
//        } else {


        //this scope
        String response = DataRequestBuilder.buildProgramStartModeRequest();
        if (response.equals("1")) {
            root = FXMLLoader.load(new File(RegisterManager.FXML_PATH).toURI().toURL());
            stage.setTitle(RegisterManager.TITLE);
            sceneTrace.add(RegisterManager.FXML_PATH);
        } else {
            root = FXMLLoader.load(new File(MainMenuController.FXML_PATH).toURI().toURL());
            stage.setTitle(MainMenuController.TITLE);
            sceneTrace.add(MainMenuController.FXML_PATH);
        }


//        root = FXMLLoader.load(new File(ChatPageController.FXML_PATH).toURI().toURL());
//        stage.setTitle(RegisterManager.TITLE);
//        sceneTrace.add(RegisterManager.FXML_PATH);

        //audioClip.play();
        // }

        titleTrace.add(stage.getTitle());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        primaryStage.show();
    }

    public void exitProgram() {
//        System.out.println(GeneralController.writeDataAndGetObjectStringRecords());
//        System.out.println(GeneralController.readDataAndSetStringRecordObjects());
        ClientMain.client.closeConnection();
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

    public FXMLLoader goToPageReturnLoader(String fxmlPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(fxmlPath).toURI().toURL());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        if (!fxmlPath.equals(sceneTrace.get(sceneTrace.size() - 1))) {
            sceneTrace.add(fxmlPath);
            titleTrace.add(title);
        }
        GraphicMain.stage.setTitle(title);
        GraphicMain.stage.setScene(scene);
        return fxmlLoader;
    }

    public Object getController(String fxmlName) throws MalformedURLException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(fxmlName).toURI().toURL());
        return fxmlLoader.getController();
    }

    public static void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
