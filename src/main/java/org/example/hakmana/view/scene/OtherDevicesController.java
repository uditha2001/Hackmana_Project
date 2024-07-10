package org.example.hakmana.view.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.hakmana.model.otherDevices.OtherDevices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.example.hakmana.view.component.DeviceCategoryCardController;
import org.example.hakmana.view.component.PathFinderController;
import org.example.hakmana.view.dialogBoxes.AddNewDevCatController;
import org.example.hakmana.view.dialogBoxes.CreateAccountController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.model.otherDevices.OtherDevices;
import org.example.hakmana.view.component.AddDevButtonController;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class OtherDevicesController implements Initializable {
    private static OtherDevicesController instance = null;
    public OtherDevices otherDevicesDb;
    private static javafx.scene.control.ScrollPane dashboardBodyScrollpaneD;
    private PathFinderController dashboardPathFinderControllerD;
    @FXML
    public Button addNew;
    @FXML
    public Button ViewMore;

    @FXML
    public TableView<OtherDevices> otherDeviceTblView;
    @FXML
    public TableColumn<OtherDevices, Integer> num;
    @FXML
    public TableColumn<OtherDevices, String> deviceNameClmn;
    @FXML
    public TableColumn<OtherDevices, Integer> totalClmn;
    @FXML
    public TableColumn<OtherDevices, Integer> activeClmn;
    @FXML
    public TableColumn<OtherDevices, Integer> inactiveClmn;
    @FXML
    public TableColumn<OtherDevices, Integer> repairClmn;
    private String devName;

    private OtherDevicesController() {
    }

    public static OtherDevicesController getInstance() {
        if (instance == null) {
            instance = new OtherDevicesController();
            return instance;
        }
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        otherDevicesDb = new OtherDevices();
        num.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("num"));
        deviceNameClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, String>("dev"));
        activeClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("numActiveDev"));
        inactiveClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("numInactiveDev"));
        repairClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("numRepairingDev"));
        totalClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("totalDev"));

        addTblRow();
        addTableSelectionListener();
    }

    public void addTblRow() {
        new Thread(() -> otherDeviceTblView.setItems(otherDevicesDb.getObservableOtherDevices())).start();
    }

    public PathFinderController getDashboardPathFinderControllerD() {
        return dashboardPathFinderControllerD;
    }
    public void setDashboardPathFinderControllerD(PathFinderController dashboardPathFinderControllerD) {
        this.dashboardPathFinderControllerD = dashboardPathFinderControllerD;
    }
    public static ScrollPane getDashboardBodyScrollpaneD() {
        return dashboardBodyScrollpaneD;
    }
    public static void setDashboardBodyScrollpaneD(ScrollPane dashboardBodyScrollpaneD) {
        OtherDevicesController.dashboardBodyScrollpaneD = dashboardBodyScrollpaneD;
    }

    public String getDevName() {
        return devName;
    }

    private void addTableSelectionListener() {
        otherDeviceTblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OtherDevices>() {
            @Override
            public void changed(ObservableValue<? extends OtherDevices> observable, OtherDevices oldValue, OtherDevices newValue) {
                if (newValue != null) {
                    devName = newValue.getDev();
                    System.out.println("Selected device: " + devName);
                }
            }
        });
    }

    public void ViewMoreButtonOnAction(ActionEvent actionEvent) {
        DeviceMngmntSmmryScene.setDbSelector(devName);
        DeviceCategoryCardController deviceCategoryCardController=new DeviceCategoryCardController();
        deviceCategoryCardController.setDevName(devName);
        //  DeviceCategoryCardController.setDashboardBodyScrollpaneD(bodyScrollPaneD);
        deviceCategoryCardController.setDashboardPathFinderControllerD(getDashboardPathFinderControllerD());
        deviceCategoryCardController.callDeviceInfo();
    }

    public void addNewButtonOnAction(ActionEvent actionEvent) throws IOException{
        FXMLLoader addNewDevfxmlLoad = new FXMLLoader();
        addNewDevfxmlLoad.setLocation(org.example.hakmana.view.dialogBoxes.AddNewDevCatController.class.getResource("AddNewDevCat.fxml"));
        addNewDevfxmlLoad.setController(AddNewDevCatController.getInstance());
        DialogPane addDevDialogPane = addNewDevfxmlLoad.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addDevDialogPane);
        dialog.setTitle("Add New Device Category");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
    }
}