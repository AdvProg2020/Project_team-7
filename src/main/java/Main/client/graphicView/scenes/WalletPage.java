package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.client.requestBuilder.DataRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WalletPage implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/walletPage.fxml";
    public static final String TITLE = "wallet page";
    public Label accountBalance;

    @FXML
    private Label walletBalance;
    @FXML
    private Button withdrawButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String response = DataRequestBuilder.buildWalletBalanceRequest();
        if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
            return;
        } else if (response.equals("failure")) {
            GraphicMain.showInformationAlert("try again !");
            return;
        }
        if (response.equals("loginNeeded")) {
            GraphicMain.showInformationAlert("you must login first !\nyou'r authentication might be expired !");
            return;
        }

        walletBalance.setText(response);

        String userType = DataRequestBuilder.buildUserTypeRequest();
        if (userType.equals("seller")) {
            //walletBalance.setText(SellerRequestBuilder.getSellerBalance());
            String response2 = DataRequestBuilder.buildAccountBalanceRequest();
            if (response2.equals("failure")) {
                GraphicMain.showInformationAlert("try again");
            } else if (response2.equals("tooManyRequests")) {
                GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
            } else if (response2.equals("loginNeeded")) {
                GraphicMain.showInformationAlert("you must login first !\nyou'r authentication might be expired !");
            } else {
                accountBalance.setText(response2);
            }
        } else if (userType.equals("buyer")) {
            //walletBalance.setText(BuyerRequestBuilder.buildInitializeBuyerPanelRequest());
            withdrawButton.setVisible(false);
            String response3 = DataRequestBuilder.buildAccountBalanceRequest();
            if (response3.equals("failure")) {
                GraphicMain.showInformationAlert("try again");
            } else if (response3.equals("tooManyRequests")) {
                GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
            } else if (response3.equals("loginNeeded")) {
                GraphicMain.showInformationAlert("you must login first !\nyou'r authentication might be expired !");
            } else {
                accountBalance.setText(response3);
            }
        } else if (response.equals("failure")) {
            GraphicMain.showInformationAlert("try again !");
        }
    }

    public void goBack() {
        GraphicMain.graphicMain.back();
    }

    public void charge() {
        Stage stage = new Stage();
        stage.setTitle("get bank information");
        Label label = new Label();
        label.setText("enter your bank account username and password.\n" +
                "(They are same with your account username and password)");
        Label errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);
        TextField username = new TextField();
        TextField password = new TextField();
        TextField amount = new TextField();
        username.setPromptText("username");
        password.setPromptText("password");
        amount.setPromptText("amount");
        Button submit = new Button("submit");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, username, password, amount, submit, errorMessage);
        Scene scene = new Scene(vBox, 750, 400);
        stage.setScene(scene);
        stage.show();
        submit.setOnAction(event -> {
            String response = GeneralRequestBuilder.buildChargeWalletRequest(username.getText(), password.getText(), amount.getText());
            if (response.equals("success")) {
                GraphicMain.showInformationAlert("charged successfully !");
                initialize(null,null);
            } else {
                showChargeResponseMessages(response, errorMessage);
            }
        });
    }

    private void showChargeResponseMessages(String response, Label errorMessage) {
        if (response.equals("invalidAmount")) {
            errorMessage.setText("invalid amount ! amount must be a positive double !");
        } else if (response.equals("invalidUsername")) {
            errorMessage.setText("invalid username !");
        } else if (response.equals("invalidPassword")) {
            errorMessage.setText("invalid password !");
        } else if (response.equals("tooMAnyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else {
            errorMessage.setText("problem connecting bank server ! try a few moments later !");
        }
    }

    public void withdraw() {
        Stage stage = new Stage();
        stage.setTitle("get bank information");
        Label label = new Label();
        label.setText("enter your bank account username and password.\n" +
                "(They are same with your account username and password)");
        Label errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);
        TextField username = new TextField();
        TextField password = new TextField();
        TextField amount = new TextField();
        username.setPromptText("username");
        password.setPromptText("password");
        amount.setPromptText("amount");
        Button submit = new Button("submit");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, username, password, amount, submit, errorMessage);
        Scene scene = new Scene(vBox, 750, 400);
        stage.setScene(scene);
        stage.show();
        submit.setOnAction(event -> {
            String response = GeneralRequestBuilder.buildWithdrawFromWalletRequest(username.getText(), password.getText(), amount.getText());
            if (response.equals("success")) {
                GraphicMain.showInformationAlert("withdraw successful !");
                initialize(null,null);
            } else {
                showWithdrawResponseMessages(response, errorMessage);
            }
        });
    }

    private void showWithdrawResponseMessages(String response, Label errorMessage) {
        if (response.equals("invalidAmount")) {
            errorMessage.setText("invalid amount ! amount must be a positive double !");
        } else if (response.equals("invalidUsername")) {
            errorMessage.setText("invalid username !");
        } else if (response.equals("invalidPassword")) {
            errorMessage.setText("invalid password !");
        } else if (response.equals("tooMAnyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else if (response.equals("insufficientBalance")) {
            errorMessage.setText("insufficient wallet balance !");
        } else {
            errorMessage.setText("problem connecting bank server ! try a few moments later !");
        }
    }
}
