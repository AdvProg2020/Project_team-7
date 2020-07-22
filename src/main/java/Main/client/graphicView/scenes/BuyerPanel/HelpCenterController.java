package Main.client.graphicView.scenes.BuyerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.ChatPageController;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.ManagerRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class HelpCenterController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/HelpCenter.fxml";
    public static final String TITLE = "Help Center";

    @FXML
    private ListView supportersList;

    public void initialize() {
        supportersList.getItems().clear();
        String userData = ManagerRequestBuilder.buildInitializeHelpCenterRequest();
        if (!userData.equals(""))
            supportersList.getItems().addAll(userData.split("#"));
        supportersList.setOnMouseClicked(mouseEvent -> {
            if (supportersList.getSelectionModel().getSelectedItem() != null) {
                String userInfo = supportersList.getSelectionModel().getSelectedItem().toString();
                String userName = userInfo.substring(userInfo.indexOf("@") + 1);
                supportersList.getSelectionModel().clearSelection();
                userName = userName.substring(0, userName.indexOf("\n"));
                if (userInfo.endsWith("(offline)"))
                    ManagerPanelController.alertError("You can only chat with online supporters!");
                else {
                    try {
                        openChatPage(GraphicMain.token, userName);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        ManagerPanelController.alertError(e.getMessage());
                    }
                }
            }
        });
    }

    public void openChatPage(String myToken, String theirUsername) throws IOException {
        FXMLLoader fxmlLoader = GraphicMain.graphicMain.goToPageReturnLoader(ChatPageController.FXML_PATH, ChatPageController.TITLE);
        ChatPageController chatPageController = fxmlLoader.getController();
        chatPageController.setPeople(myToken, theirUsername, this, null);
        //GeneralRequestBuilder.buildSaveChatMessages(theirUsername,new ArrayList<>());
        try {
            chatPageController.setMessages(GeneralRequestBuilder.buildSetChatMessagesRequest(theirUsername));
        } catch (Exception e) {
            e.printStackTrace();
            ManagerPanelController.alertError(e.getMessage());
        }
        chatPageController.initialize();
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
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }
}
