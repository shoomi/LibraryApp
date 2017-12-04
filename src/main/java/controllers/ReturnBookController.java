package controllers;

import dialogs.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import operations.ReturnBook;
import search.BooksSearch;
import search.Search;
import libraryitem.Book;

import java.io.IOException;
import java.sql.SQLException;

import libWorker.LibWorker;


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

    private Stage stage;

    public void showReturnBookWindow(javafx.event.ActionEvent actionEvent) {

        try {
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/returnBook.fxml"));
            stage.setTitle(String.format("The list of your borrowed books in our library. You entered as'%s'", LoginCheck.userLogin));
            stage.setMinHeight(300);
            stage.setMinWidth(500);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            getStageSource(actionEvent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() throws SQLException {

        loadFromDb();

        sequenceNumberColumn.setCellValueFactory(cellData -> cellData.getValue().sequenceNumberProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        releaseYearColumn.setCellValueFactory(cellData -> cellData.getValue().releaseYearProperty());
        borrowingDateColumn.setCellValueFactory(cellData -> cellData.getValue().borrowingDateProperty());

        tableBorrowingBooks.setItems(borrowedBooksList);
        mouseClick();
    }


    private void loadFromDb() throws SQLException {

        borrowedBooksList = FXCollections.observableArrayList();
        filteredBorrowedBooksList = new FilteredList<>(borrowedBooksList, e -> true);

        if (!new LibWorker().loadDataFromDbIntoBookListToReturn(borrowedBooksList)) {
            Dialogs.showInfoDialog("Information", "You have nothing to return");
        }
    }

    public void returnBook(javafx.event.ActionEvent actionEvent) throws SQLException {

        if (tableBorrowingBooks.getItems().size() == 0) {
            Dialogs.showInfoDialog("Info", "You have successfully returned all books!");
            getStageSource(actionEvent);
            stage.close();
        } else {
            new ReturnBook().returnTheBook(titleLabel, authorLabel, bookYearLabel, tableBorrowingBooks,borrowedBooksList);

        }
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


    private void getStageSource(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
    }


    public void searchBorrowedBook(KeyEvent keyEvent) {
        Search searchBorrowedBook = new BooksSearch();
        searchBorrowedBook.search(searchField, filteredBorrowedBooksList, tableBorrowingBooks);

    }
}
