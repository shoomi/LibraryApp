package library.operations;

import library.Dialogs;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import library.LibWorker;
import library.controllers.LoginCheck;
import javafx.scene.control.Label;
import library.libitems.Book;

public class TakeBook {

    private LibWorker libWorker;

    public TakeBook() {
        libWorker = new LibWorker();
    }

    private boolean giveThisBook(Label titleLabel, Label authorLabel, Label bookYearLabel) {

            if (!libWorker.doesUserBorrowThisBook(LoginCheck.userLogin, titleLabel.getText(), authorLabel.getText(), bookYearLabel.getText())) {

                libWorker.userTakesTheBook(LoginCheck.userLogin, titleLabel.getText(), authorLabel.getText(), bookYearLabel.getText());

                titleLabel.setText("");
                authorLabel.setText("");
                bookYearLabel.setText("");

                return true;
            }

        return false;
    }

    public void takeBook(Label titleLabel, Label authorLabel, Label bookYearLabel, TableView<Book> tableBooks, ObservableList<Book> booksList) {

        if (titleLabel.getText().equals("")) {
            Dialogs.showInfoDialog("Hey", "Make your choice");

        } else {

            if (giveThisBook(titleLabel, authorLabel, bookYearLabel)) {    /// give the book to user

                Dialogs.showInfoDialog("Information", String.format("The book '%s' is yours! Nice reading", titleLabel.getText()));

                Book selectedItem = tableBooks.getSelectionModel().getSelectedItem();

                booksList.remove(selectedItem);             // delete selected book from list after searching
                tableBooks.getItems().remove(selectedItem);    // delete selected book without searching

            } else {
                Dialogs.showInfoDialog("Oops", "It's looks like you have already borrowed this book");
            }

        }


    }

}


