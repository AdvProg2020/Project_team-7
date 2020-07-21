package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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


    public void setPeople(String myToken, String theirUsername) {
        this.myToken = myToken;
        this.theirUsername = theirUsername;
        String chatPeople = GeneralRequestBuilder.buildOpenChatRequest(myToken,theirUsername);
        if (chatPeople.startsWith("meBuyer")){
            chatWith.setText("SUPPORTER: "+theirUsername);
            iAm.setText("BUYER: "+chatPeople.split("#")[1]);
        } else {
            chatWith.setText("BUYER: "+theirUsername);
            iAm.setText("SUPPORTER: "+chatPeople.split("#")[1]);
        }
    }

    public void initialize() {
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void send() {
        if (!messageBox.getText().isEmpty()) {
            chatBox.setAlignment(Pos.BOTTOM_LEFT);
            Label label = new Label(messageBox.getText());
            if (messageNumber%2==0) {
                label.setId("myMessage");
            } else {
                label.setId("theirMessage");
            }
            label.setPadding(new Insets(30, 30, 30, 30));
            label.setMinHeight(100);
            label.setMinWidth(100);
            label.setMaxWidth(250);
            label.setAlignment(Pos.CENTER);
            label.setWrapText(true);
            label.setTranslateX((Math.pow(-1, messageNumber) + 1) * (90));
            chatBox.getChildren().add(label);
            messageNumber++;
            messageBox.clear();
        }
    }
}
