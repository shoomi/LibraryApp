package library.libitems;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class LibBook implements Serializable, Book {

    private SimpleStringProperty sequenceNumber = new SimpleStringProperty("");
    private SimpleStringProperty title = new SimpleStringProperty("");
    private SimpleStringProperty author = new SimpleStringProperty("");
    private SimpleStringProperty releaseYear = new SimpleStringProperty("");
    private SimpleStringProperty borrowingDate = new SimpleStringProperty("");
    private SimpleStringProperty stock = new SimpleStringProperty("");

    public LibBook(String sequenceNumber, String title, String author, String releaseYear) {
        this.sequenceNumber = new SimpleStringProperty(sequenceNumber);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.releaseYear = new SimpleStringProperty(releaseYear);
    }

    public LibBook(String sequenceNumber, String title, String author, String releaseYear, String borrowingDate) {
        this.sequenceNumber = new SimpleStringProperty(sequenceNumber);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.releaseYear = new SimpleStringProperty(releaseYear);
        this.borrowingDate = new SimpleStringProperty(borrowingDate);
    }

    public LibBook() {
    }

    @Override
    public SimpleStringProperty sequenceNumberProperty() {
        return sequenceNumber;
    }

    @Override
    public SimpleStringProperty titleProperty() {
        return title;
    }

    @Override
    public SimpleStringProperty authorProperty() {
        return author;
    }

    @Override
    public SimpleStringProperty releaseYearProperty() {
        return releaseYear;
    }

    @Override
    public SimpleStringProperty borrowingDateProperty() {
        return borrowingDate;
    }

    @Override
    public SimpleStringProperty stockProperty() {
        return stock;
    }

    @Override
    public String getTitle() {
        return title.get();
    }

    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public String getAuthor() {
        return author.get();
    }

    @Override
    public void setAuthor(String author) {
        this.author.set(author);
    }

    @Override
    public String getReleaseYear() {
        return releaseYear.get();
    }

    @Override
    public void setReleaseYear(String releaseYear) {
        this.releaseYear.set(releaseYear);
    }

    @Override
    public int getStock() {
        return Integer.parseInt(stock.get());
    }

    @Override
    public void setStock(int stock) {
        this.stock.set(String.valueOf(stock));
    }
}
