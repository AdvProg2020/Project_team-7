package Main.client.graphicView.scenes.BuyerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.ManagerRequestBuilder;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class PurchaseController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/PurchasePanel.fxml";
    public static final String TITLE = "Purchase and Payment";

    @FXML
    private TextField receiverFirstName;
    @FXML
    private TextField receiverLastName;
    @FXML
    private TextField receiverEmail;
    @FXML
    private TextField receiverAddress;
    @FXML
    private TextField discountCode;
    @FXML
    private Label enterCodeLabel;
    @FXML
    private Label ifYouHave;
    @FXML
    private Button acceptCode;
    @FXML
    private Button payment;
    @FXML
    private Button continueButton;
    @FXML
    private TextArea purchaseInfo;
    @FXML
    private Button walletPay;
    @FXML
    private Button bankPay;
    @FXML
    private Button no;
    @FXML
    private Label firstnameLabel;
    @FXML
    private Label lastnameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label pleaseLabel;


    public void initialize() {
        receiverFirstName.setFocusTraversable(false);
        receiverLastName.setFocusTraversable(false);
        receiverEmail.setFocusTraversable(false);
        receiverAddress.setFocusTraversable(false);
        continueButton.setFocusTraversable(false);
        enterCodeLabel.setVisible(false);
        ifYouHave.setVisible(false);
        acceptCode.setVisible(false);
        discountCode.setVisible(false);
        payment.setVisible(false);
        purchaseInfo.setVisible(false);
        walletPay.setVisible(false);
        bankPay.setVisible(false);
        no.setVisible(false);
        receiverFirstName.setVisible(true);
        receiverLastName.setVisible(true);
        receiverEmail.setVisible(true);
        receiverAddress.setVisible(true);
        addressLabel.setVisible(true);
        lastnameLabel.setVisible(true);
        firstnameLabel.setVisible(true);
        emailLabel.setVisible(true);
        pleaseLabel.setVisible(true);
        continueButton.setVisible(true);
        receiverFirstName.setDisable(false);
        receiverLastName.setDisable(false);
        receiverEmail.setDisable(false);
        receiverAddress.setDisable(false);
        continueButton.setDisable(false);
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    public void showDiscountGetter() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        if (receiverFirstName.getText().isEmpty())
            receiverFirstName.setStyle("-fx-border-color:red; -fx-border-width: 3;");
        else
            receiverFirstName.setStyle("-fx-border-width: 0;");
        if (receiverLastName.getText().isEmpty())
            receiverLastName.setStyle("-fx-border-color:red; -fx-border-width: 3;");
        else
            receiverLastName.setStyle("-fx-border-width: 0;");
        if (receiverEmail.getText().isEmpty())
            receiverEmail.setStyle("-fx-border-color:red; -fx-border-width: 3;");
        else
            receiverEmail.setStyle("-fx-border-width: 0;");
        if (receiverAddress.getText().isEmpty())
            receiverAddress.setStyle("-fx-border-color:red; -fx-border-width: 3;");
        else
            receiverAddress.setStyle("-fx-border-width: 0;");
        if (!receiverFirstName.getText().isEmpty() && !receiverLastName.getText().isEmpty() && !receiverEmail.getText().isEmpty() && !receiverAddress.getText().isEmpty()) {
            //GraphicMain.buyerController.setReceiverInformation(getReceiverInformation());
            BuyerRequestBuilder.buildSetReceiverInformationRequest(getReceiverInformation());
            receiverFirstName.setDisable(true);
            receiverLastName.setDisable(true);
            receiverEmail.setDisable(true);
            receiverAddress.setDisable(true);
            continueButton.setDisable(true);
            discountCode.setVisible(true);
            enterCodeLabel.setVisible(true);
            acceptCode.setVisible(true);
            ifYouHave.setVisible(true);
        }
    }

    public void acceptCode() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        if (!discountCode.getText().isEmpty()) {
            try {
                //GraphicMain.buyerController.setPurchaseDiscountCode(discountCode.getText());
                String result = BuyerRequestBuilder.buildSetPurchaseDiscountRequest(discountCode.getText());
                if (!result.equals("set code successfully"))
                    ManagerPanelController.alertError(result);
                discountCode.setStyle("-fx-border-color:green; -fx-border-width: 5;");
                payment.setVisible(true);
            } catch (Exception e) {
                ManagerPanelController.alertError(e.getMessage());
            }
        } else {
            payment.setVisible(true);
        }
    }

    public void payment() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        pleaseLabel.setVisible(false);
        firstnameLabel.setVisible(false);
        lastnameLabel.setVisible(false);
        emailLabel.setVisible(false);
        addressLabel.setVisible(false);
        receiverFirstName.setVisible(false);
        receiverLastName.setVisible(false);
        receiverEmail.setVisible(false);
        receiverAddress.setVisible(false);
        continueButton.setVisible(false);
        enterCodeLabel.setVisible(false);
        ifYouHave.setVisible(false);
        discountCode.setVisible(false);
        payment.setVisible(false);
        acceptCode.setVisible(false);
        walletPay.setVisible(true);
        bankPay.setVisible(true);
        no.setVisible(true);
        purchaseInfo.setVisible(true);
        purchaseInfo.setId("purchaseInfo");
        purchaseInfo.setEditable(false);
        purchaseInfo.setWrapText(true);
        //purchaseInfo.setText(GraphicMain.buyerController.showPurchaseInfo() + "\nDo you want to finalize your purchase?");
        purchaseInfo.setText(BuyerRequestBuilder.buildShowPurchaseInfoRequest() + "\nDo you want to finalize your purchase?");
    }

    public String getReceiverInformation() {
        return receiverFirstName.getText() + "\n\t" +
                receiverLastName.getText() + "\n\t" +
                receiverEmail.getText() + "\n\t" +
                receiverAddress.getText() + "\n\t";
    }

