package Controllers;

import Base.TableViews;
import Book.*;
import Database.Bookdb;
import Database.ISBNdb;
import User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StudentsLibrary {

    @FXML
    Button studentsInfoButton;
    @FXML
    HBox hBox;
    Bookdb bookdb = new Bookdb();
    String type;
    User user;
    TableViews table = new TableViews();
    public void studentsInfoButtonClicked() throws IOException {
        Stage stage = (Stage) studentsInfoButton.getScene().getWindow();
        stage.close();
    }
    public void typeOfTable(String type){
        this.type=type;
    }
    public void show(User user){
        this.user=user;

        Bookdb bookdb = new Bookdb();
        bookdb.show();
        table.bookTable(hBox,'R',user, bookdb.observableList);
        System.out.println("here");



    }
    public void sortButtonClick() throws IOException {
        FXMLLoader loader;
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        loader = new FXMLLoader(getClass().getResource("../FXML/sort.fxml"));
        stage.setScene(new Scene(loader.load()));
        SortController controller = loader.getController();
        controller.setHbox(hBox);
        stage.setTitle("Library");
        stage.showAndWait();
    }
}
