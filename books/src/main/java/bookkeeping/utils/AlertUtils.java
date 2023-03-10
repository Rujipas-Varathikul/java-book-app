package bookkeeping.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {

    public static void showErrorMessage(String message, String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.show();
    }

    public static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.show();
    }

    public static boolean confirmMessage(String message, String title, String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.setHeaderText(header);

        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                return true;
            } else {
                return false;
            }

    }

    public static void showBookDescription(String title, String isbn10,  String isbn13, String ddn, String language, String synopsis) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Book Description");
        alert.setHeaderText(title);
        String message = "ISBN10: " + isbn10 + "\n" +
                        "ISBN13: " + isbn13 + "\n" +
                        "DDN: " + ddn + "\n" +
                        "Language: " + language + "\n" + "\n" +
                        synopsis;
        alert.setContentText(message);

        alert.showAndWait();
    }
}
