package Main.server.serverRequestProcessor;

import Main.client.graphicView.GraphicMain;

public class SellerRequestProcessor {
    public static String buildCommentResponse(String[] splitRequest){
        String title = splitRequest[2];
        String content = splitRequest[3];
        try {
            GraphicMain.generalController.addComment(title, content);
            return "success";
        }catch (Exception e){
            return "error#" + e.getMessage();
        }
    }
}
