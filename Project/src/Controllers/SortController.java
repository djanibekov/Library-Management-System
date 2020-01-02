package Controllers;

import Base.AlertStage;
import Base.TableViews;
import Book.*;
import Database.Bookdb;
import User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.stream.Collectors;

public class SortController {

    @FXML
    TextField titleTextField;                   // controller to add new book to database
    @FXML
    TextField authorTextField;
    @FXML
    TextField subjectTextField;
    @FXML
    TextField publishDayTextField;
    @FXML
    TextField publishMonthTextField;
    @FXML
    TextField publishYearTextField;
    @FXML
    TextField globalISBN;
    @FXML
    Button sortButton;
    @FXML
    Button freeButton;
    @FXML
    Button borrowButton;
    @FXML
    Button allButton;
    @FXML
    Button reserveButton;
    HBox hBox;
    @FXML
    MenuButton menuButton = new MenuButton();

    ObservableList<Book> list = FXCollections.observableArrayList();

    public void sortButtonClick() throws IOException {
        Book book = new Book(authorTextField.getText() + "   ", titleTextField.getText() + "   ", subjectTextField.getText() + "   ", globalISBN.getText() + " ", " ", publishDayTextField.getText() + "/" + publishMonthTextField.getText() + "/" + publishYearTextField.getText() + ".");
        ISBN isbn = new ISBN();
        Bookdb bookdb = new Bookdb();
        bookdb.show();
        list = bookdb.observableList;
       if(!titleTextField.getText().isEmpty())
        list = list.stream().filter(e -> e.title.startsWith(titleTextField.getText())).collect(Collectors.toCollection(FXCollections::observableArrayList));
        if(!authorTextField.getText().isEmpty())
            list = list.stream().filter(e -> e.author.startsWith(authorTextField.getText())).collect(Collectors.toCollection(FXCollections::observableArrayList));
        if(!subjectTextField.getText().isEmpty())
            list = list.stream().filter(e -> e.subject.startsWith(subjectTextField.getText())).collect(Collectors.toCollection(FXCollections::observableArrayList));
        if(!globalISBN.getText().isEmpty())
            list = list.stream().filter(e -> e.globalISBN.startsWith(globalISBN.getText())).collect(Collectors.toCollection(FXCollections::observableArrayList));
        if(!publishDayTextField.getText().isEmpty()&&!publishMonthTextField.getText().isEmpty()&&!publishYearTextField.getText().isEmpty())
            list = list.stream().filter(e -> e.getPublishDate().startsWith(publishDayTextField.getText()+"/"+publishMonthTextField+"/"+publishYearTextField)).collect(Collectors.toCollection(FXCollections::observableArrayList));
        Stage stage = (Stage) sortButton.getScene().getWindow();
        stage.close();
        TableViews table = new TableViews();
        table.bookTable(hBox, 'v', null, list);

    }

    public void freeButton(){
        menuButton.setText("Free");
    }


    public void borrowedButton(){
        menuButton.setText("Borrowed");
    }


    public void reserveButton(){
        menuButton.setText("Reserved");
    }


    public void allButton(){
        menuButton.setText("All");
    }

    public void setHbox(HBox hBox) {
        this.hBox = hBox;
    }
}
