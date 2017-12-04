package libraryitem;

import javafx.beans.property.SimpleStringProperty;

public interface Book {

    SimpleStringProperty sequenceNumberProperty();

    SimpleStringProperty titleProperty();

    SimpleStringProperty authorProperty();

    SimpleStringProperty releaseYearProperty();

    SimpleStringProperty borrowingDateProperty();

    SimpleStringProperty stockProperty();

    String getTitle();

    void setTitle(String title);

    String getAuthor();

    void setAuthor(String author);

    String getReleaseYear();

    void setReleaseYear(String releaseYear);

    int getStock();

    void setStock(int stock);
}
