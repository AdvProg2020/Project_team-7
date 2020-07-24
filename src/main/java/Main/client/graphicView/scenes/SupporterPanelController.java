package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class SupporterPanelController {
    public static final String FXML_PATH = "src/main/sceneResources/SupporterPanel.fxml";
    public static final String TITLE = "Supporter Panel Page";

    @FXML
    private ListView chatList;


    public void initialize(){
        chatList.getItems().clear();
        String userData = GeneralRequestBuilder.buildInitializeSupporterPanelRequest();
        if (!userData.equals(""))
            chatList.getItems().addAll(userData.split("#"));
        chatList.setOnMouseClicked(mouseEvent -> {
            if (chatList.getSelectionModel().getSelectedItem() != null) {
                String userInfo = chatList.getSelectionModel().getSelectedItem().toString();
                String userName = userInfo.substring(userInfo.indexOf("@") + 1);
                chatList.getSelectionModel().clearSelection();
                userName = userName.substring(0, userName.indexOf("\n"));
                if (userInfo.endsWith("(offline)"))
                    ManagerPanelController.alertError("This buyer is offline now!");
                else {
                    try {
                        openChatPage(GraphicMain.token,userName);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        ManagerPanelController.alertError(e.getMessage());
                    }
                }
            }
        });
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

    public void openChatPage(String myToken, String theirUsername) throws IOException {
        FXMLLoader fxmlLoader = GraphicMain.graphicMain.goToPageReturnLoader(ChatPageController.FXML_PATH, ChatPageController.TITLE);
        ChatPageController chatPageController = fxmlLoader.getController();
        chatPageController.setPeople(myToken,theirUsername);
        //GeneralRequestBuilder.buildSaveChatMessages(theirUsername,new ArrayList<>());
        try {
            chatPageController.setMessages(GeneralRequestBuilder.buildSetChatMessagesRequest(theirUsername));
        } catch (Exception e) {
            e.printStackTrace();
            ManagerPanelController.alertError(e.getMessage());
        }
        chatPageController.initialize();
    }
}
