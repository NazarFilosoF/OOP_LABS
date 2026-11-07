package Paket;

public class Book {
    public int id;
    public String title;
    public String author;
    public int year;
    public String isbn;
    public String publisher;
    public Book(int id, String title, String author, int year, String isbn, String publisher) {
        this.id = id;
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setIsbn(isbn);
        setPublisher(publisher);
    }

    // Getters
    public int getId(){ return id; }
    public String getTitle(){ return title; }
    public String getAuthor(){ return author; }
    public int getYear(){ return year; }
    public String getIsbn(){ return isbn; }
    public String getPublisher(){ return publisher; }

    // Setters
    public void setTitle(String title){
        if (title == null || title.isBlank()) {
            throw new InvalidBookDataException("Название не может быть пустым");
        }
        this.title = title;
    }
    public void setAuthor(String author){
        if (author == null || author.isBlank()) {
            throw new InvalidBookDataException("Автор не может быть пустым");
        }
        this.author = author;
    }
    public void setYear(int year){
        if (year <= 0 || year > 2026) {
            throw new InvalidBookDataException("Некорректный год");
        }
        this.year = year;
    }
    public void setIsbn(String isbn){
        if (isbn == null || isbn.isBlank()) {
            throw new InvalidBookDataException("Идентификатор не может быть пустым");
        }
        this.isbn = isbn;
    }
    public void setPublisher(String publisher){
        if (publisher == null || publisher.isBlank()) {
            throw new InvalidBookDataException("Издатель не может быть пустым");
        }
        this.publisher = publisher;
    }
    public String toCsvString() {
        String safeTitle = title.contains(",") ? "\"" + title + "\"" : title;
        return id + "," + safeTitle + "," + author + "," + year + "," + isbn + "," + publisher;
    }
    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title='" + title + '\'' + ", year=" + year + ", isbn='" + isbn + '\'' + '}';
    }
}