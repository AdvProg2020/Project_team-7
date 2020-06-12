package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class CompleteSignUpPage implements Initializable {
    private static final String NO_EMAIL_GIVEN = "enter an email !";
    private static final String NO_PHONE_NUMBER_GIVEN = "enter a phone number !";
    private static final String NO_FIRST_NAME_GIVEN = "enter your first name !";
    private static final String NO_LAST_NAME_GIVEN = "enter your last name !";
    private static final String NO_COMPANY_NAME_GIVEN = "enter your company name !";
    private static final String NO_COMPANY_INFO_GIVEN = "enter a few words about your company !";

    public static final String FXML_PATH = "src/main/sceneResources/loginSignUp/completeSignUpPage.fxml";
    public static final String TITLE = "Complete Sign Up";
    public CheckBox isSeller;
    public Label companyInfoLabel;
    public TextArea companyInfoField;
    public TextField companyNameField;
    public Label companyNameLabel;

    public void back(MouseEvent mouseEvent) {
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void signUp(MouseEvent mouseEvent) {
        GraphicMain.buttonSound.play();
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
}