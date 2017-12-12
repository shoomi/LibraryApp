package libworker;

import controllers.LoginCheck;
import javafx.collections.ObservableList;
import libitems.Book;
import libitems.User;
import utils.Util;
import libitems.LibBook;
import utils.connection.NewConnection;

import java.sql.*;

public class LibWorker {


    public boolean loadDataFromDbInBookList(ObservableList<Book> booksList) {

        int sequenceNumber = 1;

        try (Connection con = NewConnection.getConnection()) {

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT books.title,books.author, books.release_date\n" +            /// select all free books from Db
                    "FROM books\n" +
                    "WHERE book_id NOT IN(SELECT DISTINCT borrowings.book_id\n" +
                    "                 FROM borrowings\n" +
                    "                   INNER JOIN books ON borrowings.book_id = books.book_id\n" +
                    "                 WHERE ((SELECT count(book_id) FROM borrowings WHERE borrowings.book_id = books.book_id AND returning_date IS NULL) = (SELECT stock FROM books WHERE books.book_id =borrowings.book_id)))");

            while (rs.next()) {
                booksList.add(new LibBook(Integer.toString(sequenceNumber), rs.getString("title"), rs.getString("author"), rs.getString("release_date")));
                sequenceNumber++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sequenceNumber > 1;
    }


    public void addNewUserToDbUsers(User libUser) {

        String addNewUser = "INSERT INTO  mylibrary.`users` (login, first_name, last_name, phone_number, date_of_birth, password) VALUES (?,?, ?,?,?,?)";
        try (Connection con = NewConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(addNewUser);
            ps.setString(1, libUser.getLogin());
            ps.setString(2, libUser.getFirstName());
            ps.setString(3, libUser.getLastName());
            ps.setString(4, libUser.getTelephone());
            ps.setString(5, libUser.dateOfBirth());
            ps.setString(6, libUser.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewBookToDbBooks(Book book) {

        String addNewBook = "INSERT INTO  mylibrary.`books` (title, author, release_date, stock) VALUES (?,?,?,?)";
        try (Connection con = NewConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(addNewBook);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getReleaseYear());
            ps.setInt(4, book.getStock());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void giveNewBookToUser(String userLogin, String bookTitle, String bookAuthor, String booksYearRelese) {

        try (Connection con = NewConnection.getConnection()) {

            Statement statement = con.createStatement();
            statement.execute(String.format("INSERT INTO mylibrary.`borrowings` (user_id, book_id, borrowing_date) VALUES ((SELECT user_id\n" +
                    "                                                                               FROM users\n" +
                    "                                                                               WHERE login = '%s'),\n" +
                    "                                                                              (SELECT book_id\n" +
                    "                                                                               FROM books\n" +
                    "                                                                               WHERE\n" +
                    "                                                                                 title = '%s' AND author = '%s' AND\n" +
                    "                                                                                 release_date = '%s'),\n" +
                    "                                                                              '%s')", userLogin, bookTitle, bookAuthor, booksYearRelese, new Util().getCurrentTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("This book is yours! Nice reading");
    }


    public void addBookToExisting(Book book) {

        try (Connection con = NewConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement("UPDATE mylibrary.books SET stock = stock + ? WHERE books.title = ? AND books.author = ? AND release_date = ?");
            ps.setInt(1, book.getStock());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getReleaseYear());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void returnUserBook(String userLogin, String bookTitle, String bookAuthor, String booksYearRelease) {

        try (Connection con = NewConnection.getConnection()) {

            Statement statement = con.createStatement();
            statement.execute(String.format("UPDATE mylibrary.borrowings\n" +
                    "SET returning_date = '%s'\n" +
                    "WHERE user_id = (SELECT user_id\n" +
                    "                 FROM users\n" +
                    "                 WHERE login = '%s') AND book_id = (SELECT book_id\n" +
                    "                                                        FROM books\n" +
                    "                                                        WHERE\n" +
                    "                                                          title = '%s' AND author = '%s' AND\n" +
                    "                                                          release_date = '%s') and returning_date is NULL", new Util().getCurrentTime(), userLogin, bookTitle, bookAuthor, booksYearRelease));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean loginExists(String userLogin) {

        try (Connection con = NewConnection.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT user_id FROM users WHERE login = '%s'", userLogin));
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean doesBookExist(Book book) {

        try (Connection con = NewConnection.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT books.title FROM books WHERE books.title = '%s' AND books.author = '%s' " +
                    "AND release_date = '%s'", book.getTitle(), book.getAuthor(), book.getReleaseYear()));
            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean userBorrowThisBook(String userLogin, String bookTitle, String bookAuthor, String booksYearRelease) {

        String query = String.format("SELECT borrowing_date\n" +
                "FROM borrowings\n" +
                "  INNER JOIN books ON borrowings.book_id = books.book_id\n" +
                "WHERE borrowings.user_id = (SELECT user_id FROM users WHERE login = '%s') AND\n" +
                "      title = '%s' and author = '%s' AND release_date = '%s' AND returning_date IS NULL", userLogin, bookTitle, bookAuthor, booksYearRelease);

        try (Connection con = NewConnection.getConnection()) {

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean loadDataFromDbInBorrowedBooksList(ObservableList<Book> borrowedBooksList) {

        int sequenceNumber = 1;

        String query = String.format("SELECT\n" +
                "  title,\n" +
                "  author,\n" +
                "  release_date,\n" +
                "  borrowing_date  \n" +
                "FROM ((borrowings\n" +
                "  INNER JOIN books ON borrowings.book_id = books.book_id)\n" +
                "  INNER JOIN users ON borrowings.user_id = users.user_id)\n" +
                "WHERE (users.login = '%s') AND returning_date IS NULL", LoginCheck.userLogin);

        try (Connection con = NewConnection.getConnection()) {

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                borrowedBooksList.add(new LibBook(Integer.toString(sequenceNumber), rs.getString("title"), rs.getString("author"), rs.getString("release_date"), rs.getString("borrowing_date")));
                sequenceNumber++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sequenceNumber > 1;
    }


    public String getUserPassword(String login) {

        String password = "";
        try (Connection con = NewConnection.getConnection()) {

            Statement statement = con.createStatement();
            statement.executeQuery(String.format("SELECT password FROM users WHERE login = '%s'", login));
            ResultSet rs = statement.getResultSet();
            if (rs.next()) {
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }


}