package Main.client.graphicView.scenes.ManagerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.ManagerRequestBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditCategoryController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/EditCategoryPanel.fxml";
    public static final String TITLE = "Edit Category";


    private static String categoryName;
    @FXML
    private ComboBox editOption;
    @FXML
    private Label guideLable;
    @FXML
    private TextField editContent;
    @FXML
    private Button save;

    public static void setCategoryName(String categoryNamee) {
        categoryName = categoryNamee;
    }

    public void initialize() {
        guideLable.setVisible(false);
        editContent.setVisible(false);
        editOption.setDisable(false);
        editContent.setDisable(false);
        save.setVisible(false);
        editContent.clear();
        editOption.setId("editOption");
        String[] options = {"Change Category Name",
                "Add a Product to Category",
                "Remove a Product from Category",
                "Add a Special Feature",
                "Remove a Special Feature"};

        editOption.getItems().addAll(options);
        editOption.setVisibleRowCount(5);
        //TODO here after calling initialize more than once combobox items are repeated.
        editOption.setOnAction(actionEvent -> {
            editOption.setDisable(true);
            if (editOption.getValue().equals("Change Category Name")) {
                guideLable.setText("Enter the new name for category instead of \"" + categoryName + "\":");
            } else if (editOption.getValue().equals("Add a Product to Category")) {
                guideLable.setText("Enter the ID of product you want to add to this category:");
            } else if (editOption.getValue().equals("Remove a Product from Category")) {
                guideLable.setText("Enter the ID of product you want to remove:");
            } else if (editOption.getValue().equals("Add a Special Feature")) {
                guideLable.setText("Enter the special feature name you want to add:");
            } else if (editOption.getValue().equals("Remove a Special Feature")) {
                guideLable.setText("Enter the special feature you want to remove:");
            }
            guideLable.setVisible(true);
            editContent.setVisible(true);
            save.setVisible(true);
        });
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void saveChanges() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
//        EditCategory editCategory = null;
//        try {
//            editCategory = GraphicMain.managerController.getCategoryToEdit(category.getName());
//        } catch (Exception e) {
//            ManagerPanelController.alertError(e.getMessage());
//            initialize();
//        }
        String newContent = editContent.getText();
        editContent.setStyle("-fx-border-width: 0;");
//        if (editOption.getValue().equals("Change Category Name")) {
//            editCategory.setName(newContent);
//        } else if (editOption.getValue().equals("Add a Product to Category")) {
//            editCategory.addProductToBeAdded(newContent);
//        } else if (editOption.getValue().equals("Remove a Product from Category")) {
//            editCategory.addProductToBeRemoved(newContent);
//        } else if (editOption.getValue().equals("Add a Special Feature")) {
//            editCategory.addSpecialFeatureToBeAdded(newContent);
//        } else if (editOption.getValue().equals("Remove a Special Feature")) {
//            editCategory.addSpecialFeatureToBeRemoved(newContent);
//        }
        try {
            //GraphicMain.managerController.submitCategoryEdits(editCategory);
            String editResult = ManagerRequestBuilder.buildEditCategoryRequest(categoryName, newContent, editOption.getValue().toString());
            ManagerPanelController.alertInfo(editResult);
        } catch (Exception e) {
            ManagerPanelController.alertError(e.getMessage());
            initialize();
        }
    }
}
