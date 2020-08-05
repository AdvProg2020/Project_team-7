package Main.client.graphicView.scenes;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AboutPage implements Initializable {
    public static final String FXML_PATH = "src/main/sceneResources/about.fxml";
    public static final String TITLE = "ABOUT";
    public Label aboutLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> teamMembers = new ArrayList<>();
        teamMembers.add("Fatemeh Asgari");
        teamMembers.add("Maryam Sadat Razavi");
        teamMembers.add("Mahta Fetrat");
        teamMembers.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        StringBuffer about = new StringBuffer();
        about.append("AP Project spring 2020\n" +
                "Buy Up, a simulated online shopping app\n\n"+
                "team members (in Alphabetical order)\n");
        for (String teamMember : teamMembers) {
            about.append(teamMember + "\n");
        }

        aboutLabel.setMinWidth(800);
        aboutLabel.setMinHeight(300);
        aboutLabel.setWrapText(true);

        aboutLabel.setText(about.toString());
    }
}
