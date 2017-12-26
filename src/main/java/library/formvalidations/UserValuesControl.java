package library.formvalidations;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class UserValuesControl {

    public boolean checkValues(TextField loginField, TextField passwordField, TextField firstNameField, TextField lastNameField, TextField telephoneField, TextField dateField, Label loginLabel, Label passwordLabel, Label firsNameLabel, Label lastNameLabel, Label telLabel, Label dateLabel) {

        boolean isLoginBusy = false;
        boolean isTelephoneFieldValid = false;
        boolean isDateValid = false;

        boolean isLoginFieldEmpty = FormValidation.isTextFieldEmpty(loginField, loginLabel, "enter login");
        boolean isPasswordEmpty = FormValidation.isTextFieldEmpty(passwordField, passwordLabel, "enter password");
        boolean isFirstNameFieldEmpty = FormValidation.isTextFieldEmpty(firstNameField, firsNameLabel, "enter Firs Name");
        boolean isLastNameFieldEmpty = FormValidation.isTextFieldEmpty(lastNameField, lastNameLabel, "enter Last Name");
        boolean isTelephoneFieldEmpty = FormValidation.isTextFieldEmpty(telephoneField, telLabel, "enter telephone");
        boolean isDateFieldEmpty = FormValidation.isTextFieldEmpty(dateField, dateLabel, "must be yyyy-MM-dd");

        if (!isLoginFieldEmpty) {
            isLoginBusy = FormValidation.isLoginBusy(loginField, loginLabel, "this login is busy");
        }

        if (!isTelephoneFieldEmpty) {
            isTelephoneFieldValid = FormValidation.isTelephoneValid(telephoneField, telLabel, "invalid value");
        }

        if (!isDateFieldEmpty) {
            isDateValid = FormValidation.isDateValid(dateField, dateLabel, "wrong date");
        }

        return !isLoginFieldEmpty && !isPasswordEmpty && !isFirstNameFieldEmpty && !isLastNameFieldEmpty && !isTelephoneFieldEmpty && !isDateFieldEmpty && !isLoginBusy && isTelephoneFieldValid && isDateValid;
    }

}
