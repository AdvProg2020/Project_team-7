package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.SellerRequestBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SalesHistoryPage implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/salesHistoryPage.fxml";
    public static final String TITLE = "Sales History";

    @FXML
    private VBox logIdBox;
    @FXML
    private Label logDetails;

    public ArrayList<Label> getLabels(){
        ArrayList<Label> idList = new ArrayList<>();
        for(int i=0;i<10;i++){
            Label label =(Label) logIdBox.getChildren().get(i);
            idList.add(label);
        }
        return idList;
    }

    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillLogVBox();
    }

    public void FillLogVBox() {
        String response = SellerRequestBuilder.getSellLogList();
        if(response.equals("empty")){
            getLabels().get(0).setText("no sales yet!");
        } else{
            String[] list = response.split("#");
            for(int i=0; i<list.length; i++){
                getLabels().get(i).setText(list[i]);
            }
        }
//        if(GraphicMain.sellerController.getSellLogIds().equals(null)){
//            getLabels().get(0).setText("no sales yet!");
//        } else{
//            for(int i=0; i<GraphicMain.sellerController.getSellLogIds().size(); i++){
//                getLabels().get(i).setText(GraphicMain.sellerController.getSellLogIds().get(i));
//            }
//        }
    }

    public void showLogDetails(MouseEvent mouseEvent){
        Label logLabel = (Label) mouseEvent.getSource();
        String id = logLabel.getText();
        logDetails.setText(SellerRequestBuilder.getLogDetails(id));
//        logDetails.setText(GraphicMain.sellerController.viewLogDetails(id));
    }
}
