package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

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

    private String myToken;
    private String theirUsername;
    private ArrayList<String> messages = new ArrayList<>();

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public void setPeople(String myToken, String theirUsername) {
        this.myToken = myToken;
        this.theirUsername = theirUsername;
        String chatPeople = GeneralRequestBuilder.buildOpenChatRequest(myToken, theirUsername);
        if (chatPeople.startsWith("meBuyer")) {
            chatWith.setText("SUPPORTER: " + theirUsername);
            iAm.setText("BUYER: " + chatPeople.split("#")[1]);
        } else if (chatPeople.startsWith("meSupporter")) {
            chatWith.setText("BUYER: " + theirUsername);
            iAm.setText("SUPPORTER: " + chatPeople.split("#")[1]);
        } else {
            ManagerPanelController.alertError("Too many request! Please try again later!");
        }
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
        if (!messageBox.getText().isEmpty()) {
            if (iAm.getText().startsWith("BUYER")) {
                messages.add("buyer: " + messageBox.getText());
            } else if (iAm.getText().startsWith("SUPPORTER")){
                messages.add("supporter: " + messageBox.getText());
            } else {
                ManagerPanelController.alertError("an error occurred.");
            }
            messageBox.clear();
        }
        refreshChatPage();
    }

    public void refreshChatPage() throws IOException {
        FXMLLoader fxmlLoader = GraphicMain.graphicMain.goToPageReturnLoader(ChatPageController.FXML_PATH, ChatPageController.TITLE);
        ChatPageController chatPageController = fxmlLoader.getController();
        chatPageController.setPeople(myToken, theirUsername);
        GeneralRequestBuilder.buildSaveChatMessages(theirUsername, this.messages);
        try {
            chatPageController.setMessages(GeneralRequestBuilder.buildSetChatMessagesRequest(theirUsername));
        } catch (Exception e) {
            e.printStackTrace();
            ManagerPanelController.alertError(e.getMessage());
        }
        GeneralRequestBuilder.buildSaveChatMessages(theirUsername, this.messages);
        chatPageController.initialize();
    }
}
