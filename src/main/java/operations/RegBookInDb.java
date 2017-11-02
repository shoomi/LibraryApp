package operations;

import LibWorker.LibWorker;
import UtilClasses.DBConnector;
import userAndBookClasses.Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class RegBookInDb {

    Statement statement;
    private Book book;

    public RegBookInDb() throws SQLException {
        book = new Book();
        statement = new DBConnector().getConnection().createStatement();
    }


    public void addNewBook(String title, String author, String releaseYear, int numberOfBooks) {

        try {

            book.setTitle(title);
            book.setAuthor(author);
            book.setReleaseDate(releaseYear);
            book.setStock(numberOfBooks);

            if (!isBookExist()) {
                new LibWorker().addNewBookToDbBooks(book);
            } else {
                addBookToExisting();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addBookToExisting() throws SQLException {

        statement.executeUpdate(String.format("UPDATE mylibrary.books SET stock = stock + %d " +
                        "WHERE books.title = '%s' AND books.author = '%s' and release_date = '%s'",
                book.getStock(), book.getTitle(), book.getAuthor(), book.getReleaseDate()));
        statement.close();

    }

    private boolean isBookExist() throws SQLException {
        ResultSet rs = statement.executeQuery(String.format("SELECT books.title FROM books WHERE books.title = '%s' AND books.author = '%s' " +
                "AND release_date = '%s'", book.getTitle(), book.getAuthor(), book.getReleaseDate()));
        if (rs.next()) {
            return true;
        }
        return false;
    }

}
