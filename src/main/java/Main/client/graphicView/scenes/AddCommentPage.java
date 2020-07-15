package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.SellerRequestBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddCommentPage {
    public static final String FXML_PATH = "src/main/sceneResources/addCommentPage.fxml";
    public static final String TITLE = "Add Comment";

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
//        try {
//            GraphicMain.generalController.addComment(commentTitle,commentContent);
//            showInformationAlert("comment added successfully");
//        } catch (Exception e) {
//            showErrorAlert(e.getMessage());
//        }
        String response = SellerRequestBuilder.buildCommentRequest(commentTitle, commentContent);
        if(response.equals("success")){
            showInformationAlert("comment added successfully");
        }else{
            String[] splitResponse = response.split("#");
            showErrorAlert(splitResponse[1]);
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

    public void logout() throws IOException {
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }
}
