package Main.client.graphicView.scenes.ManagerPanel;

import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.RegisterManager;
import Main.server.model.accounts.Account;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class ManageUsersController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageUsers.fxml";
    public static final String TITLE = "Manage Users";

    @FXML
    private ListView usersList;

    public void initialize() {
        usersList.getItems().clear();
        usersList.getItems().addAll(GraphicMain.managerController.usersListForGraphic());
        usersList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (usersList.getSelectionModel().getSelectedItem() != null) {
                    String userInfo = usersList.getSelectionModel().getSelectedItem().toString();
                    String userName = userInfo.substring(userInfo.indexOf("@") + 1);
                    usersList.getSelectionModel().clearSelection();
                    userName = userName.substring(0, userName.indexOf("\n"));
                    Account account = null;
                    try {
                        account = Account.getUserWithUserName(userName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showUserInfoOrDelete(account, userName);
                }
            }
        });
    }

    private void showUserInfoOrDelete(Account account, String userName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("USER INFO");
        alert.setHeaderText(account.viewMe());
        alert.setContentText("Do you want to delete this user?");
        Optional<ButtonType> option = alert.showAndWait();
        if (ButtonType.OK.equals(option.get())) {
            deleteUser(userName);
        }
    }

    public void logout() throws IOException{
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    private void cannotDelete() {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("cannot delete user");
        alert1.setHeaderText(null);
        alert1.setContentText("You can not delete yourself.");
        alert1.showAndWait();
    }

    private void deleteUser(String userName) {
        try {
            if (Account.getUserWithUserName(userName).equals(GeneralController.currentUser)) {
                cannotDelete();
            } else {
                GraphicMain.managerController.deleteUserWithUserName(userName);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("USER DELETED");
                alert1.setHeaderText(null);
                alert1.setContentText("User " + userName + " was deleted successfully.");
                alert1.showAndWait();
            }
            initialize();
        } catch (Exception e) {
            ManagerPanelController.alertError(e.getMessage());
        }
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void goToRegisterManager() throws IOException {
        GraphicMain.graphicMain.goToPage(RegisterManager.FXML_PATH, RegisterManager.TITLE);
    }
}
