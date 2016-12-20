package by.bsuir.cs.model;

/**
 * Created by Danilau_MO on 20.12.2016.
 */
public class Book {

    private String name;
    private String author;
    private Integer number;
    private String posterPath;
    private String description;
    private String year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!name.equals(book.name)) return false;
        if (!author.equals(book.author)) return false;
        if (!number.equals(book.number)) return false;
        if (!posterPath.equals(book.posterPath)) return false;
        if (!description.equals(book.description)) return false;
        return year.equals(book.year);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + posterPath.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + year.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", number=" + number +
                ", posterPath='" + posterPath + '\'' +
                ", description='" + description + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
