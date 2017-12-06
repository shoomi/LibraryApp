package search;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import libitems.Book;


public interface Search {
    void search(TextField searchField, FilteredList filteredBooksList, TableView<Book> tableBorrowingBooks);
}
