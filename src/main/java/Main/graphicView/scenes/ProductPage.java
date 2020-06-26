package Main.graphicView.scenes;

import Main.controller.GeneralController;
import Main.graphicView.GraphicMain;
import Main.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.model.Product;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.discountAndOffTypeService.DiscountAndOffStat;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/productPage.fxml";
    public static final String TITLE = "Seller Panel";
    private Product currentProduct;
    private ImageView productImage;

    @FXML
    private ImageView scoreImage;
    @FXML
    private Label scoreLabel;
    @FXML
    private ScrollPane scrollImage;
    @FXML
    private TextArea generalFeatures;
    @FXML
    private TextArea specialFeatures;
    @FXML
    private ScrollPane specialFeaturesPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
        zoomProperty.addListener(arg0 -> {
            productImage.setFitWidth(zoomProperty.get());
            productImage.setFitHeight(zoomProperty.get());
        });
        scrollImage.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() > 0) {
                zoomProperty.set(zoomProperty.get() * 1.1);
            } else if (event.getDeltaY() < 0) {
                if (zoomProperty.get() != productImage.getX())
                    zoomProperty.set(zoomProperty.get() / 1.1);
            }
        });
        currentProduct = GeneralController.currentProduct;
        productImage.setImage(new Image(new File(currentProduct.getImagePath()).toURI().toString()));
        setScoreImage();
        scoreLabel.setText(currentProduct.getAverageScore().toString());
        productImage.preserveRatioProperty().set(true);
        scrollImage.setContent(productImage);
        generalFeatures.setText(makeGeneralFeatures());
        if(currentProduct.getCategory() !=null){
            specialFeaturesPane.setVisible(true);
            specialFeatures.setText(currentProduct.showSpecialFeatures());
        }


    }

    public void setScoreImage(){
        double score = currentProduct.getAverageScore();
        if(score==0)
            scoreImage.setImage(new Image(new File("src/main/java/Main/graphicView/resources/images/score0.png").toURI().toString()));
        else if(score>0 && score<=1)
            scoreImage.setImage(new Image(new File("src/main/java/Main/graphicView/resources/images/score1.png").toURI().toString()));
        else if(score>1 && score<=2)
            scoreImage.setImage(new Image(new File("src/main/java/Main/graphicView/resources/images/score2.png").toURI().toString()));
        else if(score>2 && score<=3)
            scoreImage.setImage(new Image(new File("src/main/java/Main/graphicView/resources/images/score3.png").toURI().toString()));
        else if(score>3 && score<=4)
            scoreImage.setImage(new Image(new File("src/main/java/Main/graphicView/resources/images/score4.png").toURI().toString()));
        else if(score>4 && score<=5)
            scoreImage.setImage(new Image(new File("src/main/java/Main/graphicView/resources/images/score5.png").toURI().toString()));
    }

    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    public void goToUserPanelPage() throws IOException {
        Account account = GeneralController.currentUser;
        if (account instanceof ManagerAccount) {
            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
        } else if (account instanceof SellerAccount) {
            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
        } else if (account instanceof BuyerAccount) {
            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
        }
        GraphicMain.buttonSound.stop();
        GraphicMain.buttonSound.play();
    }

    public String makeGeneralFeatures(){
        if (currentProduct.getOff() != null && currentProduct.getOff().getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
            currentProduct.getOff().removeOff();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name: " + currentProduct.getName() + "\n");
        stringBuilder.append("brand:" + currentProduct.getBrand() + "\n");
        stringBuilder.append("description: " + currentProduct.getDescription() + "\n");
        stringBuilder.append("price: " + currentProduct.getProductFinalPriceConsideringOff() + "\n");
        stringBuilder.append("off amount: " + currentProduct.makeOffAmount() + "\n");
        if(currentProduct.getCategory() != null)
            stringBuilder.append("category: " + currentProduct.getCategory().getName());
        return stringBuilder.toString();
    }
}
