package Controllers;

import Base.AlertStage;
import Book.*;
import Book.ISBN;
import Database.Bookdb;
import Database.ISBNdb;
import User.User;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class BookInformationController {
    @FXML
    Label titleLabel;
    @FXML
    Label subjectLabel;
    @FXML
    Label publishDateLabel;
    @FXML
    Label amountLabel;
    @FXML
    Label authorLabel;
    @FXML
    Label leftLabel;
    @FXML
    TableView<ISBN> tableOfISBN;
    @FXML
    TableColumn<ISBN, String> ISBNColumn;
    @FXML
    TableColumn<ISBN, String> statusColumn;
    @FXML
    Button modifyButton;
    Book book;
    @FXML
    Button deleteButton;
    ISBNdb ISBN = new ISBNdb();
    @FXML
    HBox hBoxWithButtons;
    User user;
    AtomicReference<String> choice = new AtomicReference<>(new String());
    public void inItData(Book book, char viewBorrowReserve, User user) {
        this.book = book;
        this.user=user;
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        titleLabel.setText(book.getTitle());
        subjectLabel.setText(book.getSubject());
        publishDateLabel.setText(book.getPublishDate());
        amountLabel.setText(book.getAmount());
        authorLabel.setText(book.getAuthor());
        System.out.println(book.getId());
        ISBN.show(book.getId());
        System.out.println(ISBN.observableList);
        tableOfISBN.setItems(ISBN.observableList);
        if (viewBorrowReserve == 'B')
            hBoxWithButtons.getChildren().clear();
        else if (viewBorrowReserve == 'R') {
            modifyButton.setText("Reserve");
            deleteButton.setText("Image");
            choice.set(null);
            tableOfISBN.setOnMouseClicked(e -> {
                System.out.println("hereeeeewwewewewewew");
                choice.set(ISBNColumn.getCellData(tableOfISBN.getSelectionModel().selectedIndexProperty().get()));
            });
        }
    }


    public void modifyButtonClicked() throws IOException {
        modify(book);
    }


    private void modify(Book book) throws IOException {
        if (modifyButton.getText().equals("Modify")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/addNewBook.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));

            AddNewBookController controller = loader.getController();
            controller.inItData(book);

            stage.showAndWait();
        } else{
            String ISBN = choice.get();
            ISBNdb isbNdb = new ISBNdb();
            if(isbNdb.isFreeIsbn(ISBN)) {
                BorrowedBook borrowedBook = new BorrowedBook(user.getID(), ISBN);
                borrowedBook.reserve();
            }
            else{
                AlertStage.alertBox("Book borrowed or reserved");
            }
        }

    }

    public String getChoice(final Stage stage) {
        AtomicReference<String> choice = new AtomicReference<>(new String());
        choice.set(null);
        tableOfISBN.setOnMouseClicked(e -> {

            choice.set(ISBNColumn.getCellData(tableOfISBN.getSelectionModel().selectedIndexProperty().get()));
            stage.close();
        });
        stage.showAndWait();
        return choice.get();
    }



    public void deleteButtonClick() throws IOException {
        Bookdb book = new Bookdb();
        if (deleteButton.getText().equals("Delete")) {
            if (book.isFree(this.book)) {
                book.delete(this.book);
                Stage stage = (Stage) deleteButton.getScene().getWindow();
                stage.close();
            } else {
                AlertStage.alertBox("This book is reserved or borrowed. You can not delete it");
            }
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/netWork.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));

            ImgController controller = loader.getController();      //instance of controller to pass information before openning window
            controller.img(this.book.getId());
            stage.showAndWait();
        }
    }
}
