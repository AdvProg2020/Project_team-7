package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.client.requestBuilder.DataRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WalletPage implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/walletPage.fxml";
    public static final String TITLE = "wallet page";

    @FXML
    private Label walletBalance;
    @FXML
    private Button withdrawButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String userType = DataRequestBuilder.buildUserTypeRequest();
        if(userType.equals("seller")){
            walletBalance.setText(SellerRequestBuilder.getSellerBalance());
        }else if(userType.equals("buyer")){
            walletBalance.setText(BuyerRequestBuilder.buildInitializeBuyerPanelRequest());
            withdrawButton.setVisible(false);
        }
    }

    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    public void charge(){
        Stage stage = new Stage();
        stage.setTitle("get bank information");
        Label label = new Label();
        label.setText("enter your bank account username and password.\n" +
                "(They are same with your account username and password)");
        TextField username = new TextField();
        TextField password = new TextField();
        username.setPromptText("username");
        password.setPromptText("password");
        Button submit = new Button("submit");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, username, password, submit);
        Scene scene = new Scene(vBox, 750, 400);
        stage.setScene(scene);
        stage.show();
        submit.setOnAction(event -> {
            //TODO complete purchase process
        });
    }

    public void withdraw(){
        Stage stage = new Stage();
        stage.setTitle("get bank information");
        Label label = new Label();
        label.setText("enter your bank account username and password.\n" +
                "(They are same with your account username and password)");
        TextField username = new TextField();
        TextField password = new TextField();
        username.setPromptText("username");
        password.setPromptText("password");
        Button submit = new Button("submit");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, username, password, submit);
        Scene scene = new Scene(vBox, 750, 400);
        stage.setScene(scene);
        stage.show();
        submit.setOnAction(event -> {
            //TODO complete purchase process
        });
    }
}
