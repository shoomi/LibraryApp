package formvalidations;

import utils.connection.DBConnector;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidation {

    public static boolean textFieldNotEmpty(TextField text, Label label, String sValidationText) {
        if (text.getText() != null && !text.getText().isEmpty()) {
            label.setText(null);
            return true;
        }
        label.setText(sValidationText);
        System.out.println("IS EMPTY");
        return false;
    }

    public static boolean telephoneIsValid(TextField tel, Label label, String sValidationText) {

        Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
        Matcher matcher = pattern.matcher(tel.getText());
        if (matcher.matches()) {
            label.setText(null);
            return true;
        }
        label.setText(sValidationText);
        return false;

    }

    public static boolean dateIsValid(TextField date, Label label, String sValidationText) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(date.getText());
            label.setText(null);
            return true;
        } catch (ParseException ex) {
            label.setText(sValidationText);
            return false;
        }
    }

    public static boolean loginIsNotBusy(TextField login, Label label, String sValidationText) {

        DBConnector dbConnector = new DBConnector();
        try {
            Statement statement = dbConnector.getConnection().createStatement();
            String query = "SELECT login FROM mylibrary.users where login='" + login.getText() + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                label.setText(sValidationText);
                return false;
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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