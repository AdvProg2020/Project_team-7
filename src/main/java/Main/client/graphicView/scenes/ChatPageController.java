package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.HelpCenterController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatPageController {
    public static final String FXML_PATH = "src/main/sceneResources/ChatPage.fxml";
    public static final String TITLE = "Supporter Chat";

    @FXML
    private VBox chatBox;
    @FXML
    private TextArea messageBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label chatWith;
    @FXML
    private Label iAm;

    private int messageNumber = 0;
    private String myToken;
    private String theirUsername;
    private ArrayList<String> messages = new ArrayList<>();
    private HelpCenterController theOtherControllerAsHelpCenter;
    private SupporterPanelController theOtherControllerAsSupporterPanel;

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public void setPeople(String myToken, String theirUsername, HelpCenterController helpCenterController, SupporterPanelController supporterPanelController) {
        this.myToken = myToken;
        this.theirUsername = theirUsername;
        String chatPeople = GeneralRequestBuilder.buildOpenChatRequest(myToken, theirUsername);
        if (chatPeople.startsWith("meBuyer")) {
            chatWith.setText("SUPPORTER: " + theirUsername);
            iAm.setText("BUYER: " + chatPeople.split("#")[1]);
        } else {
            chatWith.setText("BUYER: " + theirUsername);
            iAm.setText("SUPPORTER: " + chatPeople.split("#")
                    [1]);
        }
        if (helpCenterController != null)
            theOtherControllerAsHelpCenter = helpCenterController;
        if (supporterPanelController != null)
            theOtherControllerAsSupporterPanel = supporterPanelController;
    }

    public void initialize() {
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
        chatBox.setAlignment(Pos.BOTTOM_LEFT);
        for (String message : messages) {
            int massageNum = 0;
            if (message.startsWith("buyer:"))
                massageNum = 0;
            else if (message.startsWith("supporter:"))
                massageNum = 1;

            Label label = new Label(message);
            label.setPadding(new Insets(30, 30, 30, 30));
            label.setMinHeight(100);
            label.setMinWidth(100);
            label.setMaxWidth(250);
            label.setAlignment(Pos.CENTER);
            label.setWrapText(true);
            label.setTranslateX((Math.pow(-1, massageNum) + 1) * (100));
            if (massageNum % 2 == 0)
                label.setId("myMessage");
            else
                label.setId("theirMessage");
            chatBox.getChildren().add(label);
        }
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void send() throws IOException {
        refreshChatPage();
        if (!messageBox.getText().isEmpty()) {
            if (iAm.getText().startsWith("BUYER")) {
                messages.add("buyer: " + messageBox.getText());
            } else {
                messages.add("supporter: " + messageBox.getText());
            }
            messageBox.clear();
        }
    }

    public void refreshChatPage(String myToken, String theirUsername) throws IOException {
        FXMLLoader fxmlLoader = GraphicMain.graphicMain.goToPageReturnLoader(ChatPageController.FXML_PATH, ChatPageController.TITLE);
        ChatPageController chatPageController = fxmlLoader.getController();
        chatPageController.setPeople(myToken, theirUsername, theOtherControllerAsHelpCenter, theOtherControllerAsSupporterPanel);
        GeneralRequestBuilder.buildSaveChatMessages(theirUsername, messages);
        try {
            chatPageController.setMessages(GeneralRequestBuilder.buildSetChatMessagesRequest(theirUsername));
        } catch (Exception e) {
            e.printStackTrace();
            ManagerPanelController.alertError(e.getMessage());
        }
        if (theOtherControllerAsSupporterPanel != null)
            theOtherControllerAsSupporterPanel.openChatPage(myToken, theirUsername);
        if (theOtherControllerAsHelpCenter != null)
            theOtherControllerAsHelpCenter.openChatPage(myToken, theirUsername);
        initialize();
    }

    public void refreshChatPage() throws IOException {
        refreshChatPage(myToken, theirUsername);
    }
}
