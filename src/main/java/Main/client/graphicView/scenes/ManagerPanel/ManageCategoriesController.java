package Main.client.graphicView.scenes.ManagerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.client.requestBuilder.ManagerRequestBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ManageCategoriesController {
    public static final String FXML_PATH = "src/main/sceneResources/ManagerPanel/ManageCategories.fxml";
    public static final String TITLE = "Manage Categories";

    @FXML
    private TextArea specialFeatures;
    @FXML
    private TextField categoryName;
    @FXML
    private ListView categoriesList;
    @FXML
    private Label imageName;
    private String path;


    public void initialize() {
        categoriesList.getItems().clear();
        //categoriesList.getItems().addAll(Category.categoriesList());
        String categoryData = ManagerRequestBuilder.buildinitializeManageCategoriesRequest();
        if (!categoryData.equals(""))
            categoriesList.getItems().addAll(categoryData.split("#"));
        categoriesList.setOnMouseClicked(mouseEvent -> {
            if (categoriesList.getSelectionModel().getSelectedItem() != null) {
                String name = categoriesList.getSelectionModel().getSelectedItem().toString();
                categoriesList.getSelectionModel().clearSelection();
//                    Category category = null;
//                    try {
//                        category = Category.getCategoryWithName(name);
//                    } catch (Exception e) {
//                        ManagerPanelController.alertError(e.getMessage());
//                    }
                try {
                    showCategoryOptions(name);
                } catch (IOException e) {
                    ManagerPanelController.alertError(e.getMessage());
                }
            }
        });
        categoryName.clear();
        specialFeatures.clear();
    }

    private void showCategoryOptions(String categoryName) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //alert.setHeaderText(category.getName() + "\n" + category.showSpecialFeatures());
        alert.setHeaderText(categoryName + "\n" + ManagerRequestBuilder.buildShowCategoryInformationRequest(categoryName));
        alert.setTitle("Category Menu");
        alert.setContentText("what do you want to do with this category?");
        alert.getButtonTypes().clear();
        ButtonType remove = new ButtonType("Remove it");
        ButtonType edit = new ButtonType("Edit");
        ButtonType done = new ButtonType("Done!");
        alert.getButtonTypes().addAll(done, edit, remove);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(remove)) {
            removeTheCategory(categoryName);
        } else if (option.get().equals(edit)) {
            editCategory(categoryName);
        }
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

    private void removeTheCategory(String categoryName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Category");
        alert.setHeaderText("All of products of this category will be deleted.");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {
            try {
                //GraphicMain.managerController.removeCategoryWithName(category.getName());
                ManagerPanelController.alertInfo(ManagerRequestBuilder.buildRemoveCategoryWithNameRequest(categoryName));
            } catch (Exception e) {
                ManagerPanelController.alertError(e.getMessage());
            }
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Category " + categoryName + " deleted successfully.");
            alert1.setHeaderText(null);
            alert1.setTitle("Category Deleted");
            alert1.showAndWait();
            initialize();
        }
    }

    private void editCategory(String categoryName) throws IOException {
        GraphicMain.graphicMain.goToPage(EditCategoryController.FXML_PATH, EditCategoryController.TITLE);
        EditCategoryController.setCategoryName(categoryName);
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }

    public void createCategory() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        String[] special = specialFeatures.getText().split("\n");
        ArrayList<String> specials = new ArrayList<>(Arrays.asList(special));
        String name = categoryName.getText();
        if (imageName.getText().startsWith("No image") || imageName.getText().startsWith("PLEASE") || imageName.getText().startsWith("choose ")) {
            imageName.setText("PLEASE SELECT AN IMAGE");
            imageName.setStyle("-fx-text-fill:red;");
        } else {
            try {
                //ManagerPanelController.alertInfo(GraphicMain.managerController.createCategory(name, specials, path));
                ManagerPanelController.alertInfo(ManagerRequestBuilder.buildCreateCategoryRequest(name, specials, path));
                initialize();
            } catch (Exception e) {
                ManagerPanelController.alertError(e.getMessage());
            }
        }
    }

    public void chooseImage() {
        List<String> extensions = new ArrayList<>();
        extensions.add("*.png");
        extensions.add("*.jpg");
        extensions.add("*.jpeg");
        FileChooser.ExtensionFilter extChooser = new FileChooser.ExtensionFilter("only images", extensions);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extChooser);
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("src/main/java/Main/client/graphicView/resources/images/categoryImages"));
        File file = fileChooser.showOpenDialog(GraphicMain.stage);
        if (file != null) {
            if (file.getParent().endsWith("categoryImages")) {
                if (file != null) {
                    imageName.setStyle("-fx-text-fill:green;");
                    imageName.setText(file.getName());
                    path = "src/main/java/Main/client/graphicView/resources/images/categoryImages" + "/" + file.getName();
                }
            }
        } else {
            imageName.setStyle("-fx-text-fill:red;");
            imageName.setText("choose from opened folder");
        }
    }
}
