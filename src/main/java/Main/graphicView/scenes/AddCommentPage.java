package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AddCommentPage {
    public static final String FXML_PATH = "src/main/sceneResources/addCommentPage.fxml";
    public static final String TITLE = "Seller Panel";

    @FXML
    private TextField title;
    @FXML
    private TextField content;

    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    public void submitComment(){
        String commentTitle = title.getText();
        String commentContent = content.getText();
        try {
            GraphicMain.generalController.addComment(commentTitle,commentContent);
            showInformationAlert("comment added successfully");
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }
    public void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }

    public void showInformationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
