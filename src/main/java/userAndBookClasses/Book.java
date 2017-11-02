package userAndBookClasses;

import java.io.Serializable;

public class Book {

    private int nn;
    private String title;
    private String author;
    private String releaseDate;
    private String borrowingDate;
    private int stock;

    public Book(int nn, String title, String author, String releaseDate) {
        this.nn = nn;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public Book(int nn, String title, String author, String releaseDate, String borrowingDate) {
        this.nn = nn;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.borrowingDate = borrowingDate;
    }

    public Book() {
    }

    public int getNn() {
        return nn;
    }

    public void setNn(int nn) {
        this.nn = nn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(String borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
