package operations;

import libworker.LibWorker;
import libitems.LibBook;

import java.sql.SQLException;

public class RegBookInDb {

    private LibWorker libWorker;

    public RegBookInDb() {
        libWorker = new LibWorker();
    }

    public void addNewBook(String title, String author, String releaseYear, int numberOfBooks) {

        LibBook libBook = new LibBook();


        libBook.setTitle(title);
        libBook.setAuthor(author);
        libBook.setReleaseYear(releaseYear);
        libBook.setStock(numberOfBooks);

        if (!libWorker.doesBookExist(libBook)) {
            System.out.println(libBook);
            libWorker.addNewBookToDbBooks(libBook);
        } else {
            libWorker.addBookToExisting(libBook);
        }


    }


}
