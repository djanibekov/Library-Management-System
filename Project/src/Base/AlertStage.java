package Base;

import javafx.scene.control.Alert;
//Alert box show error, while calling function alertBox pass text of error
public class AlertStage {
    static public void alertBox(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has been encountered");
        alert.setContentText(errorText);

        alert.showAndWait();
    }
}
