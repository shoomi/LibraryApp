package library.formvalidations;

import library.Dialogs;
import library.LibWorker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormValidation {

    // here we check each TextField for correct filling and show sValidationText in the label (that is a little bit to the right of the TextField) if something is wrong
    public static boolean isTextFieldEmpty(TextField text, Label label, String sValidationText) {
        if (text.getText() != null && !text.getText().isEmpty()) {
            label.setText(null);
            return false;
        }
        label.setText(sValidationText);
        return true;
    }

    public static boolean isTelephoneValid(TextField tel, Label label, String sValidationText) {

        if (tel.getText().length()==12) {
            return true;
        }
        label.setText(sValidationText);
        return false;
    }

    public static boolean isDateValid(TextField date, Label label, String sValidationText) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        try {
            LocalDate ld = LocalDate.parse(date.getText(), dateFormatter);

            if (ld.getYear() < 1900) {
                Dialogs.showInfoDialog("Error", "It seems you've entered invalid year");
                return false;
            }
            return true;

        } catch (DateTimeParseException ex) {
            label.setText(sValidationText);
            return false;
        }
    }

    public static boolean isLoginBusy(TextField login, Label label, String sValidationText) {

        if (new LibWorker().doesLoginExists(login.getText())) {
            label.setText(sValidationText);
            return true;
        }
        return false;
    }

    public static boolean isBookYearValid(TextField bookYear, Label label, String sValidationText) {
        if (bookYear.getText().length() == 4) {
            label.setText(null);
            return true;
        }
        label.setText(sValidationText);
        return false;
    }


}
