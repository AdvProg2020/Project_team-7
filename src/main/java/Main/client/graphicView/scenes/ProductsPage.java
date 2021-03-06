package Main.client.graphicView.scenes;

import Main.client.graphicView.GraphicMain;
import Main.client.graphicView.scenes.BuyerPanel.BuyerPanelController;
import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;
import Main.client.requestBuilder.DataRequestBuilder;
import Main.client.requestBuilder.GeneralRequestBuilder;
import Main.server.controller.GeneralController;
import Main.server.model.Category;
import Main.server.model.Product;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.filters.*;
import Main.server.model.sorting.ProductsSort;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static java.util.Arrays.asList;


public class ProductsPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/productsPage.fxml";
    public static final String TITLE = "All Products";
    public FlowPane productsPane;
    public VBox categoryPane;
    public HBox pageNumberPane;
    public VBox brandsPane;
    public VBox sellersPane;
    public CheckBox availablesOnly;
    public TextField lowerBound;
    public TextField upperBound;
    public TextField searchText;
    public Pane adPane;
    public ImageView nextAdIcon;
    public Pane adPaneBG;
    public HBox sortPane;
    private AnimationTimer animationTimer;
    private ArrayList<Product> currentFilterResult = new ArrayList<>();
    private ToggleGroup categoryToggleGroup = new ToggleGroup();
    private ToggleGroup brandToggleGroup = new ToggleGroup();
    private ToggleGroup sellerToggleGroup = new ToggleGroup();
    private ToggleGroup sortToggleGroup = new ToggleGroup();
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Category> allCategories = new ArrayList<>();
    private ArrayList<String> allBrands = new ArrayList<>();
    private ArrayList<SellerAccount> allSellers = new ArrayList<>();
    //TODO filter ba id kar mikne ya reference categ?

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String allProductsResponse = DataRequestBuilder.buildAllProductsRequest();
        if (allProductsResponse.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
            return;
        } else if (allProductsResponse.equals("failure")) {
            GraphicMain.showInformationAlert("try again !");
            return;
        }
        readAllProductsData(allProductsResponse);
        String allCategoriesResponse = DataRequestBuilder.buildAllCategoriesRequest();
        if (allCategoriesResponse.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
            return;
        } else if (allCategoriesResponse.equals("failure")) {
            GraphicMain.showInformationAlert("try again !");
            return;
        }
        readAllCategoriesData(allCategoriesResponse);
        setAllBrands();
        String allSellersResponse = DataRequestBuilder.buildAllSellersRequest();
        if (allSellersResponse.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
            return;
        } else if (allSellersResponse.equals("failure")) {
            GraphicMain.showInformationAlert("try again !");
            return;
        }
        readAllSellersData(allSellersResponse);

