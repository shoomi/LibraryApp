package library.formvalidations;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BookValuesControl {

    public boolean checkValues(TextField titleField, TextField authorField, TextField yearBookField, TextField numberOfBooksField, Label titleLabel, Label authorLabel, Label numberOfBooksLabel, Label yearBookLabel) {

        boolean isYearValid = false;

        boolean isTitleEmpty = FormValidation.isTextFieldEmpty(titleField, titleLabel, "enter Title");
        boolean isAuthorEmpty = FormValidation.isTextFieldEmpty(authorField, authorLabel, "enter Author");
        boolean isYearEmpty = FormValidation.isTextFieldEmpty(yearBookField, yearBookLabel, "year 'YYYY'");
        boolean isAmountEmpty = FormValidation.isTextFieldEmpty(numberOfBooksField, numberOfBooksLabel, "enter number");

        if (!isYearEmpty) {
            isYearValid = FormValidation.isBookYearValid(yearBookField, yearBookLabel, "invalid value");
        }

        return !isTitleEmpty && !isAuthorEmpty && !isYearEmpty && !isAmountEmpty && isYearValid;


    }

}
