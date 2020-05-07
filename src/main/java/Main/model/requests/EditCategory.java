package Main.model.requests;

import Main.model.Category;
import java.util.ArrayList;

public class EditCategory {

    private Category category;
    private String name;
    private ArrayList<String> specialFeatures = new ArrayList<String>();
    private ArrayList<String> products = new ArrayList<String>();

    public EditCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProduct(String productID){

    }

    public void addSpecialFeature(){

    }

    public void acceptRequest() {

    }
}
