package controllers;

import dialogs.Dialogs;
import javafx.collections.transformation.FilteredList;
import javafx.scene.input.KeyEvent;
import libworker.LibWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import search.BooksSearch;
import search.Search;
import libitems.Book;

import java.io.IOException;
import java.sql.SQLException;

import operations.TakeBook;

public class TakeBookController {

    private ObservableList<Book> booksList;
    private FilteredList<Book> filteredBooksList;

    @FXML
    private TableView<Book> tableBooks;
    @FXML
    private TableColumn<Book, String> sequenceNumberColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> releaseYearColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label bookYearLabel;

    private TakeBook takeBook;

    public TakeBookController (){
        takeBook = new TakeBook();
    }

    public void showTakeBookWindow(javafx.event.ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/takeBook.fxml"));
            stage.setTitle("Free books list");
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

        loadFromDbToFillBooksLists();

        sequenceNumberColumn.setCellValueFactory(cellData -> cellData.getValue().sequenceNumberProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        releaseYearColumn.setCellValueFactory(cellData -> cellData.getValue().releaseYearProperty());

        tableBooks.setItems(booksList);
        mouseClick();
    }


    private void loadFromDbToFillBooksLists() throws SQLException {

        booksList = FXCollections.observableArrayList();
        filteredBooksList = new FilteredList<>(booksList, e -> true);

        if (!new LibWorker().loadDataFromDbInBookList(booksList)) {
            Dialogs.showInfoDialog("Info", "Sorry, but currently there is no books in our library");
        }

    }


    public void takeTheBook(javafx.event.ActionEvent actionEvent) {

        takeBook.takeBook(titleLabel, authorLabel, bookYearLabel, tableBooks, booksList);

    }

    private void mouseClick() {
        tableBooks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    titleLabel.setText(tableBooks.getSelectionModel().getSelectedItem().getTitle());
                    authorLabel.setText(tableBooks.getSelectionModel().getSelectedItem().getAuthor());
                    bookYearLabel.setText(tableBooks.getSelectionModel().getSelectedItem().getReleaseYear());
                }
            }
        });
    }

    public void searchTheBook(KeyEvent keyEvent) {
        Search searchBorrowedBook = new BooksSearch();
        searchBorrowedBook.search(searchField, filteredBooksList, tableBooks);
    }


//    private void getStageSource(ActionEvent actionEvent) {
//        Node source = (Node) actionEvent.getSource();
//        stage = (Stage) source.getScene().getWindow();
//    }



}
