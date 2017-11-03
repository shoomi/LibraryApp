package LibWorker;

import UtilClasses.DBConnector;
import UtilClasses.Util;
import userAndBookClasses.Book;
import userAndBookClasses.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LibWorker {

    DBConnector dbConnector;
    Statement statement;
    ResultSet rs;

    public LibWorker() throws SQLException {
        dbConnector = new DBConnector();
        statement = dbConnector.getConnection().createStatement();
    }

    public void closeStatementAndConnection() throws SQLException {
        statement.close();
        dbConnector.closeConnection();
    }

    public void showAllFreeBooksFromDb() throws SQLException {
        rs = statement.executeQuery("SELECT\n" +
                "  title,\n" +
                "  author,\n" +
                "  release_date\n" +
                "FROM books\n" +
                "WHERE title NOT IN (SELECT title\n" +
                "                    FROM books\n" +
                "                      INNER JOIN borrowings ON books.book_id = borrowings.book_id\n" +
                "                    WHERE returning_date IS NULL)");

        while (rs.next()) {
            System.out.println(String.format("%s,   %s,   %s", rs.getString("title"), rs.getString("author"), rs.getString("release_date")));
        }
    }

    ////////////
    public ResultSet rsFreeBooksFromDb() throws SQLException {
        rs = statement.executeQuery("SELECT\n" +
                "  title,\n" +
                "  author,\n" +
                "  release_date\n" +
                "FROM books\n" +
                "WHERE title NOT IN (SELECT title\n" +
                "                    FROM books\n" +
                "                      INNER JOIN borrowings ON books.book_id = borrowings.book_id\n" +
                "                    WHERE returning_date IS NULL)");
        return rs;
    }

    ////////

    public void addNewUserToDbUsers(User libUser) throws SQLException {
        String addNewUser = String.format("INSERT INTO  mylibrary.`users` (login, first_name, last_name, phone_number, date_of_birth) VALUES ('%s','%s', '%s', '%s', '%s')", libUser.getLogin(), libUser.getFirstName(), libUser.getLastName(), libUser.getTelephone(), libUser.dateOfBirthirth());
        statement.execute(addNewUser);
        statement.close();
    }

    public void addNewBookToDbBooks(Book book) throws SQLException {
        String addNewBook = String.format("INSERT INTO  mylibrary.`books` (title, author, release_date, stock) VALUES ('%s','%s', '%s', %d)", book.getTitle(), book.getAuthor(), book.getReleaseDate(), book.getStock());
        statement.execute(addNewBook);
        statement.close();
    }

    public boolean loginIsValidated(String userlogin) throws SQLException {
        rs = statement.executeQuery(String.format("SELECT user_id FROM users WHERE login = '%s'", userlogin));
        if (rs.next()) {
            return true;
        }
        rs.close();
        return false;
    }

    public int getIdBookByName(String bookName, String booksYearRelese) throws SQLException {
        rs = statement.executeQuery(String.format("SELECT book_id FROM books WHERE title = '%s' AND books.release_date ='%s'", bookName, booksYearRelese));
        int bookId = -1;
        if (rs.next()) {
            bookId = rs.getInt("book_id");
        }
        rs.close();
        return bookId;
    }


    public void giveNewBookUser(String userLogin, String bookTitle, String bookAuthor, String booksYearRelese) throws SQLException {

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
        statement.close();
    }


    public boolean bookIsFree(String bookName, String booksYearRelese) throws SQLException {
        rs = statement.executeQuery(String.format("SELECT (SELECT stock\n" +
                "                        FROM books\n" +
                "                        WHERE title = '%s' AND release_date = '%s') - (SELECT count(borrowings.book_id)\n" +
                "                                                 FROM borrowings INNER JOIN books ON borrowings.book_id = books.book_id\n" +
                "                                                 WHERE title = '%s' AND release_date = '%s' AND returning_date IS NULL) AS free_books", bookName, booksYearRelese, bookName, booksYearRelese));
        rs.next();
        int freeBooks = rs.getInt("free_books");
        rs.close();
        return freeBooks > 0;
    }


    public void returnUserBook(String userLogin, String bookTitle, String bookAuthor, String booksYearRelese) throws SQLException {

        statement.execute(String.format("UPDATE mylibrary.borrowings\n" +
                "SET returning_date = '%s'\n" +
                "WHERE user_id = (SELECT user_id\n" +
                "                 FROM users\n" +
                "                 WHERE login = '%s') AND book_id = (SELECT book_id\n" +
                "                                                        FROM books\n" +
                "                                                        WHERE\n" +
                "                                                          title = '%s' AND author = '%s' AND\n" +
                "                                                          release_date = '%s') and returning_date is NULL", new Util().getCurrentTime(), userLogin, bookTitle, bookAuthor, booksYearRelese));
//        statement.close();
    }


    public boolean haveUserSomethingToReturn(String userLogin) throws SQLException {

        String query = String.format("SELECT book_id\n" +
                "FROM borrowings\n" +
                "  INNER JOIN users ON borrowings.user_id = users.user_id\n" +
                "WHERE (users.login = '%s') AND returning_date IS NULL", userLogin);
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            return true;
        }
        return false;
    }

    ////////////////

    public ResultSet rsUsersBooks(String userLogin) throws SQLException {
        String query = String.format("SELECT\n" +
                "  title,\n" +
                "  author,\n" +
                "  release_date,\n" +
                "  borrowing_date  \n" +
                "FROM ((borrowings\n" +
                "  INNER JOIN books ON borrowings.book_id = books.book_id)\n" +
                "  INNER JOIN users ON borrowings.user_id = users.user_id)\n" +
                "WHERE (users.login = '%s') AND returning_date IS NULL", userLogin);
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }

    ///////////////

    public boolean userBorrowThisBook(String userLogin, String bookTitle, String bookAuthor, String booksYearRelese) throws SQLException {
        String query = String.format("SELECT borrowing_date\n" +
                "FROM borrowings\n" +
                "  INNER JOIN books ON borrowings.book_id = books.book_id\n" +
                "WHERE borrowings.user_id = (SELECT user_id FROM users WHERE login = '%s') AND\n" +
                "      title = '%s' and author = '%s' AND release_date = '%s' AND returning_date IS NULL", userLogin, bookTitle, bookAuthor, booksYearRelese);
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            return true;
        }
        rs.close();
        return false;
    }

    public void deleteUserWithId(int userId) throws SQLException {
        statement.execute(String.format("DELETE FROM users WHERE user_id = '%d'", userId));
    }
}