package operations;

import controllers.LoginCheck;
import dialogs.Dialogs;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import libWorker.LibWorker;
import libraryitem.Book;

import java.sql.SQLException;


public class ReturnBook {

    public void returnTheBook(Label titleLabel, Label authorLabel, Label bookYearLabel, TableView<Book> tableBorrowingBooks, ObservableList<Book> booksList) {

        if (titleLabel.getText().equals("")) {
            Dialogs.showInfoDialog("Hey", "Make your choice");
        } else {

            try {
                new LibWorker().returnUserBook(LoginCheck.userLogin, titleLabel.getText(), authorLabel.getText(), bookYearLabel.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Dialogs.showInfoDialog("Information", String.format("The book '%s' was returned! Thanks", titleLabel.getText()));

            titleLabel.setText("");
            authorLabel.setText("");
            bookYearLabel.setText("");

            Book selectedItem = tableBorrowingBooks.getSelectionModel().getSelectedItem();

            booksList.remove(selectedItem);                         // delete selected book from list after search
            tableBorrowingBooks.getItems().remove(selectedItem);    // delete selected book without search

        }
    }

}
