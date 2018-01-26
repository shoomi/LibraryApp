package library.search;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import library.libitems.Book;

import java.util.function.Predicate;

public class BooksSearch implements Search {

    @Override
    public void search(TextField searchField, FilteredList filteredBooksList, TableView tableBorrowingBooks) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredBooksList.setPredicate((Predicate<? super Book>) book -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getReleaseYear().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Book> sortedData = new SortedList<Book>(filteredBooksList);
        sortedData.comparatorProperty().bind(tableBorrowingBooks.comparatorProperty());
        tableBorrowingBooks.setItems(sortedData);
    }


}
