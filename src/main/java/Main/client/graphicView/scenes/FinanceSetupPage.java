package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FinanceSetupPage {
    public static final String FXML_PATH = "src/main/sceneResources/loginSignUp/financeSetupPage.fxml";
    public static final String TITLE = "Finance Setup";
    public TextField bankPort;
    public PasswordField bankIP;
    public TextField walletMinimumBalance;
    public TextField commission;

    private boolean areTextFieldsFilled() {
        boolean isInfoCorrect = true;
        if (bankPort.getText().equals("")) {
            bankPort.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (bankIP.getText().equals("")) {
            bankIP.setStyle("-fx-border-color :RED;");
            isInfoCorrect = false;
        }
        if (walletMinimumBalance.getText().equals("")) {
            walletMinimumBalance.setStyle("-fx-border-color :RED;");
            isInfoCorrect = false;
        }
        if (commission.getText().equals("")) {
            commission.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }

        return isInfoCorrect;
    }

    public void submitFinance(MouseEvent mouseEvent) {
        if (areTextFieldsFilled()) {
            String response = GeneralRequestBuilder.buildSetUpFinanceRequest(bankPort.getText(), bankIP.getText(), walletMinimumBalance.getText(), commission.getText());
            if (response.equals("success")) {
                try {
                    GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showResponseMessage(response);
            }
        }
    }

    private void showResponseMessage(String response) {
        if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else if (response.equals("invalidPort")) {
            bankPort.setText("invalid port number");
            bankPort.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        }else if (response.equals("invalidWalletBalance")) {
            walletMinimumBalance.setText("invalid balance! balance must be of double type !");
            walletMinimumBalance.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        }else if (response.equals("invalidCommission")) {
            commission.setText("invalid commission!commission must be of double type and less than 100!");
            commission.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        } else if (response.equals("loginNeeded")||response.equals("notChiefManager")) {
            GraphicMain.graphicMain.showInformationAlert("you must login as the chief manager!");
        }else {
            bankPort.setText("invalid Port or IP");
            bankPort.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        }
    }

    public void resetTextField(MouseEvent mouseEvent) {
        TextField textField = (TextField) mouseEvent.getSource();
        textField.setStyle("-fx-border-color: #230038;-fx-prompt-text-fill : #4d4254;");
        textField.setText("");
    }
}
