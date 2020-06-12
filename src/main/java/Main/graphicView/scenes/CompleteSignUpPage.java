package Main.graphicView.scenes;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.exceptions.AccountsException;
import Main.model.requests.CreateSellerAccountRequest;
import Main.model.requests.Request;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompleteSignUpPage implements Initializable {
    /*
    private static final String NO_EMAIL_GIVEN = "enter an email !";
    private static final String NO_PHONE_NUMBER_GIVEN = "enter a phone number !";
    private static final String NO_FIRST_NAME_GIVEN = "enter your first name !";
    private static final String NO_LAST_NAME_GIVEN = "enter your last name !";
    private static final String NO_COMPANY_NAME_GIVEN = "enter your company name !";
    private static final String NO_COMPANY_INFO_GIVEN = "enter a few words about your company !";
     */

    public static final String FXML_PATH = "src/main/sceneResources/loginSignUp/completeSignUpPage.fxml";
    public static final String TITLE = "Complete Sign Up";
    public CheckBox isSeller;
    public Label companyInfoLabel;
    public TextArea companyInfoField;
    public TextField companyNameField;
    public Label companyNameLabel;
    public TextField phoneNumber;
    public TextField email;
    public TextField lastName;
    public TextField firstName;

    public void back(MouseEvent mouseEvent) {
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    private boolean areTextFieldsFilled() {
        boolean isInfoCorrect = true;
        if (email.getText().equals("")) {
            email.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (phoneNumber.getText().equals("")) {
            phoneNumber.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (firstName.getText().equals("")) {
            firstName.setStyle("-fx-border-color :RED;");
            isInfoCorrect = false;
        }
        if (lastName.getText().equals("")) {
            lastName.setStyle("-fx-border-color : RED;");
            isInfoCorrect = false;
        }
        if (isSeller.isSelected()) {
            if (companyInfoField.getText().equals("")) {
                companyInfoField.setStyle("-fx-border-color :RED;");
                isInfoCorrect = false;
            }
            if (companyNameField.getText().equals("")) {
                companyNameField.setStyle("-fx-border-color : RED;");
                isInfoCorrect = false;
            }
        }
        return isInfoCorrect;
    }

    public void signUp(MouseEvent mouseEvent) throws IOException {
        GraphicMain.buttonSound.play();
        if (areTextFieldsFilled() && areTextFieldsValid()) {
            LoginSignUpPage loginSignUpPage = (LoginSignUpPage) GraphicMain.graphicMain.getController(LoginSignUpPage.FXML_PATH);
            if (isSeller.isSelected()) {
                SellerAccount sellerAccount = new SellerAccount(loginSignUpPage.getSignUpUsername().getText(), firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(), loginSignUpPage.getSignUpPassword().getText(), companyNameField.getText(), companyInfoField.getText(), 1000000);
                CreateSellerAccountRequest createSellerAccountRequest = new CreateSellerAccountRequest(sellerAccount, "create seller account");
                Request.addRequest(createSellerAccountRequest);
                Account.getReservedUserNames().add(loginSignUpPage.getSignUpUsername().getText());
            } else {
                BuyerAccount buyerAccount = new BuyerAccount(loginSignUpPage.getSignUpUsername().getText(), firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(), loginSignUpPage.getSignUpPassword().getText(), 1000000);
                BuyerAccount.addBuyer(buyerAccount);
                GeneralController.currentUser = buyerAccount;
            }
            GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
            LoginSignUpPage.mediaPlayer.stop();
        }
    }


    private boolean areTextFieldsValid() throws MalformedURLException {
        boolean isInfoValid = true;
        try {
            AccountsException.validateNameTypeInfo("first name", firstName.getText());
        } catch (AccountsException e) {
            firstName.setText(e.getErrorMessage());
            firstName.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            isInfoValid = false;
        }
        try {
            AccountsException.validateNameTypeInfo("last name", lastName.getText());
        } catch (AccountsException e) {
            lastName.setText(e.getErrorMessage());
            lastName.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            isInfoValid = false;
        }
        try {
            AccountsException.validateEmail(email.getText());
        } catch (AccountsException e) {
            email.setText(e.getErrorMessage());
            email.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            isInfoValid = false;
        }
        try {
            AccountsException.validatePhoneNumber(phoneNumber.getText());
        } catch (AccountsException e) {
            phoneNumber.setText(e.getErrorMessage());
            phoneNumber.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
            isInfoValid = false;
        }
        /*
        try {
            AccountsException.validatePassWord(accountInfo.get(0));
        } catch (AccountsException e) {
            accountCreationErrors.append(e.getErrorMessage());
        }
        */
        return isInfoValid;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isSeller.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    companyNameLabel.setDisable(false);
                    companyNameField.setDisable(false);
                    companyInfoLabel.setDisable(false);
                    companyInfoField.setDisable(false);
                } else {
                    companyNameLabel.setDisable(true);
                    companyNameField.setDisable(true);
                    companyInfoLabel.setDisable(true);
                    companyInfoField.setDisable(true);
                }
            }
        });
    }

    public void resetTextFields(MouseEvent mouseEvent) {
        Object eventSource = mouseEvent.getSource();
        if(eventSource instanceof TextField){
        TextField textField = (TextField) eventSource;
        textField.setStyle("-fx-border-color: #230038;-fx-prompt-text-fill : #4d4254;");
        textField.setText("");}else{
            TextArea textArea = (TextArea) eventSource;
            textArea.setStyle("-fx-border-color: #230038;-fx-prompt-text-fill : #4d4254;");
        }
    }
}