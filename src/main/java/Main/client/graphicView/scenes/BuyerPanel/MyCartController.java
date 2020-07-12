package Main.client.graphicView.scenes.BuyerPanel;

import Main.server.controller.BuyerController;
import Main.server.controller.GeneralController;
import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.server.model.CartProduct;
import Main.server.model.Product;
import Main.server.model.accounts.BuyerAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.function.Function;

public class MyCartController {
    public static final String FXML_PATH = "src/main/sceneResources/BuyerPanel/ViewCart.fxml";
    public static final String TITLE = "My Cart";

    @FXML
    private Label totalPrice;
    @FXML
    private TableView cartTable;

    public void initialize() {
        BuyerController.setBuyerController();
        totalPrice.setText(Double.toString(((BuyerAccount) GeneralController.currentUser).getCart().getCartTotalPriceConsideringOffs()));
        cartTable.sortPolicyProperty();
        TableColumn<Product, String> imageCol = new TableColumn("Image");
        imageCol.setMinWidth(150);
        imageCol.setSortable(false);
        imageCol.setCellValueFactory(new PropertyValueFactory<Product, String>("imagePath"));
        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        nameCol.setMinWidth(175);
        TableColumn<Product, String> idCol = new TableColumn("Id");
        idCol.setMinWidth(70);
        idCol.setCellValueFactory(new PropertyValueFactory<Product, String>("productId"));
        TableColumn priceCol = new TableColumn("Price");
        priceCol.setMinWidth(220);
        TableColumn<Product, Double> onePriceCol = new TableColumn("One");
        onePriceCol.setMinWidth(110);
        onePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Product, Double> allPriceCol = new TableColumn("All");
        allPriceCol.setMinWidth(110);
        allPriceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("tempTotalPrice"));
        priceCol.getColumns().addAll(onePriceCol, allPriceCol);
        TableColumn numberCol = new TableColumn("Number");
        numberCol.setMinWidth(335);
        TableColumn decreaseCol = new TableColumn("Decrease");
        decreaseCol.setMinWidth(115);
        decreaseCol.setSortable(false);
        decreaseCol.setCellFactory(ActionButtonTableCell.<Product>forTableColumn("-", (Product p) -> {
            try {
                p.getTempCartProduct().decreaseNumberByOne();
                GraphicMain.buttonSound.stop();
                GraphicMain.buttonSound.play();
            } catch (Exception e) {
                ManagerPanelController.alertError(e.getMessage());
            } finally {
                initialize();
            }
            return p;
        }));
        TableColumn<Product, Integer> currentNumberCol = new TableColumn("Current");
        currentNumberCol.setMinWidth(105);
        currentNumberCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("tempNumberOfProduct"));
        TableColumn increaseNumberCol = new TableColumn("Increase");
        increaseNumberCol.setMinWidth(115);
        increaseNumberCol.setSortable(false);
        increaseNumberCol.setCellFactory(ActionButtonTableCell.<Product>forTableColumn("+", (Product p) -> {
            try {
                p.getTempCartProduct().increaseNumberByOne();
                GraphicMain.buttonSound.stop();
                GraphicMain.buttonSound.play();
            } catch (Exception e) {
                ManagerPanelController.alertError(e.getMessage());
            } finally {
                initialize();
            }
            return p;
        }));
        numberCol.getColumns().setAll(decreaseCol, currentNumberCol, increaseNumberCol);
        cartTable.getColumns().setAll(imageCol, nameCol, idCol, priceCol, numberCol);
        cartTable.autosize();
        cartTable.setItems(getCartProductsAsPro());

        //System.out.println(imageCol.getCellData(0));
        //TODO show image
    }
    public void logout() throws IOException{
        GraphicMain.generalController.logout();
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH,MainMenuController.TITLE);
    }

    private ObservableList<CartProduct> getCartProducts() {
        ObservableList<CartProduct> cartProducts = FXCollections.observableArrayList();
        for (CartProduct cartProduct : ((BuyerAccount) GeneralController.currentUser).getCart().getCartProducts()) {
            cartProducts.add(cartProduct);
        }
        return cartProducts;
    }

    private ObservableList<Product> getCartProductsAsPro() {
        ObservableList<Product> cartProducts = FXCollections.observableArrayList();
        for (Product cartProduct : ((BuyerAccount) GeneralController.currentUser).getCart().getCartsProductList()) {
            cartProducts.add(cartProduct);
        }
        return cartProducts;
    }

    public void goToPurchase() throws IOException {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(PurchaseController.FXML_PATH, PurchaseController.TITLE);
    }

    public void goBack() {
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}

class ActionButtonTableCell<S> extends TableCell<S, Button> {
    private final Button actionButton;

    public ActionButtonTableCell(String label, Function<S, S> function) {
        this.getStyleClass().add("action-button-table-cell");
        this.actionButton = new Button(label);
        this.actionButton.setOnAction((ActionEvent e) -> function.apply(getCurrentItem()));
        this.actionButton.setMaxWidth(Double.MAX_VALUE);
        if (label.equals("-"))
            actionButton.setId("decreaseButton");
        if (label.equals("+"))
            actionButton.setId("increaseButton");
    }

    public S getCurrentItem() {
        return (S) getTableView().getItems().get(getIndex());
    }

    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, Function<S, S> function) {
        return param -> new ActionButtonTableCell<>(label, function);
    }

    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}