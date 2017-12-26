package library.operations;

import library.LibWorker;
import library.libitems.LibBook;

public class RegisterBookInDb {

    private LibWorker libWorker;

    public RegisterBookInDb() {
        libWorker = new LibWorker();
    }

    public void addNewBook(String title, String author, String releaseYear, int numberOfBooks) {

        LibBook libBook = new LibBook();

        libBook.setTitle(title);
        libBook.setAuthor(author);
        libBook.setReleaseYear(releaseYear);
        libBook.setStock(numberOfBooks);

        if (!libWorker.doesTheBookExist(libBook)) {
            libWorker.addNewBookToDb(libBook);
        } else {
            libWorker.addBookToExisting(libBook);
        }

    }


}
