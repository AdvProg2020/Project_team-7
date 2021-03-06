package Main.client.graphicView.scenes.ManagerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.FinanceSetupPage;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterSupporterController implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/loginSignUp/registerSupporter.fxml";
    public static final String TITLE = "Register Supporter";

    //public static MediaPlayer mediaPlayer;
    public TextField phoneNumber;
    public TextField email;
    public TextField lastName;
    public TextField firstName;
    public PasswordField password;
    public TextField username;
    public Label imageName;
    private static String profileImagePath;

    public static String getProfileImagePath() {
        return profileImagePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Media bgMusic = new Media(Paths.get("src/main/java/Main/client/graphicView/resources/soundEffects/login-crowd.wav").toUri().toString());
        //mediaPlayer = new MediaPlayer(bgMusic);
        //mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        //mediaPlayer.setVolume(0.2);
    }

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

        return isInfoCorrect;
    }

    public void back() {
        GraphicMain.graphicMain.back();
    }

    public void signUp() throws Exception {
        if (areTextFieldsFilled()) {
            String response = GeneralRequestBuilder.buildSupporterSignUpRequest(username.getText(), firstName.getText(), lastName.getText(),
                    email.getText(), phoneNumber.getText(), password.getText(), profileImagePath);

            if (response.startsWith("success")) {
                if (response.split("#")[1].equals("chief")) {
                    GraphicMain.token = response.split("#")[2];
                    GraphicMain.graphicMain.goToPage(FinanceSetupPage.FXML_PATH, MainMenuController.TITLE);
                } else {
                    GraphicMain.graphicMain.goToPage(ManageUsersController.FXML_PATH, ManageUsersController.TITLE);
                }
                //LoginSignUpPage.mediaPlayer.stop();
            } else {
                showRegisterResponseMessage(response);
            }
        }
    }

    private void showRegisterResponseMessage(String response) {
        if (response.startsWith("invalidCharacter")) {
            username.setText(response.split("#")[1]);
            username.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        } else if (response.startsWith("invalidEmail")) {
            email.setText(response.split("#")[1]);
            email.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        } else if (response.startsWith("invalidPhoneNumber")) {
            phoneNumber.setText(response.split("#")[1]);
            phoneNumber.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        } else if (response.equals("duplicateUserName")) {
            username.setText(response.split("#")[1]);
            username.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        } else if (response.equals("loginNeeded")) {
            GraphicMain.showInformationAlert("you must login first !\nyou'r authentication might be expired !");
        } else if (response.equals("tooManyRequests")) {
            username.setText("too many requests sent to server, slow down !!");
            username.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        } else {
            username.setText("unknown problem connecting the server ! please try again a few moments later !");
            username.setStyle("-fx-text-fill : #6e0113; -fx-border-color : RED; ");
        }
    }

    public void resetTextField(MouseEvent mouseEvent) {
        TextField textField = (TextField) mouseEvent.getSource();
        textField.setStyle("-fx-border-color: #230038;-fx-prompt-text-fill : #4d4254;");
        textField.setText("");
    }

    public void chooseImage() {
        List<String> extensions = new ArrayList<>();
        extensions.add("*.png");
        extensions.add("*.jpg");
        extensions.add("*.jpeg");
        FileChooser.ExtensionFilter extChooser = new FileChooser.ExtensionFilter("only images", extensions);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extChooser);
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("src/main/java/Main/client/graphicView/resources/images/avatars"));
        File file = fileChooser.showOpenDialog(GraphicMain.stage);
        if (file.getParent().endsWith("avatars")) {
            if (file != null) {
                imageName.setStyle("-fx-text-fill:green;");
                imageName.setText(file.getName());
                profileImagePath = "src/main/java/Main/client/graphicView/resources/images/avatars" + "/" + file.getName();
            }
        } else {
            profileImagePath = "src/main/java/Main/client/graphicView/resources/images/avatars/1.png";
            imageName.setStyle("-fx-text-fill:red;");
            imageName.setText("choose from opened folder");
        }
    }
}