//        GeneralController.setImagePaths();
        currentFilterResult.addAll(allProducts);
        setPageElementsDueToCurrentFilters();
        setCategoriesFilter();
        setBrandsFilter();
        setSellersFilter();
        setSortPane();

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    updateFilters();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        animationTimer.start();
        //TODO : (non-UI actions in another thread ! so that scheduled services not responsible)

        nextAd(null);

    }

    public ArrayList<String> getAllSellers() {
        ArrayList<String> allUniqueSellers = new ArrayList<>();
        for (SellerAccount seller : allSellers) {
            if (!allUniqueSellers.contains(seller.getUserName())) {
                allUniqueSellers.add(seller.getUserName());
            }
        }
        return allUniqueSellers;
    }

    private void readAllSellersData(String allSellersResponse) {
        GeneralController.jsonReader = new JsonReader(new StringReader(allSellersResponse));
        SellerAccount[] allSel = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, SellerAccount[].class);
        allSellers = (allSel == null) ? new ArrayList<>() : new ArrayList<>(asList(allSel));
    }

    private void setAllBrands() {
        for (Product product : allProducts) {
            if (!allBrands.contains(product.getBrand())) {
                allBrands.add(product.getBrand());
            }
        }
    }

    private void readAllCategoriesData(String allCategoriesResponse) {
        GeneralController.jsonReader = new JsonReader(new StringReader(allCategoriesResponse));
        Category[] allcat = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Category[].class);
        allCategories = (allcat == null) ? new ArrayList<>() : new ArrayList<>(asList(allcat));
    }

    public void readAllProductsData(String allProductsString) {
        GeneralController.jsonReader = new JsonReader(new StringReader(allProductsString));
        Product[] allPro = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Product[].class);
        allProducts = (allPro == null) ? new ArrayList<>() : new ArrayList<>(asList(allPro));
    }

    private void setSortPane() {
        RadioButton noSort = new RadioButton("none");
        noSort.getStyleClass().remove("radio-button");
        noSort.getStyleClass().add("toggle-button");
        noSort.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(noSort);
        RadioButton nameAscending = new RadioButton("name(ascending)");
        nameAscending.getStyleClass().remove("radio-button");
        nameAscending.getStyleClass().add("toggle-button");
        nameAscending.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(nameAscending);
        RadioButton nameDescending = new RadioButton("name(descending)");
        nameDescending.getStyleClass().remove("radio-button");
        nameDescending.getStyleClass().add("toggle-button");
        nameDescending.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(nameDescending);
        RadioButton mostVisited = new RadioButton("visit");
        mostVisited.getStyleClass().remove("radio-button");
        mostVisited.getStyleClass().add("toggle-button");
        mostVisited.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(mostVisited);
        RadioButton mostRated = new RadioButton("rate");
        mostRated.getStyleClass().remove("radio-button");
        mostRated.getStyleClass().add("toggle-button");
        mostRated.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(mostRated);
        RadioButton priceAscending = new RadioButton("cheapest");
        priceAscending.getStyleClass().remove("radio-button");
        priceAscending.getStyleClass().add("toggle-button");
        priceAscending.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(priceAscending);
        RadioButton priceDescending = new RadioButton("most expensive");
        priceDescending.getStyleClass().remove("radio-button");
        priceDescending.getStyleClass().add("toggle-button");
        priceDescending.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(priceDescending);
    }

    private void setProductsPane(int pageNumber) {
        productsPane.getChildren().clear();
        int startingIndex = (pageNumber - 1) * 10;
        int endingIndex = currentFilterResult.size();
        for (int i = startingIndex; i < startingIndex + 10 && i < endingIndex; i++) {
            Product product = currentFilterResult.get(i);
            productsPane.getChildren().add(product.createProductBoxForUI());
        }
    }

    public void logout() throws IOException {
        //GraphicMain.generalController.logout();
        String response = GeneralRequestBuilder.buildLogoutRequest();
        if (response.equals("tooManyRequests")) {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        } else if (response.equals("failure")) {
            GraphicMain.showInformationAlert("try again !");
        } else {
            GraphicMain.token = "0000";
            //goBack();
            GraphicMain.graphicMain.back();
        }
    }

    private void setPageNumberPane(int startPageNumber) {
        pageNumberPane.getChildren().clear();
        final int BUTTON_MIN_HEIGHT = 40;

        Button showLessPages = new Button("show less");
        showLessPages.setOnMouseClicked(event -> showLessPages());
        showLessPages.setMinHeight(BUTTON_MIN_HEIGHT);
        pageNumberPane.getChildren().add(showLessPages);

        int pageNumberButtonsNo = (int) Math.ceil(currentFilterResult.size() / 10.0);
        pageNumberButtonsNo = (pageNumberButtonsNo == 0 ? 1 : pageNumberButtonsNo);
        for (int i = startPageNumber; i <= startPageNumber + 12 && i <= pageNumberButtonsNo; i++) {
            Button number = new Button(i + "");
            number.setStyle("-fx-background-radius : 30;");
            int finalI = i;
            number.setOnMouseClicked(event -> setProductsPane(finalI));
            number.setMinWidth(BUTTON_MIN_HEIGHT);
            number.setMinHeight(BUTTON_MIN_HEIGHT);
            pageNumberPane.getChildren().add(number);
        }

        Button showMorePages = new Button("show more");
        showMorePages.setOnMouseClicked(event -> showMorePages());
        showMorePages.setMinHeight(BUTTON_MIN_HEIGHT);
        pageNumberPane.getChildren().add(showMorePages);

        ObservableList<Node> buttons = pageNumberPane.getChildren();
        Button firstPageNumber = (Button) buttons.get(1);
        Button lastPageNumber = (Button) buttons.get(buttons.size() - 2);
        if (pageNumberButtonsNo <= Integer.parseInt(lastPageNumber.getText())) {
            showMorePages.setDisable(true);
        }
        if (firstPageNumber.getText().equals("1")) {
            showLessPages.setDisable(true);
        }
    }

    private void showMorePages() {
        ObservableList<Node> buttons = pageNumberPane.getChildren();
        Button lastPageNumber = (Button) buttons.get(buttons.size() - 2);
        setPageNumberPane(Integer.parseInt(lastPageNumber.getText()) + 1);
    }

    private void showLessPages() {
        ObservableList<Node> buttons = pageNumberPane.getChildren();
        Button firstPageNumber = (Button) buttons.get(1);
        setPageNumberPane(Integer.parseInt(firstPageNumber.getText()) - 13);
    }

    private void setCategoriesFilter() {
        RadioButton noCategory = new RadioButton("none");
        noCategory.getStyleClass().remove("radio-button");
        noCategory.getStyleClass().add("toggle-button");
        noCategory.setMinWidth(250);
        noCategory.setToggleGroup(categoryToggleGroup);
        categoryPane.getChildren().add(noCategory);

        /***/
        int categoryNo = allCategories.size();
        for (int i = 0; i < 15 && i < categoryNo; i++) {
            Category category = allCategories.get(i);
            RadioButton categoryName = new RadioButton(category.getName());
            categoryName.getStyleClass().remove("radio-button");
            categoryName.getStyleClass().add("toggle-button");
            categoryName.setMinWidth(250);
            categoryName.setToggleGroup(categoryToggleGroup);
            categoryPane.getChildren().add(categoryName);
        }
        Button showMore = new Button("show more");
        categoryPane.getChildren().add(showMore);
        showMore.setOnMouseClicked(event -> showMoreCategories());
    }

    private void showMoreCategories() {
        ObservableList<Node> illustratedCategories = categoryPane.getChildren();
        RadioButton lastIllustratedCategory = (RadioButton) illustratedCategories.get(illustratedCategories.size() - 2);
        int indexOfLastCategory = 0;
        try {
            indexOfLastCategory = allCategories.indexOf(Category.getCategoryWithName(lastIllustratedCategory.getText()));
        } catch (Exception e) {
        }
        int CategoryNo = allCategories.size();
        for (int i = indexOfLastCategory + 1; i < indexOfLastCategory + 15 && i < CategoryNo; i++) {
            RadioButton categoryName = new RadioButton(allCategories.get(i).getName());
            categoryName.getStyleClass().remove("radio-button");
            categoryName.getStyleClass().add("toggle-button");
            categoryName.setMinWidth(250);
            categoryName.setToggleGroup(categoryToggleGroup);
            categoryPane.getChildren().add(categoryName);
        }
    }

    private void setPageElementsDueToCurrentFilters() {
        setProductsPane(1);
        setPageNumberPane(1);
    }

    private void setBrandsFilter() {
        RadioButton noBrand = new RadioButton("none");
        noBrand.getStyleClass().remove("radio-button");
        noBrand.getStyleClass().add("toggle-button");
        noBrand.setMinWidth(250);
        noBrand.setToggleGroup(brandToggleGroup);
        brandsPane.getChildren().add(noBrand);
        int brandNo = allBrands.size();
        for (int i = 0; i < 15 && i < brandNo; i++) {
            RadioButton brandName = new RadioButton(allBrands.get(i));
            brandName.getStyleClass().remove("radio-button");
            brandName.getStyleClass().add("toggle-button");
            brandName.setMinWidth(250);
            brandName.setToggleGroup(brandToggleGroup);
            brandsPane.getChildren().add(brandName);
        }
        Button showMore = new Button("show more");
        brandsPane.getChildren().add(showMore);
        showMore.setOnMouseClicked(event -> showMoreBrands());
    }

    private void showMoreBrands() {
        ObservableList<Node> illustratedBrands = brandsPane.getChildren();
        RadioButton lastIllustratedBrand = (RadioButton) illustratedBrands.get(illustratedBrands.size() - 2);
        int indexOfLastBrand = allBrands.indexOf(lastIllustratedBrand.getText());
        int brandNo = allBrands.size();
        for (int i = indexOfLastBrand + 1; i < indexOfLastBrand + 15 && i < brandNo; i++) {
            RadioButton brandName = new RadioButton(allBrands.get(i));
            brandName.getStyleClass().remove("radio-button");
            brandName.getStyleClass().add("toggle-button");
            brandName.setMinWidth(250);
            brandName.setToggleGroup(brandToggleGroup);
            brandsPane.getChildren().add(brandName);
        }
    }

    private void setSellersFilter() {
        RadioButton noSeller = new RadioButton("none");
        noSeller.getStyleClass().remove("radio-button");
        noSeller.getStyleClass().add("toggle-button");
        noSeller.setMinWidth(250);
        noSeller.setToggleGroup(sellerToggleGroup);
        sellersPane.getChildren().add(noSeller);

        ArrayList<String> allUniqueSellers = getAllSellers();
        int uniqueSellersNo = allUniqueSellers.size();
        for (int i = 0; i < 15 && i < uniqueSellersNo; i++) {
            RadioButton sellerUserName = new RadioButton(allUniqueSellers.get(i));
            sellerUserName.getStyleClass().remove("radio-button");
            sellerUserName.getStyleClass().add("toggle-button");
            sellerUserName.setMinWidth(250);
            sellerUserName.setToggleGroup(sellerToggleGroup);
            sellersPane.getChildren().add(sellerUserName);
        }
        Button showMore = new Button("show more");
        sellersPane.getChildren().add(showMore);
        showMore.setOnMouseClicked(event -> showMoreSellers());
    }

    private void showMoreSellers() {
        ArrayList<String> allUniqueSellers = getAllSellers();
        ObservableList<Node> illustratedSellers = sellersPane.getChildren();
        RadioButton lastIllustratedSeller = (RadioButton) illustratedSellers.get(illustratedSellers.size() - 2);
        int indexOfLastSellers = allUniqueSellers.indexOf(lastIllustratedSeller.getText());
        int uniqueSellersNo = allUniqueSellers.size();
        for (int i = indexOfLastSellers + 1; i < indexOfLastSellers + 15 && i < uniqueSellersNo; i++) {
            RadioButton sellerUserName = new RadioButton(allUniqueSellers.get(i));
            sellerUserName.getStyleClass().remove("radio-button");
            sellerUserName.getStyleClass().add("toggle-button");
            sellerUserName.setMinWidth(250);
            sellerUserName.setToggleGroup(sellerToggleGroup);
            sellersPane.getChildren().add(sellerUserName);
        }
    }


    private void updateFilters() throws Exception {

        ArrayList<Product> tempCategoryFilterResult = new ArrayList<>();
        RadioButton selectedCategory = (RadioButton) categoryToggleGroup.getSelectedToggle();
        if (selectedCategory == null || selectedCategory.getText().equals("none")) {
            tempCategoryFilterResult.addAll(allProducts);
        } else {
            CategoryFilter categoryFilter = new CategoryFilter(selectedCategory.getText(), tempCategoryFilterResult);
            categoryFilter.apply(tempCategoryFilterResult, allProducts);
        }

        ArrayList<Product> tempBrandFilterResult = new ArrayList<>();
        RadioButton selectedBrand = (RadioButton) brandToggleGroup.getSelectedToggle();
        if (!(selectedBrand == null || selectedBrand.getText().equals("none"))) {
            BrandFilter brandFilter = new BrandFilter(selectedBrand.getText(), tempCategoryFilterResult);
            brandFilter.apply(tempBrandFilterResult, tempCategoryFilterResult);
        } else {
            tempBrandFilterResult = tempCategoryFilterResult;
        }

        ArrayList<Product> tempAvailabilityFilterResult = new ArrayList<>();
        if (availablesOnly.isSelected()) {
            AvailabilityFilter availabilityFilter = new AvailabilityFilter("availability", tempBrandFilterResult);
            availabilityFilter.apply(tempAvailabilityFilterResult, tempBrandFilterResult);
        } else {
            tempAvailabilityFilterResult = tempBrandFilterResult;
        }

        ArrayList<Product> tempPriceRangeFilterResult = new ArrayList<>();
        double priceLowerBound;
        try {
            priceLowerBound = Double.parseDouble(lowerBound.getText());
        } catch (Exception e) {
            priceLowerBound = 0;
        }
        double priceUpperBound = 0;
        try {
            priceUpperBound = Double.parseDouble(upperBound.getText());
        } catch (Exception e) {
            priceUpperBound = Double.POSITIVE_INFINITY;
        }
        PriceRangeFilter priceRangeFilter = new PriceRangeFilter(priceLowerBound, priceUpperBound, tempAvailabilityFilterResult);
        priceRangeFilter.apply(tempPriceRangeFilterResult, tempAvailabilityFilterResult);

        ArrayList<Product> tempSellerFilterResult = new ArrayList<>();
        RadioButton selectedSeller = (RadioButton) sellerToggleGroup.getSelectedToggle();
        if (!(selectedSeller == null || selectedSeller.getText().equals("none"))) {
            SellerFilter sellerFilter = new SellerFilter(selectedSeller.getText(), tempPriceRangeFilterResult);
            sellerFilter.apply(tempSellerFilterResult, tempPriceRangeFilterResult);
        } else {
            tempSellerFilterResult = tempPriceRangeFilterResult;
        }

        RadioButton selectedSort = (RadioButton) sortToggleGroup.getSelectedToggle();
        if (!(selectedSort == null || selectedSort.getText().equals("none"))) {
            applySelectedSort(selectedSort.getText(), tempSellerFilterResult);
        }

        if (!currentFilterResult.equals(tempSellerFilterResult)) {
            currentFilterResult = tempSellerFilterResult;
            setPageElementsDueToCurrentFilters();
        }
    }

    private void applySelectedSort(String selectedSort, ArrayList<Product> tempSellerFilterResult) {
        if (selectedSort.equals("name(ascending)")) {
            tempSellerFilterResult.sort(new ProductsSort.productSortByNameAscending());
        } else if (selectedSort.equals("name(descending)")) {
            tempSellerFilterResult.sort(new ProductsSort.productSortByNameDescending());
        } else if (selectedSort.equals("visit")) {
            tempSellerFilterResult.sort(new ProductsSort.productSortByView());
        } else if (selectedSort.equals("rate")) {
            tempSellerFilterResult.sort(new ProductsSort.productSortByRate());
        } else if (selectedSort.equals("cheapest")) {
            tempSellerFilterResult.sort(new ProductsSort.productSortByPriceAscendingly());
        } else if (selectedSort.equals("most expensive")) {
            tempSellerFilterResult.sort(new ProductsSort.productSortByPriceDescendingly());
        }
    }

    public void showSearchResult(MouseEvent mouseEvent) {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        Stage searchResult = new Stage();
        searchResult.setTitle("Search Result");

        ArrayList<Product> searchResultProducts = new ArrayList<>();
        ProductNameFilter productNameFilter = new ProductNameFilter(searchText.getText(), allProducts);
        productNameFilter.apply(searchResultProducts, allProducts);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(30, 0, 30, 30));
        vBox.setSpacing(50);
        vBox.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(vBox);

        if (searchResultProducts.isEmpty()) {
            Label notFoundLabel = new Label("no related products found !");
            notFoundLabel.setStyle("-fx-font-size : 20;");
            vBox.getChildren().add(notFoundLabel);
        } else {
            Label foundResults = new Label("related results : ");
            foundResults.setStyle("-fx-font-size : 20;");
            vBox.getChildren().add(foundResults);
            for (Product product : searchResultProducts) {
                VBox productBox = product.createProductBoxForUI();
                vBox.getChildren().add(productBox);
            }
        }

        Scene scene = new Scene(scrollPane, 750, 400);
        searchResult.setScene(scene);
        searchResult.show();
    }

    public void nextAd(MouseEvent mouseEvent) {
        ObservableList<Node> adPaneChildren = adPane.getChildren();
        for (int i = 0; i < adPaneChildren.size(); i++) {
            if (adPaneChildren.get(i) instanceof HBox) {
                adPane.getChildren().remove(adPaneChildren.get(i));
                i--;
            }
        }
        Random random = new Random();
        int allProductsSize = allProducts.size();
        if (allProductsSize != 0) {
            int productIndex = random.nextInt(allProductsSize);
            Product product = allProducts.get(productIndex);

            Image image = new Image(new File(product.getImagePath()).toURI().toString());
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false));
            Background background = new Background(backgroundImage);
            adPaneBG.setBackground(background);

            GaussianBlur gaussianBlur = new GaussianBlur();
            adPaneBG.setEffect(gaussianBlur);

            HBox adProductBox = product.createProductBoxForAdPane();
            adProductBox.setLayoutX(200);
            adPane.getChildren().add(adProductBox);


            nextAdIcon.toFront();
        }

    }

    public void giveButtonStyle(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setCursor(Cursor.HAND);
    }

