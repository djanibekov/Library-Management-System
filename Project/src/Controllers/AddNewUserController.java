package Controllers;

import Base.AlertStage;
import Database.UserDb;
import User.User;
import javafx.fxml.FXML;


import javafx.scene.control.*;
import javafx.stage.Stage;


public class AddNewUserController {
    @FXML
    TextField IDTextField;
    @FXML
    TextField nameTextField;
    @FXML
    TextField surnameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    Button okButton;
    @FXML
    Label idLabel;
    String type = "";

    public boolean validation() {
        return  IDTextField.getText() != null &&
                passwordTextField.getText().length() > 7 && passwordTextField.getText().length() < 21 &&
                nameTextField.getText() != null && surnameTextField.getText() != null;
    }


    public void okButtonClick() {
        UserDb userdb = new UserDb();
        if (!userdb.isLoginExist(IDTextField.getText())) {
            if (type.equals("s") && validation() || type.equals("l")) {
                User user = new User();
                user.setID(IDTextField.getText());
                user.setName(nameTextField.getText());
                user.setPassword(passwordTextField.getText());
                user.setSurname(surnameTextField.getText());
                user.setType(type);
                user.save();
                Stage stage = (Stage) okButton.getScene().getWindow();
                stage.close();
            } else {
                AlertStage.alertBox("Invalid data");
            }
        } else
            AlertStage.alertBox("Login exist. Please create new!");

    }

    public void inItData(User user){
        IDTextField.setText(user.getID());
        nameTextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
    }

    public void setType(String type) {
        this.type = type;
        if(type.equals("l"))
            idLabel.setText("Login");

    }
}
