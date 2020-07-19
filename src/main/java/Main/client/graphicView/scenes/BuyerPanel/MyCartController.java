package Main.client.graphicView.scenes.BuyerPanel;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.MainMenuController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
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
//        BuyerController.setBuyerController();
//        totalPrice.setText(Double.toString(((BuyerAccount) GeneralController.currentUser).getCart().getCartTotalPriceConsideringOffs()));
        totalPrice.setText(BuyerRequestBuilder.buildInitializeCartAndPriceRequest());
        TableColumn imageCol = new TableColumn("Image");
        TableColumn nameCol = new TableColumn<>("Name");
        TableColumn idCol = new TableColumn("Id");
        TableColumn priceCol = new TableColumn("Price");
        TableColumn onePriceCol = new TableColumn("One");
        TableColumn allPriceCol = new TableColumn("All");
        TableColumn numberCol = new TableColumn("Number");
        TableColumn decreaseCol = new TableColumn("Decrease");
        TableColumn currentNumberCol = new TableColumn("Current");
        TableColumn increaseNumberCol = new TableColumn("Increase");
        imageCol.setMinWidth(150);
        imageCol.setSortable(false);
        nameCol.setMinWidth(175);
        idCol.setMinWidth(70);
        priceCol.setMinWidth(220);
        onePriceCol.setMinWidth(110);
        allPriceCol.setMinWidth(110);
        numberCol.setMinWidth(335);
        decreaseCol.setMinWidth(115);
        decreaseCol.setSortable(false);
        currentNumberCol.setMinWidth(105);
        increaseNumberCol.setMinWidth(115);
        increaseNumberCol.setSortable(false);
        imageCol.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        onePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPriceCol.setCellValueFactory(new PropertyValueFactory<>("tempTotalPrice"));
        currentNumberCol.setCellValueFactory(new PropertyValueFactory<>("tempNumberOfProduct"));
        priceCol.getColumns().addAll(onePriceCol, allPriceCol);
        decreaseCol.setCellFactory(ActionButtonTableCell.forTableColumn("-", (Product p) -> {
            try {
                //p.getTempCartProduct().decreaseNumberByOne();
                String decreaseResult = BuyerRequestBuilder.buildDecreaseCartProductRequest(p.productId);
                if (!decreaseResult.equals("decreased"))
                    ManagerPanelController.alertError(decreaseResult);
            } catch (Exception e) {
                ManagerPanelController.alertError(e.getMessage());
            } finally {
                initialize();
            }
            return p;
        }));
        increaseNumberCol.setCellFactory(ActionButtonTableCell.forTableColumn("+", (Product p) -> {
            try {
                //p.getTempCartProduct().increaseNumberByOne();
                String increaseResult = BuyerRequestBuilder.buildIncreaseCartProductRequest(p.productId);
                if (!increaseResult.equals("increased"))
                    ManagerPanelController.alertError(increaseResult);
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
        cartTable.sortPolicyProperty();
        //cartTable.setItems(Product.getCartProductsAsPro());
        try {
            cartTable.setItems(BuyerRequestBuilder.buildGetCartProductsRequest());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ManagerPanelController.alertError(e.getMessage());
        }
        //System.out.println(imageCol.getCellData(0));
        //TODO show image
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        GeneralRequestBuilder.buildLogoutRequest();
        GraphicMain.token = "0000";
        //goBack();
        GraphicMain.graphicMain.goToPage(MainMenuController.FXML_PATH, MainMenuController.TITLE);
    }

    public void goToPurchase() throws IOException {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.goToPage(PurchaseController.FXML_PATH, PurchaseController.TITLE);
    }

    public void goBack() {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
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
        return getTableView().getItems().get(getIndex());
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

class Product {
    public String productId;
    private String name;
    private double price;
    private String imagePath;
    private int tempNumberOfProduct;
    private double tempTotalPrice;
}
