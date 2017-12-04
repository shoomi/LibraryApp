package operations;

import dialogs.Dialogs;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import libWorker.LibWorker;
import controllers.LoginCheck;
import javafx.scene.control.Label;
import libraryitem.Book;
import libraryitem.LibBook;

import java.sql.SQLException;

public class TakeBook {

    private boolean giveThisBook(Label titleLabel, Label authorLabel, Label bookYearLabel) {

        try {
            LibWorker libWorker = new LibWorker();
            if (!libWorker.userBorrowThisBook(LoginCheck.userLogin, titleLabel.getText(), authorLabel.getText(), bookYearLabel.getText())) {

                libWorker.giveNewBookToUser(LoginCheck.userLogin, titleLabel.getText(), authorLabel.getText(), bookYearLabel.getText());

                titleLabel.setText("");
                authorLabel.setText("");
                bookYearLabel.setText("");

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void takeBook(Label titleLabel, Label authorLabel, Label bookYearLabel, TableView<Book> tableBooks, ObservableList<Book> booksList) {

        if (titleLabel.getText().equals("")) {
            Dialogs.showInfoDialog("Hey", "Make your choice");

        } else {

            if (giveThisBook(titleLabel, authorLabel, bookYearLabel)) {    /// give the book to user

                Dialogs.showInfoDialog("Information", String.format("The book '%s' is yours! Nice reading", titleLabel.getText()));

                LibBook selectedItem = (LibBook) tableBooks.getSelectionModel().getSelectedItem();

                booksList.removeAll(selectedItem);             // delete selected book from list after search
                tableBooks.getItems().remove(selectedItem);    // delete selected book without search

            } else {
                Dialogs.showInfoDialog("Opps", "It's looks like you have already borrowed this book");
            }

        }
    }

}


