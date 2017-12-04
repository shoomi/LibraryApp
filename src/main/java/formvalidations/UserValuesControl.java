package formvalidations;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class UserValuesControl {

    public boolean checkValues(TextField loginField, TextField firstNameField, TextField lastNameField, TextField telephoneField, TextField dateField, Label loginLabel, Label firsNameLabel, Label lastNameLabel, Label telLabel, Label dateLabel) {

        boolean loginIsNotBusy = false;
        boolean telephoneFieldIsValid = false;
        boolean dateIsValid = false;

        boolean loginFieldIsNotEmpty = FormValidation.textFieldNotEmpty(loginField, loginLabel, "enter login");
        boolean firstNameFieldIsNotEmpty = FormValidation.textFieldNotEmpty(firstNameField, firsNameLabel, "enter Firs Name");
        boolean lastNameFieldIsNotEmpty = FormValidation.textFieldNotEmpty(lastNameField, lastNameLabel, "enter Last Name");
        boolean telephoneFieldIsNotEmpty = FormValidation.textFieldNotEmpty(telephoneField, telLabel, "enter telephone");
        boolean dateFieldIsNotEmpty = FormValidation.textFieldNotEmpty(dateField, dateLabel, "must be yyyy-MM-dd");

        if (loginFieldIsNotEmpty) {
            loginIsNotBusy = FormValidation.loginIsNotBusy(loginField, loginLabel, "this login is busy");
        }

        if (telephoneFieldIsNotEmpty) {
            telephoneFieldIsValid = FormValidation.telephoneIsValid(telephoneField, telLabel, "invalid value");
        }

        if (dateFieldIsNotEmpty) {
            dateIsValid = FormValidation.dateIsValid(dateField, dateLabel, "wrong date format");
        }

        return firstNameFieldIsNotEmpty && lastNameFieldIsNotEmpty && loginFieldIsNotEmpty && telephoneFieldIsNotEmpty && telephoneFieldIsValid && loginIsNotBusy && dateFieldIsNotEmpty && dateIsValid;
    }

}
