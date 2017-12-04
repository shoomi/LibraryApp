package libWorker;

import controllers.LoginCheck;
import javafx.collections.ObservableList;
import libraryitem.Book;
import libraryitem.User;
import utils.connection.NewStatementConnector;
import utils.Util;
import libraryitem.LibBook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LibWorker {

    private static Statement statement;
    private ResultSet rs;

    public LibWorker() {
        if (statement == null) {
            statement = new NewStatementConnector().returnStatement();
        }
    }

    private ResultSet rsFreeBooksFromDb() throws SQLException {
        rs = statement.executeQuery("SELECT books.title,books.author, books.release_date\n" +
                "FROM books\n" +
                "WHERE book_id NOT IN(SELECT DISTINCT borrowings.book_id\n" +
                "                 FROM borrowings\n" +
                "                   INNER JOIN books ON borrowings.book_id = books.book_id\n" +
                "                 WHERE ((SELECT count(book_id) FROM borrowings WHERE borrowings.book_id = books.book_id AND returning_date IS NULL) = (SELECT stock FROM books WHERE books.book_id =borrowings.book_id)))");
        return rs;
    }


    public void addNewUserToDbUsers(User libUser) throws SQLException {
        String addNewUser = String.format("INSERT INTO  mylibrary.`users` (login, first_name, last_name, phone_number, date_of_birth) VALUES ('%s','%s', '%s', '%s', '%s')", libUser.getLogin(), libUser.getFirstName(), libUser.getLastName(), libUser.getTelephone(), libUser.dateOfBirth());
        statement.execute(addNewUser);
    }

    public void addNewBookToDbBooks(Book book) throws SQLException {
        String addNewBook = String.format("INSERT INTO  mylibrary.`books` (title, author, release_date, stock) VALUES ('%s','%s', '%s', %d)", book.getTitle(), book.getAuthor(), book.getReleaseYear(), book.getStock());
        statement.execute(addNewBook);
    }

    public boolean loginIsValidated(String userLogin) throws SQLException {
        rs = statement.executeQuery(String.format("SELECT user_id FROM users WHERE login = '%s'", userLogin));
        if (rs.next()) {
            return true;
        }
        rs.close();
        return false;
    }


    public void giveNewBookToUser(String userLogin, String bookTitle, String bookAuthor, String booksYearRelese) throws SQLException {

        statement.execute(String.format("INSERT INTO mylibrary.`borrowings` (user_id, book_id, borrowing_date) VALUES ((SELECT user_id\n" +
                "                                                                               FROM users\n" +
                "                                                                               WHERE login = '%s'),\n" +
                "                                                                              (SELECT book_id\n" +
                "                                                                               FROM books\n" +
                "                                                                               WHERE\n" +
                "                                                                                 title = '%s' AND author = '%s' AND\n" +
                "                                                                                 release_date = '%s'),\n" +
                "                                                                              '%s')", userLogin, bookTitle, bookAuthor, booksYearRelese, new Util().getCurrentTime()));
        System.out.println("This book is yours! Nice reading");
    }

    public void returnUserBook(String userLogin, String bookTitle, String bookAuthor, String booksYearRelease) throws SQLException {

        statement.execute(String.format("UPDATE mylibrary.borrowings\n" +
                "SET returning_date = '%s'\n" +
                "WHERE user_id = (SELECT user_id\n" +
                "                 FROM users\n" +
                "                 WHERE login = '%s') AND book_id = (SELECT book_id\n" +
                "                                                        FROM books\n" +
                "                                                        WHERE\n" +
                "                                                          title = '%s' AND author = '%s' AND\n" +
                "                                                          release_date = '%s') and returning_date is NULL", new Util().getCurrentTime(), userLogin, bookTitle, bookAuthor, booksYearRelease));
    }


    private ResultSet rsUsersBooks(String userLogin) throws SQLException {
        String query = String.format("SELECT\n" +
                "  title,\n" +
                "  author,\n" +
                "  release_date,\n" +
                "  borrowing_date  \n" +
                "FROM ((borrowings\n" +
                "  INNER JOIN books ON borrowings.book_id = books.book_id)\n" +
                "  INNER JOIN users ON borrowings.user_id = users.user_id)\n" +
                "WHERE (users.login = '%s') AND returning_date IS NULL", userLogin);
        return statement.executeQuery(query);
    }


    public boolean userBorrowThisBook(String userLogin, String bookTitle, String bookAuthor, String booksYearRelease) throws SQLException {
        String query = String.format("SELECT borrowing_date\n" +
                "FROM borrowings\n" +
                "  INNER JOIN books ON borrowings.book_id = books.book_id\n" +
                "WHERE borrowings.user_id = (SELECT user_id FROM users WHERE login = '%s') AND\n" +
                "      title = '%s' and author = '%s' AND release_date = '%s' AND returning_date IS NULL", userLogin, bookTitle, bookAuthor, booksYearRelease);
        rs = statement.executeQuery(query);
        if (rs.next()) {
            return true;
        }
        rs.close();
        return false;
    }


    public void addBookToExisting(Book book) throws SQLException {
        statement.executeUpdate(String.format("UPDATE mylibrary.books SET stock = stock + %d " +
                        "WHERE books.title = '%s' AND books.author = '%s' and release_date = '%s'",
                book.getStock(), book.getTitle(), book.getAuthor(), book.getReleaseYear()));
    }

    public boolean doesBookExist(Book book) throws SQLException {
        rs = statement.executeQuery(String.format("SELECT books.title FROM books WHERE books.title = '%s' AND books.author = '%s' " +
                "AND release_date = '%s'", book.getTitle(), book.getAuthor(), book.getReleaseYear()));
        return rs.next();
    }

    public boolean loadDataFromDbIntoBookListToTake(ObservableList<Book> booksList) throws SQLException {
        rs = rsFreeBooksFromDb();        /// select all free books from Db
        int sequenceNumber = 1;
        while (rs.next()) {
            booksList.add(new LibBook(Integer.toString(sequenceNumber), rs.getString("title"), rs.getString("author"), rs.getString("release_date")));
            sequenceNumber++;
        }
        rs.close();
        return sequenceNumber > 1;
    }

    public boolean loadDataFromDbIntoBookListToReturn(ObservableList<Book> borrowedBooksList) throws SQLException {

        int sequenceNumber = 1;
        rs = rsUsersBooks(LoginCheck.userLogin);
        while (rs.next()) {
            borrowedBooksList.add(new LibBook(Integer.toString(sequenceNumber), rs.getString("title"), rs.getString("author"), rs.getString("release_date"), rs.getString("borrowing_date")));
            sequenceNumber++;
        }
        rs.close();
        return sequenceNumber > 1;
    }

}