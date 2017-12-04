package operations;

import libWorker.LibWorker;
import libraryitem.LibBook;

import java.sql.SQLException;

public class RegBookInDb {


    public void addNewBook(String title, String author, String releaseYear, int numberOfBooks) {

        LibBook libBook = new LibBook();
        LibWorker libWorker = new LibWorker();

        try {

            libBook.setTitle(title);
            libBook.setAuthor(author);
            libBook.setReleaseYear(releaseYear);
            libBook.setStock(numberOfBooks);

            if (!libWorker.doesBookExist(libBook)) {
                libWorker.addNewBookToDbBooks(libBook);
            } else {
                libWorker.addBookToExisting(libBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
