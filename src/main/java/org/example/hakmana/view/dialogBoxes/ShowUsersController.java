package org.example.hakmana.view.dialogBoxes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.model.DatabaseConnection;
import org.example.hakmana.model.userMngmnt.SystemUser;
import org.example.hakmana.view.component.AddDevButtonController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowUsersController implements Initializable {
    private static final Logger sqlLogger= (Logger) LogManager.getLogger(AddDevButtonController.class);
    private static ShowUsersController insance=null;
    @FXML
    private TableView<SystemUser> tableView;

    @FXML
    private TableColumn<SystemUser, String> usernameColumn;

    @FXML
    private TableColumn<SystemUser, String> emailColumn;

    private DatabaseConnection databaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private List<SystemUser> userList;

    private ShowUsersController(){}

    public static ShowUsersController getInsance() {
        if(insance==null){
            insance=new ShowUsersController();
            return insance;
        }
        return insance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection = DatabaseConnection.getInstance();
        connection = databaseConnection.getConnection();
        userList = new ArrayList<>();

        try {
            // Query to retrieve deviceUser information
            String query = "SELECT * FROM systemUser";
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            while (resultSet.next()) {
                String storedUserName = resultSet.getString("userName");
                String storedEmail = resultSet.getString("email");

                userList.add(new SystemUser(storedUserName, "", "", "", "", storedEmail, ""));
            }
            tableView.getItems().addAll(userList);

        } catch (SQLException e) {
            sqlLogger.error(e.getMessage());
            e.printStackTrace();

        }

    }


}
