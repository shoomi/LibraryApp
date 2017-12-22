package library.formvalidations;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BookValuesControl {

    public boolean checkValues(TextField titleField, TextField authorField, TextField yearBookField, TextField numberOfBooksField, Label titleLabel, Label authorLabel, Label numberOfBooksLabel, Label yearBookLabel) {

        boolean yearIsValid = false;
        boolean amountOfBooksIsValid = false;

        boolean titleIsNotEmpty = FormValidation.textFieldNotEmpty(titleField, titleLabel, "enter Title");
        boolean authorIsNotEmpty = FormValidation.textFieldNotEmpty(authorField, authorLabel, "enter Author");
        boolean yearIsNotEmpty = FormValidation.textFieldNotEmpty(yearBookField, yearBookLabel, "year 'YYYY'");
        boolean amountIsNotEmpty = FormValidation.textFieldNotEmpty(numberOfBooksField, numberOfBooksLabel, "enter number");

        if (yearIsNotEmpty) {
            yearIsValid = FormValidation.bookYearIsValid(yearBookField, yearBookLabel, "invalid value");
        }
        if (amountIsNotEmpty) {
            amountOfBooksIsValid = FormValidation.numberOfBooksIsValid(numberOfBooksField, numberOfBooksLabel, "invalid value");
        }

        return titleIsNotEmpty && authorIsNotEmpty && yearIsNotEmpty && amountIsNotEmpty && yearIsValid && amountOfBooksIsValid;


    }

}
