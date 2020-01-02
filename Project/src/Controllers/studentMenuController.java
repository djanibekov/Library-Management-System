package Controllers;

import Database.ISBNdb;
import User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class studentMenuController {
    @FXML
    Label firstNameLabel;
    @FXML
    Label lastNameLabel;
    @FXML
    Label studentIDLabel;
    @FXML
    Button viewBooksButton;
    @FXML
    Button viewLibraryButton;
    @FXML
    Button logoutButton;
    @FXML
    Label feeLabel;

    User user;

    public void viewLibraryButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/studentMenu.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));

        StudentsLibrary controller = loader.getController();      //instance of controller to pass information before openning window
        controller.show(user);
        stage.showAndWait();

    }

    public void viewBooksButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/studentInformation.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));

        StudentInformationController controller = loader.getController();      //instance of controller to pass information before openning window
        controller.showOnlyTable(user);
        stage.showAndWait();

    }

    public void logoutButtonClicked() throws IOException {
        Parent root;
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        Stage addStage = new Stage();
        addStage.initModality(Modality.APPLICATION_MODAL);
        root = FXMLLoader.load(getClass().getResource("../FXML/sample.fxml"));

        addStage.setTitle("Login menu");
        addStage.setScene(new Scene(root));
        addStage.show();

    }



    public void inItData(User user){
        this.user=user;
        ISBNdb isbNdb = new ISBNdb();
        System.out.println(user.getID());
        firstNameLabel.setText(user.getName());
        lastNameLabel.setText(user.getSurname());
        studentIDLabel.setText(user.getID());
        feeLabel.setText(String.valueOf(isbNdb.viewUsersBook(user.getID())));
    }


}
