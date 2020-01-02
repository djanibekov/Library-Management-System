package Controllers;
import Base.AlertStage;
import Book.Book;
import Book.ISBN;
import Database.Bookdb;
import Database.ISBNdb;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;



public class AddNewBookController {

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
    TextField numberTextField;
    @FXML
    TextField globalISBN;
    @FXML
    Button okButton;
    @FXML
    VBox ISBNScrollBar;
    @FXML
    HBox numberHbox;
    @FXML
    HBox ScrollHbox;
    @FXML
    HBox globalIsbnHbox;
    boolean modify = false;
    Book book;
    LinkedList<TextField> list = new LinkedList<>();
    boolean correct = true;

    public boolean isDataCorrect() {

        System.out.println("here");
        return Integer.parseInt(publishDayTextField.getText()) > 0 && Integer.parseInt(publishDayTextField.getText()) <= 31 &&
                Integer.parseInt(publishMonthTextField.getText()) > 0 && Integer.parseInt(publishMonthTextField.getText()) <= 12 &&
                Integer.parseInt(publishYearTextField.getText()) > 0 && titleTextField != null && authorTextField != null &&
                subjectTextField != null && Integer.parseInt(numberTextField.getText()) > 0 && !list.isEmpty() && !isListEmpty() && globalISBN.getText() != null;

    }

    public boolean isModifiedDataCorrect() {

        System.out.println("here!!!!!!!!!!!");
        return Integer.parseInt(publishDayTextField.getText()) > 0 && Integer.parseInt(publishDayTextField.getText()) <= 31 &&
                Integer.parseInt(publishMonthTextField.getText()) > 0 && Integer.parseInt(publishMonthTextField.getText()) <= 12 &&
                Integer.parseInt(publishYearTextField.getText()) > 0 && titleTextField != null && authorTextField != null &&
                subjectTextField != null;//modify

    }

    public boolean isListEmpty() {
        for (int i = 0; i < Integer.parseInt(numberTextField.getText()); i++)
            if (list.get(i).getText() == null || list.get(i).getText().isEmpty())
                return true;                                                      //checks is textFields in list empty

        return false;

    }

    public void numberISBN() {

        ISBNScrollBar.getChildren().clear();
        for (int i = 0; i < Integer.parseInt(numberTextField.getText()); i++) {
            TextField textField = new TextField();                                //generate textFields for setting ISBN
            list.add(textField);                                                  //if amount of book 3 there will be 3 textFields for 3 ISBN
            ISBNScrollBar.getChildren().add(list.get(i));
        }

    }

    public void handleOkButton() throws SQLException {

        System.out.println("here!!!!!!!!!!!");
        Bookdb bookdb = new Bookdb();
        if (isModifiedDataCorrect() && modify) {
            System.out.println("here!!!!!!!!!!!");
            book.setTitle(titleTextField.getText());
            book.setAuthor(authorTextField.getText());
            book.setPublishDate(publishDayTextField.getText(), publishMonthTextField.getText(), publishYearTextField.getText());
            book.setSubject(subjectTextField.getText());
            System.out.println("here!!!!!!!!!!!");
            bookdb.update(book);
        } else if (isDataCorrect() && !modify) {
            if(checkISBN()) {
                if(!bookdb.isISBNExist(globalISBN.getText())) {
                    numberISBN();
                    Book book = new Book();
                    ISBN isbn = new ISBN();
                    book.setTitle(titleTextField.getText());
                    book.setAuthor(authorTextField.getText());                                      //   if data correct saves new book in database
                    book.setSubject(subjectTextField.getText());                                    //   first creates instance of book set there all information
                    book.setAmount(numberTextField.getText());                                      //   from textFields and then saves its instance
                    book.setPublishDate(publishDayTextField.getText(), publishMonthTextField.getText(), publishYearTextField.getText());
                    book.setId(globalISBN.getText());
                    book.save();
                    for (int i = 0; i < Integer.parseInt(numberTextField.getText()); i++) {
                        isbn.setISBN(list.get(i).getText());
                        isbn.save(globalISBN.getText());                                                               //   saves all ISBn to database seperatly
                    }
                    Stage stage = (Stage) okButton.getScene().getWindow();
                    stage.close();
                }
                else
                    AlertStage.alertBox("Global ISBN exist. Write new one");
            }
            else
                AlertStage.alertBox("Local Isbn exist. Create new one please.");
        } else
            AlertStage.alertBox("Invalid data");
    }

    public void inItData(Book book) {
        modify = true;
        this.book = book;
        System.out.println(book.getTitle());
        StringBuilder string = new StringBuilder();
        titleTextField.setText(book.getTitle());
        authorTextField.setText(book.getAuthor());                                           // gets information from caller
        subjectTextField.setText(book.getSubject());                                         // and sets to textFields
        numberTextField.setText(book.getAmount());                                           //function calls when you want modify information about book
        publishDayTextField.setText(String.valueOf(string.append(book.getDay())));
        string.delete(0, string.length());
        publishMonthTextField.setText(String.valueOf(string.append(book.getMonth())));
        string.delete(0, string.length());
        publishYearTextField.setText(String.valueOf(string.append(book.getYear())));
        numberHbox.getChildren().clear();
        ScrollHbox.getChildren().clear();
        globalIsbnHbox.getChildren().clear();
    }

    public boolean checkISBN() {
        ISBNdb isbNdb = new ISBNdb();
        for (int i = 0; i < Integer.parseInt(numberTextField.getText()); i++)
            if (isbNdb.isISBNExist(list.get(i).getText()))
                return false;
        return true;
    }
}



