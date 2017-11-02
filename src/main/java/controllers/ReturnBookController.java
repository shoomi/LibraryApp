package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import userAndBookClasses.Book;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import LibWorker.LibWorker;

public class ReturnBookController {

    @FXML
    public ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public TableView<Book> tableBorrowingBooks;
    @FXML
    public TableColumn<Book, Integer> nnColumn;
    @FXML
    public TableColumn<Book, String> titleColumn;
    @FXML
    public TableColumn<Book, String> authorColumn;
    @FXML
    public TableColumn<Book, String> releaseYearColumn;
    @FXML
    public TableColumn<Book, String> borrowingDateColumn;

    @FXML
    public TextField searchField;
    @FXML
    public Button returnBookButton;

    @FXML
    public Label titleLabel;
    @FXML
    public Label authorLabel;
    @FXML
    public Label bookYearLabel;


    LibWorker libWorker;

    public ReturnBookController() throws SQLException {
        libWorker = new LibWorker();
    }

    public void showReturnBookWindow(javafx.event.ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/returnBook.fxml"));
            stage.setTitle("Return the book");
            stage.setMinHeight(300);
            stage.setMinWidth(500);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {

        try {
            if (libWorker.doesUserBorrowBooks(LoginCheck.userLogin)) {

                loadDataFromDbIntoBookList();

                nnColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("nn"));
                titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
                authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
                releaseYearColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("releaseDate"));
                borrowingDateColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("borrowingDate"));

                tableBorrowingBooks.setItems(bookList);
                mauseClicked();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void loadDataFromDbIntoBookList() {

        try {
            int nn = 1;
            ResultSet rs = libWorker.rsUsersBooks(LoginCheck.userLogin);
            while (rs.next()) {
                bookList.add(new Book(nn, rs.getString("title"), rs.getString("author"), rs.getString("release_date"), rs.getString("borrowing_date")));
                nn++;
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void mauseClicked() {
        tableBorrowingBooks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    titleLabel.setText(tableBorrowingBooks.getSelectionModel().getSelectedItem().getTitle());
                    authorLabel.setText(tableBorrowingBooks.getSelectionModel().getSelectedItem().getAuthor());
                    bookYearLabel.setText(tableBorrowingBooks.getSelectionModel().getSelectedItem().getReleaseDate());
                }
            }
        });
    }


    public void returnBook(javafx.event.ActionEvent actionEvent) throws SQLException {

        libWorker.returnUserBook(LoginCheck.userLogin, titleLabel.getText(), authorLabel.getText(), bookYearLabel.getText());

    }

}
