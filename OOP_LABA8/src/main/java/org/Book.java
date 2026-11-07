package org;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    @JsonProperty("id")
    private long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private String author;
    @JsonProperty("year")
    private int year;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("publisher")
    private String publisher;
    @JsonIgnore
    private String internalId;
    @JsonProperty("addedDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate addedDate;
    public Book() {
        this.addedDate = LocalDate.now();
    }

    @JsonCreator
    public Book(
            @JsonProperty("id") long id,
            @JsonProperty("title") String title,
            @JsonProperty("author") String author,
            @JsonProperty("year") int year,
            @JsonProperty("isbn") String isbn,
            @JsonProperty("publisher") String publisher,
            @JsonProperty("addedDate") LocalDate addedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
        this.publisher = publisher;
        this.addedDate = addedDate != null ? addedDate : LocalDate.now();
    }

    // Getters
    public long getId(){ return id; }
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