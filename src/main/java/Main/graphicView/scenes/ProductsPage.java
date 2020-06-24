package Main.graphicView.scenes;

import Main.model.Category;
import Main.model.Product;
import Main.model.accounts.SellerAccount;
import Main.model.filters.*;
import Main.model.sorting.ProductsSort;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentFilterResult.addAll(Product.allProducts);
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

    private void setSortPane() {
        RadioButton noSort = new RadioButton("none");
        noSort.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(noSort);
        RadioButton nameAscending = new RadioButton("name(ascending)");
        nameAscending.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(nameAscending);
        RadioButton nameDescending = new RadioButton("name(descending)");
        nameDescending.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(nameDescending);
        RadioButton mostVisited = new RadioButton("visit");
        mostVisited.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(mostVisited);
        RadioButton mostRated = new RadioButton("rate");
        mostRated.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(mostRated);
        RadioButton priceAscending = new RadioButton("cheapest");
        priceAscending.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(priceAscending);
        RadioButton priceDescending = new RadioButton("most expensive");
        priceDescending.setToggleGroup(sortToggleGroup);
        sortPane.getChildren().add(priceDescending);
    }

    private void setProductsPane(int pageNumber) {
        productsPane.getChildren().clear();
        int startingIndex = (pageNumber - 1) * 15;
        int endingIndex = currentFilterResult.size();
        for (int i = startingIndex; i < startingIndex + 15 && i < endingIndex; i++) {
            Product product = currentFilterResult.get(i);
            productsPane.getChildren().add(product.createProductBoxForUI());
        }
    }

    private void setPageNumberPane(int startPageNumber) {
        pageNumberPane.getChildren().clear();
        final int BUTTON_MIN_HEIGHT = 40;

        Button showLessPages = new Button("show less");
        showLessPages.setOnMouseClicked(event -> showLessPages());
        showLessPages.setMinHeight(BUTTON_MIN_HEIGHT);
        pageNumberPane.getChildren().add(showLessPages);

        int pageNumberButtonsNo = (int) Math.ceil(currentFilterResult.size() / 15.0);
        pageNumberButtonsNo = (pageNumberButtonsNo == 0 ? 1 : pageNumberButtonsNo);
        for (int i = startPageNumber; i <= startPageNumber + 12 && i <= pageNumberButtonsNo; i++) {
            Button number = new Button(i + "");
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
        noCategory.setToggleGroup(categoryToggleGroup);
        categoryPane.getChildren().add(noCategory);

        ArrayList<Category> allCategories = Category.getAllCategories();
        int categoryNo = allCategories.size();
        for (int i = 0; i < 15 && i < categoryNo; i++) {
            Category category = allCategories.get(i);
            RadioButton categoryName = new RadioButton(category.getName());
            categoryName.setToggleGroup(categoryToggleGroup);
            categoryPane.getChildren().add(categoryName);
        }
        Button showMore = new Button("show more");
        categoryPane.getChildren().add(showMore);
        showMore.setOnMouseClicked(event -> showMoreCategories());
    }

    private void showMoreCategories() {
        ArrayList<Category> allCategories = Category.getAllCategories();
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
        noBrand.setToggleGroup(brandToggleGroup);
        brandsPane.getChildren().add(noBrand);
        ArrayList<String> allBrands = Product.getAllBrands();
        int brandNo = allBrands.size();
        for (int i = 0; i < 15 && i < brandNo; i++) {
            RadioButton brandName = new RadioButton(allBrands.get(i));
            brandName.setToggleGroup(brandToggleGroup);
            brandsPane.getChildren().add(brandName);
        }
        Button showMore = new Button("show more");
        brandsPane.getChildren().add(showMore);
        showMore.setOnMouseClicked(event -> showMoreBrands());
    }

    private void showMoreBrands() {
        ArrayList<String> allBrands = Product.getAllBrands();
        ObservableList<Node> illustratedBrands = brandsPane.getChildren();
        RadioButton lastIllustratedBrand = (RadioButton) illustratedBrands.get(illustratedBrands.size() - 2);
        int indexOfLastBrand = allBrands.indexOf(lastIllustratedBrand.getText());
        int brandNo = allBrands.size();
        for (int i = indexOfLastBrand + 1; i < indexOfLastBrand + 15 && i < brandNo; i++) {
            RadioButton brandName = new RadioButton(allBrands.get(i));
            brandName.setToggleGroup(brandToggleGroup);
            brandsPane.getChildren().add(brandName);
        }
    }

    private void setSellersFilter() {
        RadioButton noSeller = new RadioButton("none");
        noSeller.setToggleGroup(sellerToggleGroup);
        sellersPane.getChildren().add(noSeller);

        ArrayList<String> allUniqueSellers = SellerAccount.getAllSellers();
        int uniqueSellersNo = allUniqueSellers.size();
        for (int i = 0; i < 15 && i < uniqueSellersNo; i++) {
            RadioButton sellerUserName = new RadioButton(allUniqueSellers.get(i));
            sellerUserName.setToggleGroup(sellerToggleGroup);
            sellersPane.getChildren().add(sellerUserName);
        }
        Button showMore = new Button("show more");
        sellersPane.getChildren().add(showMore);
        showMore.setOnMouseClicked(event -> showMoreSellers());
    }

    private void showMoreSellers() {
        ArrayList<String> allUniqueSellers = SellerAccount.getAllSellers();
        ObservableList<Node> illustratedSellers = sellersPane.getChildren();
        RadioButton lastIllustratedSeller = (RadioButton) illustratedSellers.get(illustratedSellers.size() - 2);
        int indexOfLastSellers = allUniqueSellers.indexOf(lastIllustratedSeller.getText());
        int uniqueSellersNo = allUniqueSellers.size();
        for (int i = indexOfLastSellers + 1; i < indexOfLastSellers + 15 && i < uniqueSellersNo; i++) {
            RadioButton sellerUserName = new RadioButton(allUniqueSellers.get(i));
            sellerUserName.setToggleGroup(sellerToggleGroup);
            sellersPane.getChildren().add(sellerUserName);
        }
    }


    private void updateFilters() throws Exception {

        ArrayList<Product> tempCategoryFilterResult = new ArrayList<>();
        RadioButton selectedCategory = (RadioButton) categoryToggleGroup.getSelectedToggle();
        if (selectedCategory == null || selectedCategory.getText().equals("none")) {
            tempCategoryFilterResult.addAll(Product.allProducts);
        } else {
            CategoryFilter categoryFilter = new CategoryFilter(selectedCategory.getText(), tempCategoryFilterResult);
            categoryFilter.apply(tempCategoryFilterResult, Product.allProducts);
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

        ArrayList<Product> tempSortResult = new ArrayList<>();
        RadioButton selectedSort = (RadioButton) sortToggleGroup.getSelectedToggle();
        if (!(selectedSort == null || selectedSort.getText().equals("none"))) {
            applySelectedSort(selectedSort.getText(),tempSellerFilterResult,currentFilterResult);
        } else {
            tempSortResult = tempSellerFilterResult;
        }

        if(!currentFilterResult.equals(tempSortResult)){
            currentFilterResult = tempSortResult;
            setPageElementsDueToCurrentFilters();
        }

    }

    private void applySelectedSort(String selectedSort, ArrayList<Product> tempSellerFilterResult, ArrayList<Product> currentFilterResult) {
        switch (selectedSort){
            case "name(ascending)":
                tempSellerFilterResult.sort(new ProductsSort.productSortByNameAscending());
                currentFilterResult.sort(new ProductsSort.productSortByNameAscending());
            case "name(descending)":
                tempSellerFilterResult.sort(new ProductsSort.productSortByNameDescending());
                currentFilterResult.sort(new ProductsSort.productSortByNameDescending());
            case "visit" :
                tempSellerFilterResult.sort(new ProductsSort.productSortByView());
                currentFilterResult.sort(new ProductsSort.productSortByView());
            case "rate":
                tempSellerFilterResult.sort(new ProductsSort.productSortByRate());
                currentFilterResult.sort(new ProductsSort.productSortByRate());
            case "cheapest":
                tempSellerFilterResult.sort(new ProductsSort.productSortByPriceAscendingly());
                currentFilterResult.sort(new ProductsSort.productSortByPriceAscendingly());
            case "most expensive":
                tempSellerFilterResult.sort(new ProductsSort.productSortByPriceDescendingly());
                currentFilterResult.sort(new ProductsSort.productSortByPriceDescendingly());
        }
    }

    public void showSearchResult(MouseEvent mouseEvent) {
        Stage searchResult = new Stage();
        searchResult.setTitle("Search Result");

        //TODO : search process could be better not exactly the same name
        ArrayList<Product> searchResultProducts = new ArrayList<>();
        ProductNameFilter productNameFilter = new ProductNameFilter(searchText.getText(), Product.allProducts);
        productNameFilter.apply(searchResultProducts, Product.allProducts);

        VBox vBox = new VBox();
        ScrollPane scrollPane = new ScrollPane(vBox);

        if (searchResultProducts.isEmpty()) {
            Label notFoundLabel = new Label("no related products found !");
            vBox.getChildren().add(notFoundLabel);
        } else {
            Label foundResults = new Label("related results : ");
            vBox.getChildren().add(foundResults);
            for (Product product : searchResultProducts) {
                VBox productBox = product.createProductBoxForUI();
                vBox.getChildren().add(productBox);
            }
        }

        Scene scene = new Scene(scrollPane);
        searchResult.setScene(scene);
        searchResult.show();
    }

    public void nextAd(MouseEvent mouseEvent) {
        ObservableList<Node> adPaneChildren = adPane.getChildren();
        for (int i = 0; i<adPaneChildren.size();i++) {
            if(adPaneChildren.get(i) instanceof HBox){
                adPane.getChildren().remove(adPaneChildren.get(i));
                i--;
            }
        }
        Random random = new Random();
        int allProductsSize = Product.allProducts.size();
        int productIndex = random.nextInt(allProductsSize);
        Product product = Product.allProducts.get(productIndex);

        //TODO : using image path doesn't work :(
        Image image = new Image(new File("src/main/java/Main/graphicView/resources/images/product.png").toURI().toString());
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(1.0,1.0,true,true,false,false));
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