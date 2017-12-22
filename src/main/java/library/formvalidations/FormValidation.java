package library.formvalidations;

import library.Dialogs;
import library.LibWorker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidation {

    public static boolean textFieldNotEmpty(TextField text, Label label, String sValidationText) {
        if (text.getText() != null && !text.getText().isEmpty()) {
            label.setText(null);
            return true;
        }
        label.setText(sValidationText);
        return false;
    }

    public static boolean telephoneIsValid(TextField tel, Label label, String sValidationText) {

        Pattern pattern = Pattern.compile("\\d{10}|\\d{12}|\\d{13}|\\d{3}-\\d{7}|\\d{3}-\\d{4}-\\d{3}|\\d{3}-\\d{3}-\\d{4}|\\+\\d{12}|\\+\\d{3}-\\d{9}|\\+\\d{3}-\\d{3}-\\d{3}-\\d{3}|\\+\\d{2}-\\d{3}-\\d{4}-\\d{3}|\\+\\d{3}-\\d{2}-\\d{3}-\\d{4}|\\d{3} \\d{7}|\\d{3} \\d{4} \\d{3}|\\d{3} \\d{3} \\d{4}|\\+\\d{3} \\d{9}|\\+\\d{3} \\d{3} \\d{3} \\d{3}|\\+\\d{2} \\d{3} \\d{4} \\d{3}|\\+\\d{3} \\d{2} \\d{3} \\d{4}");

        Matcher matcher = pattern.matcher(tel.getText());

        if (matcher.matches()) {
            label.setText(null);
            tel.setText(tel.getText().replaceAll("[\\-\\+\\ ]", ""));
            return true;
        }
        label.setText(sValidationText);
        return false;
    }

    public static boolean dateIsValid(TextField date, Label label, String sValidationText) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        try {
            LocalDate ld = LocalDate.parse(date.getText(), dateFormatter);

            if (ld.getYear() < 1900) {
                Dialogs.showInfoDialog("Error","It seems you've entered invalid year");
                return false;
            }
            return true;

        } catch (DateTimeParseException ex) {
            label.setText(sValidationText);
            return false;
        }
    }

    public static boolean loginIsNotBusy(TextField login, Label label, String sValidationText) {

        if (new LibWorker().loginExists(login.getText())) {
            label.setText(sValidationText);
            return false;
        }


        return true;
    }

    public static boolean bookYearIsValid(TextField bookYear, Label label, String sValidationText) {
        if (bookYear.getText().length() == 4 && bookYear.getText().chars().allMatch(Character::isDigit)) {
            label.setText(null);
            return true;
        }
        label.setText(sValidationText);
        return false;
    }

    public static boolean numberOfBooksIsValid(TextField numberOfBooks, Label label, String sValidationText) {
        if (numberOfBooks.getText().chars().allMatch(Character::isDigit) && Integer.parseInt(numberOfBooks.getText()) > 0) {
            label.setText(null);
            return true;
        }
        label.setText(sValidationText);
        return false;
    }

}
