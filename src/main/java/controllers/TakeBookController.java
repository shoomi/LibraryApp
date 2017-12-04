package controllers;

import dialogs.Dialogs;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import libWorker.LibWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import libraryitem.Book;

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

    private Stage stage;

    public void showTakeBookWindow(javafx.event.ActionEvent actionEvent) {

        try {
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/takeBook.fxml"));
            stage.setTitle(String.format("The list of all available books in the library. You entered as '%s'", LoginCheck.userLogin));
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

        tableBooks.setItems(booksList);
        mouseClicked();
    }


    private void loadFromDb() throws SQLException {

        booksList = FXCollections.observableArrayList();
        filteredBooksList = new FilteredList<>(booksList, e -> true);

        if (!new LibWorker().loadDataFromDbIntoBookListToTake(booksList)) {
            Dialogs.showInfoDialog("Info", "Sorry, but currently there is no books in our library");
        }

    }


    public void takeBook(javafx.event.ActionEvent actionEvent) {

        if (tableBooks.getItems().size() == 0) {
            Dialogs.showInfoDialog("Information", "Sorry, but you've took all free books");
            getStageSource(actionEvent);
            stage.close();
        } else {
            new TakeBook().takeBook(titleLabel, authorLabel, bookYearLabel, tableBooks, booksList);
        }
    }

    private void mouseClicked() {
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


    private void getStageSource(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
    }


    public void searchTheBook(KeyEvent keyEvent) {
        Search searchBorrowedBook = new BooksSearch();
        searchBorrowedBook.search(searchField, filteredBooksList, tableBooks);
    }


}
