package fxTreenipaivakirja;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

import java.util.Optional;


@SuppressWarnings("javadoc")
public class PoistoIkkuna extends Application {
    @Override
    public void start(Stage stage) {
        // BYCODEBEGIN
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Valitse");
        alert.setHeaderText(null);
        alert.setContentText("Poistetaanko harjoituskerta 8.1.21?");

        ButtonType buttonTypeYes = new ButtonType("Kyllä", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Ei", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if ( result.get() == buttonTypeYes ) System.out.println("Talletaan");
        // BYCODEEND
    }

    public static void main(String[] args) { launch(args); 	}
}