//    public void finalizePayment() {
//        //GraphicMain.buttonSound.stop();
//        //GraphicMain.buttonSound.play();
//        //String result = GraphicMain.buyerController.finalizePurchaseAndPay();
//        String result = BuyerRequestBuilder.buildFinalizePaymentRequestProcessor();
//        if (result.equals("Purchase finished successfully.")) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Successful");
//            alert.setHeaderText("Your purchase completed successfully.");
//            alert.setContentText("Thank you!");
//            alert.showAndWait();
//            goBack();
//            goBack();
//        } else {
//            ManagerPanelController.alertError(result);
//            initialize();
//        }
//    }

    public void finalizeWalletPayment(){
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
            String result = BuyerRequestBuilder.buildFinalizeWalletPaymentRequestProcessor();
            if (result.startsWith("Purchase finished successfully.")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Your purchase completed successfully.");
                alert.setContentText("Thank you!");
                alert.showAndWait();
                if (result.contains("File detected"))
                    downloadFiles(result);
                goBack();
                goBack();
            } else {
                ManagerPanelController.alertError(result);
                initialize();
            }
        });
    }

    public void finalizeBankPayment(){
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
            String result = BuyerRequestBuilder.buildFinalizeBankPaymentRequestProcessor();
            if (result.startsWith("Purchase finished successfully.")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Your purchase completed successfully.");
                alert.setContentText("Thank you!");
                alert.showAndWait();
                if (result.contains("File detected"))
                    downloadFiles(result);
                goBack();
                goBack();
            } else {
                ManagerPanelController.alertError(result);
                initialize();
            }
        });
    }

    private void downloadFiles(String result) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("FILES DETECTED");
        alert.setHeaderText(result);
        alert.setContentText("Do you want to download files?");
        Optional<ButtonType> option = alert.showAndWait();
        if (ButtonType.OK.equals(option.get())) {
            result = result.replaceAll("File detected: ", "");
            result = result.replace("Purchase finished successfully.", "");
            String[] names = result.split("\n");
            ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(names));
            ManagerPanelController.alertInfo(BuyerRequestBuilder.buildDownloadFileRequest(fileNames));
        }
    }
}