//    public void goToUserPanel(MouseEvent mouseEvent) throws IOException {
//        Account account = GeneralController.currentUser;
//        if (account instanceof ManagerAccount) {
//            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
//        } else if (account instanceof SellerAccount) {
//            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
//        } else if (account instanceof BuyerAccount) {
//            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
//        } else {
//            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
//            //GraphicMain.audioClip.stop();
//            //LoginSignUpPage.mediaPlayer.play();
//        }
//    }

    public void goToUserPanel(MouseEvent mouseEvent) throws IOException {
        String response = DataRequestBuilder.buildUserTypeRequest();
        if (response.equals("manager")) {
            GraphicMain.graphicMain.goToPage(ManagerPanelController.FXML_PATH, ManagerPanelController.TITLE);
        } else if (response.equals("seller")) {
            GraphicMain.graphicMain.goToPage(SellerPanelPage.FXML_PATH, SellerPanelPage.TITLE);
        } else if (response.equals("buyer")) {
            GraphicMain.graphicMain.goToPage(BuyerPanelController.FXML_PATH, BuyerPanelController.TITLE);
        } else if (response.equals("supporter")) {
            GraphicMain.graphicMain.goToPage(SupporterPanelController.FXML_PATH, SupporterPanelController.TITLE);
        } else if (response.equals("loginNeeded")) {
            GraphicMain.graphicMain.goToPage(LoginSignUpPage.FXML_PATH, LoginSignUpPage.TITLE);
        } else {
            GraphicMain.showInformationAlert("too many requests sent to server, slow down !!");
        }
        //GraphicMain.audioClip.stop();
        //LoginSignUpPage.mediaPlayer.play();
    }

    public void grow(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setFitWidth(imageView.getFitWidth() + imageView.getFitWidth() / 40);
        imageView.setFitHeight(imageView.getFitHeight() + imageView.getFitHeight() / 40);
    }

    public void shrink(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setFitWidth(imageView.getFitWidth() * 40 / 41);
        imageView.setFitHeight(imageView.getFitHeight() * 40 / 41);
    }

    public void back(MouseEvent mouseEvent) {
        //GraphicMain.buttonSound.stop();
        //GraphicMain.buttonSound.play();
        GraphicMain.graphicMain.back();
    }
}