package Controllers;

import Base.AlertStage;
import Base.TableViews;
import Book.*;
import Database.Bookdb;
import Database.ISBNdb;
import Database.UserDb;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class StudentInformationController {
    @FXML
    Label fullName;
    @FXML
    Label ID;
    @FXML
    Label fee;
    @FXML
    TableColumn<Book, String> titleColumn = new TableColumn<>();
    @FXML
    TableColumn<Book, String> authorColumn = new TableColumn<>();
    @FXML
    TableColumn<BorrowedBook, String> ISBNColumn = new TableColumn<>();
    @FXML
    TableColumn<BorrowedBook, String> borrowedDate = new TableColumn<>();
    @FXML
    TableColumn<BorrowedBook, String> expiredDate = new TableColumn<>();
    @FXML
    HBox hBox;
    @FXML
    TableView<Book> tableBook=new TableView<>();
    @FXML
    TableView<BorrowedBook>tableIsbn=new TableView<>();
    @FXML
    Button addBookButton;
    @FXML
    Button deleteButton;
    @FXML
    VBox vBox;
    @FXML
    Button blockButton;

    private User user;
    boolean addBook = false;

    public void inItData(User user) {
        this.user = user;
        fullName.setText(user.getSurname() + " " + user.getName());
        ID.setText(user.getID());
        fee.setText("");
        Bookdb bookdb = new Bookdb();
        ISBNdb isbn=new ISBNdb();
        fee.setText(String.valueOf(isbn.viewUsersBook(user.getID())));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        borrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        expiredDate.setCellValueFactory(new PropertyValueFactory<>("expiredDate"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        if (user.getBlockStatus().equals("cant")) {
            blockButton.setText("Unblock");
        } else {
            blockButton.setText("Block");
        }

        tableIsbn.setItems(isbn.listOfUsersBook);
        bookdb.usersBook(isbn.listOfUsersBook);
        tableBook.setItems(bookdb.observableList);
    }

    public void showOnlyTable(User user){

        Bookdb bookdb = new Bookdb();
        ISBNdb isbn=new ISBNdb();
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        borrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        expiredDate.setCellValueFactory(new PropertyValueFactory<>("expiredDate"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        isbn.viewUsersBook(user.getID());
        tableIsbn.setItems(isbn.listOfUsersBook);
        bookdb.usersBook(isbn.listOfUsersBook);
        tableBook.setItems(bookdb.observableList);
         vBox.getChildren().clear();
         vBox.getChildren().add(hBox);
    }

    public void inItDataForLibrarian(User user) {
        this.user = user;
        fullName.setText(user.getSurname() + " " + user.getName());
        ID.setText(user.getID());
    }




    private void modify(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../FXML/addNewUser.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene(
                        loader.load()
                )
        );

        AddNewUserController controller =
                loader.getController();
        controller.inItData(user);

        stage.showAndWait();

    }

    public void deleteButtonClick(){
        if(user.isFree()) {
            user.delete();
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.close();
        }
        else
            AlertStage.alertBox("This Student borrow or reserve book. Please delete his books firstly.");
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    public void blockButtonClick() throws SQLException {

        if (user.getBlockStatus().equals("can")) {
            blockButton.setText("Unblock");
            user.setBlockStatus("cant");
        } else {
            blockButton.setText("Block");
            user.setBlockStatus("can");
        }
        System.out.println(user.getBlockStatus());
    }

    public void addBookButtonClick() {
        addBook = !addBook;
        if (addBook) {
            if (user.getBlockStatus().equals("can")) {
                Bookdb bookdb = new Bookdb();
                bookdb.show();
                TableViews tableView = new TableViews();           //borrow book
                tableView.bookTable(hBox, 'B', user, bookdb.observableList);
                addBookButton.setText("Back");
            }

            else
                AlertStage.alertBox("This user blocked");
        }else {
            hBox.getChildren().clear();
            hBox.getChildren().add(tableBook);
            hBox.getChildren().add(tableIsbn);
            addBookButton.setText("Borrow");
        }
    }

    public void getBorrowedBooks(){

    }

    public void returnBook(){
        System.out.println("return");
        int index = tableIsbn.getSelectionModel().selectedIndexProperty().get();
        if(index == -1)
            index = tableBook.getSelectionModel().selectedIndexProperty().get();
        ISBNdb isbn = new ISBNdb();
        isbn.viewUsersBook(user.getID());
        isbn.returnBook(isbn.listOfUsersBook.get(index).getISBN());
    }

    public void modifyButtonClick() throws IOException {
        modify(user);
    }
}
