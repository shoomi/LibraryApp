package FormValidations;

import UtilClasses.DBConnector;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidadtion {

    public static boolean textFieldNotEmpty(TextField text, Label l, String sVelidationText) {
        if (text.getText() != null && !text.getText().isEmpty()) {
            l.setText(null);
            return true;
        }
        l.setText(sVelidationText);
        System.out.println("IS EMPTY");
        return false;
    }

    public static boolean telephoneIsValid(TextField tel, Label l, String sVelidationText) {

        Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
        Matcher matcher = pattern.matcher(tel.getText());
        if (matcher.matches()) {
            l.setText(null);
            return true;
        }
        l.setText(sVelidationText);
        return false;

    }

    public static boolean dateIsValide(TextField date, Label l, String sVelidationText) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(date.getText());
            l.setText(null);
            return true;
        } catch (ParseException ex) {
            l.setText(sVelidationText);
            return false;
        }
    }

    public static boolean loginIsNotBusy(TextField login, Label l, String sVelidationText) {

        DBConnector dbConnector = new DBConnector();
        try {
            Statement statement = dbConnector.getConnection().createStatement();
            String query = "SELECT login FROM mylibrary.users where login='" + login.getText() + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                l.setText(sVelidationText);
                return false;
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean bookYearIsValid(TextField bookYear, Label l, String sVelidationText) {
        if (bookYear.getText().length() == 4 && bookYear.getText().chars().allMatch(Character::isDigit)) {
            l.setText(null);
            return true;
        }
        l.setText(sVelidationText);
        return false;
    }

    public static boolean numberOfBooksIsValide(TextField numberOfBooks, Label l, String sVelidationText) {
        if (numberOfBooks.getText().chars().allMatch(Character::isDigit) && Integer.parseInt(numberOfBooks.getText()) != 0) {
            l.setText(null);
            return true;
        }
        l.setText(sVelidationText);
        return false;
    }

}
