package org.example.hakmana.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardCardController extends ScrollPane implements Initializable {
    @FXML
    private Label topLabel;
    private String text;

    //methods for component initialize and constructor for component load----------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
    public DashboardCardController() {
        super();
        String filePath = "src/main/resources/org/example/hakmana/Component/dashboardCard.fxml";
        FXMLLoader fxmlDashboardCardLoader = null;
        try {
            fxmlDashboardCardLoader = new FXMLLoader(new File(filePath).toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        fxmlDashboardCardLoader.setController(this);
        fxmlDashboardCardLoader.setRoot(this);


        try {
            fxmlDashboardCardLoader.load();

        }
        catch(IOException navPnlException){
            throw new RuntimeException(navPnlException);
        }
    }


    //getters and setters for label set----------------------------------------------------
    public void setText(String text) {
        this.text = text;
        topLabel.setText(this.text);
    }
    public String getText() {
        return topLabel.getText();
    }

}