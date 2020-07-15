package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;

public class SellerRequestBuilder {

    public static String buildCommentRequest(String title, String content){
        String string = GraphicMain.token + "#addComment#" + title + content;
        return ClientMain.client.sendRequest(string);
    }
}
