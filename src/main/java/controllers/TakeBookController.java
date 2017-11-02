package controllers;

import Dialogs.Dialogs;
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

public class TakeBookController {

    public ObservableList<Book> bookList = FXCollections.observableArrayList();


    @FXML
    public TableView<Book> tableBooks;
    @FXML
    public TableColumn<Book, Integer> nnColumn;
    @FXML
    public TableColumn<Book, String> titleColumn;
    @FXML
    public TableColumn<Book, String> authorColumn;
    @FXML
    public TableColumn<Book, String> releaseYearColumn;

    @FXML
    public TextField searchField;
    @FXML
    public Button takeBookButton;

    @FXML
    public Label titleLabel;
    @FXML
    public Label authorLabel;
    @FXML
    public Label bookYearLabel;


    public void showTakeBookWindow(javafx.event.ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/takeBook.fxml"));
            stage.setTitle("Take book window");
            stage.setMinHeight(300);
            stage.setMinWidth(500);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

        loadDataFromDbIntoBookList();

        nnColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("nn"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("releaseDate"));

        tableBooks.setItems(bookList);
        mauseClicked();
    }


    public void loadDataFromDbIntoBookList() {

        try {
            LibWorker libWorker = new LibWorker();
            ResultSet rs = libWorker.rsFreeBooksFromDb();
            int nn = 1;
            while (rs.next()) {
                bookList.add(new Book(nn, rs.getString("title"), rs.getString("author"), rs.getString("release_date")));
                nn++;
            }
            rs.close();
            libWorker.closeStatementAndConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void mauseClicked() {
        tableBooks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    titleLabel.setText(tableBooks.getSelectionModel().getSelectedItem().getTitle());
                    authorLabel.setText(tableBooks.getSelectionModel().getSelectedItem().getAuthor());
                    bookYearLabel.setText(tableBooks.getSelectionModel().getSelectedItem().getReleaseDate());
                }
            }
        });
    }

    public void takeBook(javafx.event.ActionEvent actionEvent) throws SQLException {
        LibWorker libWorker = new LibWorker();

        if (titleLabel.getText().equals("")) {
            Dialogs.showInfoDialog("Hey", "Make your choice");
        }
        if (!libWorker.userBorrowThisBook(LoginCheck.userLogin, titleLabel.getText(), authorLabel.getText(), bookYearLabel.getText())) {
            libWorker.giveNewBookUser(LoginCheck.userLogin, titleLabel.getText(), authorLabel.getText(), bookYearLabel.getText());
        } else Dialogs.showInfoDialog("Opps", "It's looks like you have already borrowed this book");
    }
}
