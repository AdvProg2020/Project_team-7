package Main.client.graphicView.scenes.ManagerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.server.model.discountAndOffTypeService.DiscountCode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageDiscountsController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageDiscountCodes.fxml";
    public static final String TITLE = "Manage Discounts";

    @FXML
    private ListView discountsList;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private TextField percent;
    @FXML
    private TextField maxAmount;
    @FXML
    private TextField maxNumberOfUse;
    @FXML
    private TextArea buyers;


    public void initialize() {
        discountsList.getItems().clear();
        discountsList.getItems().addAll(DiscountCode.getDiscountsList());
        discountsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (discountsList.getSelectionModel().getSelectedItem() != null) {
                    String code = discountsList.getSelectionModel().getSelectedItem().toString();
                    code = code.substring(1, code.indexOf(' '));
                    discountsList.getSelectionModel().clearSelection();
                    DiscountCode discountCode = null;
                    try {
                        discountCode = DiscountCode.getDiscountCodeWithCode(code);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        showDiscountOptions(discountCode);
                    } catch (IOException e) {
                        ManagerPanelController.alertError(e.getMessage());
                    }
                }
            }
        });
    }

    private void showDiscountOptions(DiscountCode discountCode) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(discountCode.viewMeAsManager());
        alert.setTitle("Discount Menu");
        alert.setContentText("what do you want to do with this discount?");
        alert.getButtonTypes().clear();
        ButtonType remove = new ButtonType("Remove it");
        ButtonType edit = new ButtonType("Edit");
        ButtonType done = new ButtonType("Done!");
        alert.getButtonTypes().addAll(done, edit, remove);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(remove)) {
            removeDiscount(discountCode);
        } else if (option.get().equals(edit)) {
            editDiscount(discountCode);
        }
    }

    public void logout() throws IOException{
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    private void removeDiscount(DiscountCode discountCode) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Discount");
        alert.setHeaderText("Discount will be deleted completely.");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {
            discountCode.removeDiscountCode();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Discount " + discountCode.getCode() + " deleted successfully.");
            alert1.setHeaderText(null);
            alert1.setTitle("Discount Deleted");
            alert1.showAndWait();
            initialize();
        }
    }

    private void editDiscount(DiscountCode discountCode) throws IOException {
        GraphicMain.graphicMain.goToPage(EditDiscountController.FXML_PATH, EditDiscountController.TITLE);
        EditDiscountController.setDiscount(discountCode);
    }

    public void goBack() throws IOException {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void createDiscount() {
        ArrayList<String> discountInfo = new ArrayList<>();
        getDiscountInfo(discountInfo);
        ArrayList<String> buyersList = new ArrayList<>();
        getBuyerIdList(buyersList);
        try {
            ManagerPanelController.alertInfo(GraphicMain.managerController.createDiscountCode(buyersList, discountInfo));
            initialize();
        } catch (Exception e) {
            ManagerPanelController.alertError(e.getMessage());
        }
    }

    private void getBuyerIdList(ArrayList<String> buyersList) {
        String[] users = buyers.getText().split("\n");
        for (String user : users) {
            buyersList.add(user.trim());
        }
    }

    private void getDiscountInfo(ArrayList<String> discountInfo) {
        String startDate = this.startDate.getText().trim();
        discountInfo.add(startDate);
        String endDate = this.endDate.getText().trim();
        discountInfo.add(endDate);
        String percent = this.percent.getText().trim();
        discountInfo.add(percent);
        String maxAmount = this.maxAmount.getText().trim();
        discountInfo.add(maxAmount);
        String muxNumberOfUse = this.maxNumberOfUse.getText().trim();
        discountInfo.add(muxNumberOfUse);
    }
}