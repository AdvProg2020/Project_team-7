package Main.graphicView.scenes;

import Main.graphicView.GraphicMain;
import Main.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerProductPage implements Initializable {

    public static final String FXML_PATH = "src/main/sceneResources/SellerPanel/sellerProductPage.fxml";
    public static final String TITLE = "Seller Product page";
    public String productId;

    @FXML
    private ImageView productImage;
    @FXML
    private ImageView averageScoreImage;
    @FXML
    private Label averageScore;
    @FXML
    private Label digestLabel;
    @FXML
    private Label buyersList;


    public void goBack(){
        GraphicMain.graphicMain.back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productId = SellerProductsPage.selectedProduct;
        try {
            Product product = Product.getProductWithId(productId);
            productImage.setImage(new Image(product.getImagePath()));
            digestLabel.setText(makeDigestLabel(product));
            setScoreImage(product);
            averageScore.setText((product.getAverageScore()).toString());
            buyersList.setText(product.viewBuyers());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String makeDigestLabel(Product product){
        return "name: " + product.getName() +
                "\n\tid: " + product.getProductId() +
                "\n\tdescription: " + product.getDescription() +
                "\n\tprice: " + product.getPrice() +
                "\n\toff amount: " + product.makeOffAmount() +
                "\n\tcategory: " + product.getCategory().getName();
    }

    public void setScoreImage(Product product){
        double score = product.getAverageScore();
        if(score==0)
            averageScoreImage.setImage(new Image("src/main/java/Main/graphicView/resources/images/score0.png"));
        else if(score>0 && score<=1)
            averageScoreImage.setImage(new Image("src/main/java/Main/graphicView/resources/images/score1.png"));
        else if(score>1 && score<=2)
            averageScoreImage.setImage(new Image("src/main/java/Main/graphicView/resources/images/score2.png"));
        else if(score>2 && score<=3)
            averageScoreImage.setImage(new Image("src/main/java/Main/graphicView/resources/images/score3.png"));
        else if(score>3 && score<=4)
            averageScoreImage.setImage(new Image("src/main/java/Main/graphicView/resources/images/score4.png"));
        else if(score>4 && score<=5)
            averageScoreImage.setImage(new Image("src/main/java/Main/graphicView/resources/images/score5.png"));

    }
}