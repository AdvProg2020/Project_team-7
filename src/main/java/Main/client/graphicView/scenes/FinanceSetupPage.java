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
    public TextField walletMinimumBalance;
    public TextField commission;
    public PasswordField password;
    public TextField username;

    private boolean areTextFieldsFilled() {
        boolean isInfoCorrect = true;
        if (username.getText().equals("")) {
            username.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (password.getText().equals("")) {
            password.setStyle("-fx-border-color :RED;");
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
            String response = GeneralRequestBuilder.buildSetUpFinanceRequest(username.getText(), password.getText(), walletMinimumBalance.getText(), commission.getText());
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
        } else if (response.equals("invalidUserName")) {
            username.setText("\"Invalid character ! user name  can only contain English letters, numbers\" +\n" +
                    "                    \", '_','.' and '-' . make sure that it doesn't contain spaces !\\n\"");
            username.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        } else if (response.equals("invalidPassword")) {
            password.setText("PassWord must be at least 8 valid characters\nalso, it can only contain English letters, numbers" +
                    ", '_','.' and '-' . make sure that it doesn't contain spaces !\n");
            password.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        }else if (response.equals("invalidWalletBalance")) {
            walletMinimumBalance.setText("invalid balance! balance must be of double type !");
            walletMinimumBalance.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        }else if (response.equals("invalidCommission")) {
            commission.setText("invalid commission!commission must be of double type and less than 100!");
            commission.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        }else if (response.equals("duplicateUsername")) {
            username.setText("this username is token");
            username.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        } else if (response.equals("loginNeeded")||response.equals("notChiefManager")) {
            GraphicMain.graphicMain.showInformationAlert("you must login as the chief manager!");
        }else {
            username.setText("problem connecting bank server");
            username.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        }
    }

    public void resetTextField(MouseEvent mouseEvent) {
        TextField textField = (TextField) mouseEvent.getSource();
        textField.setStyle("-fx-border-color: #230038;-fx-prompt-text-fill : #4d4254;");
        textField.setText("");
    }
}
