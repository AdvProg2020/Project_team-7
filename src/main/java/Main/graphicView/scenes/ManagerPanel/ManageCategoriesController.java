package Main.graphicView.scenes.ManagerPanel;

import Main.graphicView.GraphicMain;
import Main.model.Category;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
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
    private Button createCategory;




    public void initialize(){
        categoriesList.getItems().clear();
        categoriesList.getItems().addAll(Category.categoriesList());
        categoriesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (categoriesList.getSelectionModel().getSelectedItem() != null){
                    String name = categoriesList.getSelectionModel().getSelectedItem().toString();
                    Category category = null;
                    try {
                        category = Category.getCategoryWithName(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showCategoryOptions(category);
                }
            }
        });
    }

    private void showCategoryOptions(Category category){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(category.getName()+"\n"+category.showSpecialFeatures());
        alert.setTitle("Category Menu");
        alert.setContentText("what do you want to do with this category?");
        alert.getButtonTypes().clear();
        ButtonType remove = new ButtonType("Remove it");
        ButtonType edit = new ButtonType("Edit");
        ButtonType done = new ButtonType("Done!");
        alert.getButtonTypes().addAll(remove,edit,done);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.equals(remove)){
            removeCategory(category);
        } else if (option.equals(edit)){
            editCategory(category);
        }
    }

    private void removeCategory(Category category){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Category");
        alert.setHeaderText("All of products of this category will be deleted.");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.equals(ButtonType.OK)){
            category.removeCategory();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Category "+category.getName()+" deleted successfully.");
            alert1.setHeaderText(null);
            alert1.setTitle("Category Deleted");
            alert1.showAndWait();
        }
    }

    private void editCategory(Category category){
        System.out.println("CATEGORY EDIT PAGE");
    }

    public void goBack() throws IOException {
        GraphicMain.graphicMain.back();
    }
}
