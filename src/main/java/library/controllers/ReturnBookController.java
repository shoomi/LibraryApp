package library.controllers;

import library.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.operations.ReturnBook;
import library.search.BooksSearch;
import library.search.Search;
import library.libitems.Book;

import java.io.IOException;
import java.sql.SQLException;
import library.LibWorker;

public class ReturnBookController {

    private ObservableList<Book> borrowedBooksList;
    private FilteredList<Book> filteredBorrowedBooksList;

    @FXML
    private TableView<Book> tableBorrowingBooks;
    @FXML
    private TableColumn<Book, String> sequenceNumberColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> releaseYearColumn;
    @FXML
    private TableColumn<Book, String> borrowingDateColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label bookYearLabel;

    private ReturnBook returnBook;

    public ReturnBookController(){
        returnBook = new ReturnBook();
    }

    public void showReturnBookWindow(javafx.event.ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/returnBook.fxml"));
            stage.setTitle(String.format("Borrowed books '%s'", LoginCheck.userLogin));
            stage.setMinHeight(300);
            stage.setMinWidth(500);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() throws SQLException {

        loadFromDbToFillBorrowedBooksLists();

        sequenceNumberColumn.setCellValueFactory(cellData -> cellData.getValue().sequenceNumberProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        releaseYearColumn.setCellValueFactory(cellData -> cellData.getValue().releaseYearProperty());
        borrowingDateColumn.setCellValueFactory(cellData -> cellData.getValue().borrowingDateProperty());

        tableBorrowingBooks.setItems(borrowedBooksList);
        mouseClick();
    }


    private void loadFromDbToFillBorrowedBooksLists() throws SQLException {

        borrowedBooksList = FXCollections.observableArrayList();
        filteredBorrowedBooksList = new FilteredList<>(borrowedBooksList, e -> true);

        if (!new LibWorker().loadDataFromDbInBorrowedBooksList(borrowedBooksList)) {
            Dialogs.showInfoDialog("Information", "You have nothing to return");
        }
    }


    public void returnTheBook(javafx.event.ActionEvent actionEvent) throws SQLException {

        returnBook.returnTheBook(titleLabel, authorLabel, bookYearLabel, tableBorrowingBooks, borrowedBooksList);

    }


    private void mouseClick() {
        tableBorrowingBooks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    titleLabel.setText(tableBorrowingBooks.getSelectionModel().getSelectedItem().getTitle());
                    authorLabel.setText(tableBorrowingBooks.getSelectionModel().getSelectedItem().getAuthor());
                    bookYearLabel.setText(tableBorrowingBooks.getSelectionModel().getSelectedItem().getReleaseYear());
                }
            }
        });
    }

    public void searchBorrowedBook(KeyEvent keyEvent) {
        Search searchBorrowedBook = new BooksSearch();
        searchBorrowedBook.search(searchField, filteredBorrowedBooksList, tableBorrowingBooks);
    }



//    private void getStageSource(ActionEvent actionEvent) {
//        Node source = (Node) actionEvent.getSource();
//        stage = (Stage) source.getScene().getWindow();
//    }


}