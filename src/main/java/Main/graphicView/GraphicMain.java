package Main.graphicView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class GraphicMain extends Application{

    public static Stage stage;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(new File("src/main/java/Main/graphicView/scenes/mainMenu.fxml").toURI().toURL());
        stage.setTitle("MFM SHOP");
        stage.setScene(new Scene(root));
        primaryStage.show();
    }


}
