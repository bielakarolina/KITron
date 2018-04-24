package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertView {
    private Alert.AlertType type = Alert.AlertType.INFORMATION;

    public AlertView(Stage owner, String text){
        Alert.AlertType type  = Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(owner);
        alert.getDialogPane().setContentText(text);
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
                //.ifPresent(response -> System.out.println("The alert was approved"));
    }
}
