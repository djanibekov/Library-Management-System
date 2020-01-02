package Controllers;

import Book.BorrowedBook;
import Book.ISBN;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;

public class BorrowedDateController {

    @FXML
    TextField expiredDate;
    @FXML
    Button okButton;
    @FXML
    TextField feePerDay;
    Date date = new Date();
    String isbn;
    String userID;

    public void okButtonClick(){
        System.out.println("date:"+String.valueOf(date.getTime()/86400000));
        BorrowedBook book = new BorrowedBook(String.valueOf(date.getTime()/86400000+ Integer.parseInt(expiredDate.getText())),String.valueOf(date.getTime()/86400000),userID,isbn, Integer.parseInt(feePerDay.getText()));
        book.save();
        System.out.println(book);
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void setInformation(String isbn, String userID, boolean reserveORborrow) {
        this.isbn = isbn;
        this.userID = userID;
    }
}
