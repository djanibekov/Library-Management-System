package Controllers;

import Base.TableViews;
import Database.Bookdb;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianMenuController  implements Initializable {


    @FXML
    private HBox hBox;

    @FXML
    Button logoutButton;

    TableViews view = new TableViews();

    private char menu;

    public void allBooksButtonClicked() {
        menu = 'b';
        Bookdb bookdb = new Bookdb();
        bookdb.show();
        view.bookTable(hBox, 'V', null, bookdb.observableList);
    }

    public void borrowedBooksButtonClicked() {
        view.borrowedBookTable(hBox, true);
    }

    public void addButtonClick() throws Exception {
        if (menu == 'b' || menu == 's') {
            FXMLLoader loader;
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            if (menu == 'b') {
                loader = new FXMLLoader(getClass().getResource("../FXML/addNewBook.fxml"));
                stage.setScene(new Scene(loader.load()));
            } else {
                loader = new FXMLLoader(getClass().getResource("../FXML/addNewUser.fxml"));
                stage.setScene(new Scene(loader.load()));
                AddNewUserController controller = loader.getController();
                controller.setType("s");
            }
            stage.setTitle("Library");
            stage.showAndWait();
        }
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

    public void addBookButtonClick() throws Exception {
        menu = 'b';
        addButtonClick();
    }

    public void addUserButtonClick() throws Exception {
        menu = 's';
        addButtonClick();
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

    public void allStudentButtonClicked() {
        view.studentTable(hBox);
    }


    public void overdueStudentButtonClicked() {
        view.borrowedBookTable(hBox, false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
