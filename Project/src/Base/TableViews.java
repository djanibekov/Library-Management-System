package Base;

import Book.*;

import Controllers.BookInformationController;
import Controllers.BorrowedDateController;
import Controllers.StudentInformationController;
import Database.Bookdb;
import Database.ISBNdb;
import Database.UserDb;
import User.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
//TableViews: bookTable,librarianTable and studentTable generate TableView and pass it to children of HBox
public class TableViews {
    User user;

    public void bookTable(HBox hBox, char viewBorrowReserve, User user, ObservableList<Book> list) {
        this.user = user;
        TableView<Book> tableOfBook;
        TableColumn<Book, String> title = new TableColumn<>("Title");

        title.setResizable(true);
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        TableColumn<Book, String> author = new TableColumn<>("Author");

        author.setResizable(true);
        author.setCellValueFactory(new PropertyValueFactory<>("Author"));
        TableColumn<Book, String> subject = new TableColumn<>("Subject");

        subject.setResizable(true);
        subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        tableOfBook = new TableView<>();
        tableOfBook.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableOfBook.getColumns().add(title);
        tableOfBook.getColumns().add(author);
        tableOfBook.getColumns().add(subject);
        hBox.getChildren().clear();
        hBox.getChildren().add(tableOfBook);
        HBox.setHgrow(tableOfBook, Priority.ALWAYS);                                                            //          set to observable list all books
        tableOfBook.setItems(list);                                   //          show in table

        tableOfBook.setOnMouseClicked(e -> {                                            //         set Action to row by lambda to pass
            int index = tableOfBook.getSelectionModel().selectedIndexProperty().get();  //         information and open window with bookInformation
            try {
                showBookInformation(list.get(index), viewBorrowReserve, hBox);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    //showBookInformation calls when user select row of table to see it information
    private void showBookInformation(Book book, char viewBorrowReserve, HBox hBox) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/bookInformation.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));

        BookInformationController controller = loader.getController();      //instance of controller to pass information before openning window
        controller.inItData(book,viewBorrowReserve, user);                     //pass information and set to 'choice' chosen ISBN
        if (viewBorrowReserve == 'B') {
            setBorrowedBook(controller.getChoice(stage));
        } else {
            stage.showAndWait();
        }


    }

    //generate librarian table to view all librarians
    public void librarianTable(HBox hBox) {
        TableView<User> tableOfLibrarians;
        TableColumn<User, String> surName = new TableColumn<>("Surname");
        surName.setMinWidth(200);
        surName.setCellValueFactory(new PropertyValueFactory<>("Surname"));

        TableColumn<User, String> name = new TableColumn<>("Name");
        name.setMinWidth(200);
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TableColumn<User, String> author = new TableColumn<>("Author");
        author.setMinWidth(100);
        author.setCellValueFactory(new PropertyValueFactory<>("Author"));
        tableOfLibrarians = new TableView<>();
        tableOfLibrarians.getColumns().add(surName);
        tableOfLibrarians.getColumns().add(name);
        hBox.getChildren().clear();
        hBox.getChildren().add(tableOfLibrarians);
        HBox.setHgrow(tableOfLibrarians, Priority.ALWAYS);
        UserDb userDb = new UserDb();
        userDb.show("l");                                                       //set to observable list all users whose type is 'l'(librarian)
        tableOfLibrarians.setItems(userDb.observableList);                            //show in table

        tableOfLibrarians.setOnMouseClicked(e -> {
            int index = tableOfLibrarians.getSelectionModel().selectedIndexProperty().get();
            System.out.println(index);
            try {
                showUserInformation(userDb.observableList.get(index), index);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    //generate student tables
    public void studentTable(HBox hBox) {
        TableView<User> tableOfStudents;
        TableColumn<User, String> surName = new TableColumn<>("Surname");
        surName.setMinWidth(200);
        surName.setCellValueFactory(new PropertyValueFactory<>("Surname"));

        TableColumn<User, String> name = new TableColumn<>("Name");
        name.setMinWidth(200);
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<User, String> id = new TableColumn<>("ID");
        id.setMinWidth(100);
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));

        tableOfStudents = new TableView<>();

        tableOfStudents.getColumns().add(surName);
        tableOfStudents.getColumns().add(name);
        tableOfStudents.getColumns().add(id);

        hBox.getChildren().clear();
        hBox.getChildren().add(tableOfStudents);
        HBox.setHgrow(tableOfStudents, Priority.ALWAYS);
        UserDb userDb = new UserDb();
        userDb.show("s");                                            //set to observable list all users whose type is 's'(student)
        tableOfStudents.setItems(userDb.observableList);                  //show in table

        tableOfStudents.setOnMouseClicked(e -> {
            int index = tableOfStudents.getSelectionModel().selectedIndexProperty().get();
            System.out.println(index);
            try {
                showUserInformation(userDb.observableList.get(index), index);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    //like showBookInformation show all students all librarian information(information of chosen user)
    private void showUserInformation(User user, int index) throws IOException {
        FXMLLoader loader;
        if(user.getType().equals("s"))
         loader = new FXMLLoader(getClass().getResource("../FXML/StudentInformation.fxml"));
       else
            loader = new FXMLLoader(getClass().getResource("../FXML/librarianInfo.fxml"));
        this.user = user;
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));
        StudentInformationController controller = loader.getController();
        if(user.getType().equals("s"))
        controller.inItData(user);
else
    controller.inItDataForLibrarian(user);
        stage.show();

    }


    public void setBorrowedBook(String ISBN) throws IOException {
        ISBNdb isbndb = new ISBNdb();
        if (ISBN != null) {
            if (isbndb.isFreeIsbn(ISBN)) {
                System.out.println(ISBN);
                System.out.println(user.getID());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/borrowedDate.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(loader.load()));
                BorrowedDateController controller = loader.getController();
                controller.setInformation(ISBN, user.getID(), true);
                stage.showAndWait();
            } else
                AlertStage.alertBox("This book is already borrowed or reserve.");
        }
    }







    public void borrowedBookTable(HBox hBox, boolean all) {
        ISBNdb isbnDb = new ISBNdb();
        TableView<Book> titleTable;
        TableView<BorrowedBook> tableOfBorrowedBook = new TableView<>();
        TableColumn<Book, String> title = new TableColumn<>("Title");
        title.setResizable(true);
        title.setSortable(false);
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        TableColumn<BorrowedBook, String> isbn = new TableColumn<>("ISBN");
        isbn.setResizable(true);
        isbn.setSortable(false);
        isbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        TableColumn<BorrowedBook, String> userID = new TableColumn<>("Student");
        userID.setResizable(true);
        userID.setSortable(false);
        userID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        titleTable = new TableView<>();
        titleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        titleTable.getColumns().add(title);
        tableOfBorrowedBook.getColumns().add(isbn);
        tableOfBorrowedBook.getColumns().add(userID);
        hBox.getChildren().clear();
        Bookdb bookDb = new Bookdb();
        if(all)
        isbnDb.viewUsersBook("all");
        else
         isbnDb.searchWithOverdue();

        tableOfBorrowedBook.setItems(isbnDb.listOfUsersBook);
        tableOfBorrowedBook.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        bookDb.usersBook(isbnDb.listOfUsersBook);
        titleTable.setItems(bookDb.observableList);
        hBox.getChildren().add(titleTable);
        hBox.getChildren().add(tableOfBorrowedBook);
        HBox.setHgrow(tableOfBorrowedBook, Priority.ALWAYS);
        //          show in table
        titleTable.setOnMouseClicked(e -> {                                            //         set Action to row by lambda to pass
            int index = titleTable.getSelectionModel().selectedIndexProperty().get();  //         information and open window with bookInformation
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}

