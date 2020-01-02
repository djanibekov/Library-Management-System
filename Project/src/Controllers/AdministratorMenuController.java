package Controllers;

import Base.TableViews;
import Book.*;


import Database.Bookdb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdministratorMenuController implements Initializable {
    ObservableList<Book> books = FXCollections.observableArrayList();
    @FXML
    Button studentButton;
    @FXML
    Button librarianButton;
    @FXML
    Button booksButton;
    @FXML
    Label label;
    @FXML
    HBox hBox;
    @FXML
    Button addButton;
    @FXML
            Button logoutButton;
    TableViews view = new TableViews();
    char menu;

    public void booksButtonClicked() {
        menu = 'b';
        Bookdb book = new Bookdb();
        book.show();
        view.bookTable(hBox, 'V', null, book.observableList);
    }


    public void librarianButtonClicked() {
        menu = 'l';
        view.librarianTable(hBox);
    }

    public void studentButtonClicked() {
        menu = 's';
        view.studentTable(hBox);

    }

    public void addButtonClick() throws Exception {
        FXMLLoader loader;
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        if (menu == 'b' || menu == 's' || menu == 'l') {
            if (menu == 'b') {
                loader = new FXMLLoader(getClass().getResource("../FXML/addNewBook.fxml"));
                stage.setScene(new Scene(loader.load()));
                stage.showAndWait();
                booksButtonClicked();
            } else if (menu == 'l') {
                loader = new FXMLLoader(getClass().getResource("../FXML/addNewUser.fxml"));
                stage.setScene(new Scene(loader.load()));
                AddNewUserController controller = loader.getController();
                controller.setType("l");
                stage.showAndWait();
                librarianButtonClicked();
            } else {
                loader =  new FXMLLoader(getClass().getResource("../FXML/addNewUser.fxml"));
                stage.setScene(new Scene(loader.load()));
                AddNewUserController controller = loader.getController();
                controller.setType("s");
                stage.showAndWait();
                studentButtonClicked();
            }


        }
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

        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}
