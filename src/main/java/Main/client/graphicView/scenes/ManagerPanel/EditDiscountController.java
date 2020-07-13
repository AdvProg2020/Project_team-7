package Main.client.graphicView.scenes.ManagerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.server.model.discountAndOffTypeService.DiscountCode;
import Main.server.model.requests.EditDiscountCode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditDiscountController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/EditDiscountPanel.fxml";
    public static final String TITLE = "Edit Discount";
    private static DiscountCode discountCode;
    @FXML
    private ComboBox editOption;
    @FXML
    private Label guideLable;
    @FXML
    private TextField editContent;
    @FXML
    private Button save;

    public static void setDiscount(DiscountCode discountCode1) {
        discountCode = discountCode1;
    }

    public void initialize() {
        guideLable.setVisible(false);
        editContent.setVisible(false);
        editOption.setDisable(false);
        editContent.setDisable(false);
        save.setVisible(false);
        editContent.clear();
        editOption.setId("editOption");
        String[] options = {"Start Date",
                "End Date",
                "Percent",
                "Max Amount",
                "Max Number Of Use",
                "Add Buyer UserName",
                "Remove Buyer UserName"};

        editOption.getItems().addAll(options);
        editOption.setVisibleRowCount(7);
        //TODO here after calling initialize more than once combobox items are repeated.
        editOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                editOption.setDisable(true);
                if (editOption.getValue().equals("Start Date")) {
                    guideLable.setText("Enter the new Start Date for Discount instead of \"" + discountCode.getStartDateInStringFormat() + "\":");
                } else if (editOption.getValue().equals("End Date")) {
                    guideLable.setText("Enter the new End Date for Discount instead of\"" + discountCode.getEndDateInStringFormat());
                } else if (editOption.getValue().equals("Percent")) {
                    guideLable.setText("Enter the new percent for discount instead of\"" + discountCode.getPercent());
                } else if (editOption.getValue().equals("Max Amount")) {
                    guideLable.setText("Enter the new max amount for discount instead of\"" + discountCode.getMaxAmount());
                } else if (editOption.getValue().equals("Max Number Of Use")) {
                    guideLable.setText("Enter the new max number of use instead of\"" + discountCode.getMaxNumberOfUse());
                } else if (editOption.getValue().equals("Add Buyer UserName")) {
                    guideLable.setText("Enter the buyer username you want to add:");
                } else if (editOption.getValue().equals("Remove Buyer UserName")) {
                    guideLable.setText("Enter the buyer username you want to remove:");
                }
                guideLable.setVisible(true);
                editContent.setVisible(true);
                save.setVisible(true);
            }
        });
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void logout() throws IOException {
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    public void saveChanges() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        EditDiscountCode editDiscountCode = null;
        try {
            editDiscountCode = GraphicMain.managerController.getDiscountCodeToEdit(discountCode.getCode());
        } catch (Exception e) {
            ManagerPanelController.alertError(e.getMessage());
            initialize();
        }
        String newContent = editContent.getText();
        editContent.setStyle("-fx-border-width: 0;");
        if (editOption.getValue().equals("Start Date")) {
            editDiscountCode.setStartDate(newContent);
        } else if (editOption.getValue().equals("End Date")) {
            editDiscountCode.setEndDate(newContent);
        } else if (editOption.getValue().equals("Percent")) {
            editDiscountCode.setPercent(newContent);
        } else if (editOption.getValue().equals("Max Amount")) {
            editDiscountCode.setMaxAmount(newContent);
        } else if (editOption.getValue().equals("Max Number Of Use")) {
            editDiscountCode.setMaxNumberOfUse(newContent);
        } else if (editOption.getValue().equals("Add Buyer UserName")) {
            editDiscountCode.addUserToBeAdded(newContent);
        } else if (editOption.getValue().equals("Remove Buyer UserName")) {
            editDiscountCode.addUserToBeRemoved(newContent);
        }
        try {
            GraphicMain.managerController.submitDiscountCodeEdits(editDiscountCode);
            ManagerPanelController.alertInfo("Discount Edited Successfully!");
            goBack();
        } catch (Exception e) {
            ManagerPanelController.alertError(e.getMessage());
            initialize();
        }
    }
}
