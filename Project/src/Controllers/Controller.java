package Controllers;
import Base.AlertStage;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button OkButton;
    @FXML
    private RadioButton administratorRadioButton;
    @FXML
    private RadioButton librarianRadioButton;
    @FXML
    private RadioButton studentRadioButton;

    private User user = new User();


    @FXML
    public void okButtonClick() throws Exception{
        Stage stage=new Stage();
        Parent root;
        Scene scene;

        if(administratorRadioButton.isSelected()){
            user=User.getUser("a",loginTextField.getText(),passwordTextField.getText());
            if(user != null) {
                user.Login();
                stage = (Stage) OkButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("../FXML/administratorMenu.fxml"));
                scene = new Scene(root);
                stage.setScene(scene);
            }
            else{
                AlertStage.alertBox("Wrong Password or Login");
                stage = (Stage) OkButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("../FXML/sample.fxml"));
                scene = new Scene(root);
                stage.setScene(scene);
            }
        }

        else if(librarianRadioButton.isSelected()) {
            user=User.getUser("l",loginTextField.getText(), passwordTextField.getText());
            if (user!=null) {
                user.Login();
                stage = (Stage) OkButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("../FXML/librarianMenu.fxml"));
                scene = new Scene(root);
                stage.setScene(scene);
            }
            else {
                AlertStage.alertBox("Wrong Password or Login");

                stage = (Stage) OkButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("../FXML/sample.fxml"));
                scene = new Scene(root);
                stage.setScene(scene);
            }
        }

        else if(studentRadioButton.isSelected()) {
            user=User.getUser("s",loginTextField.getText(), passwordTextField.getText());
            if (user!=null) {


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/StudentMenu2.fxml"));
                    stage = (Stage) OkButton.getScene().getWindow();
                    scene = new Scene(loader.load());
                    studentMenuController controller = loader.getController();
                    stage.setScene(scene);
                    System.out.println(user.getID());
                    controller.inItData(user);

                    user.Login();
            }
            else{
                AlertStage.alertBox("Wrong Password or Login");
                stage = (Stage) OkButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("../FXML/sample.fxml"));
                scene = new Scene(root);
                stage.setScene(scene);
            }
        }

        else{
            stage = (Stage) OkButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../FXML/sample.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
        }

        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}






