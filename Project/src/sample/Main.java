package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../FXML/sample.fxml"));
        primaryStage.setTitle("Library");
        primaryStage.setScene(new Scene(root, 444, 235));
        primaryStage.show();
        Stage stage = new Stage();
        Parent help = FXMLLoader.load(getClass().getResource("../FXML/help.fxml"));
        stage.setTitle("Library");
        stage.setScene(new Scene(help, 1000, 500));
        stage.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
