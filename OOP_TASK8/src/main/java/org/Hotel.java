package org;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hotel {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("city")
    private String city;
    @JsonProperty("stars")
    private int stars;
    @JsonProperty("roomsCount")
    private int roomsCount;
    @JsonProperty("rating")
    private Double rating;
    @JsonIgnore
    private String managementInfo;
    @JsonProperty("addedDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate addedDate;
    public Hotel() {
        this.addedDate = LocalDate.now();
    }

    @JsonCreator
    public Hotel(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name,
            @JsonProperty("city") String city,
            @JsonProperty("stars") int stars,
            @JsonProperty("roomsCount") int roomsCount,
            @JsonProperty("rating") double rating,
            @JsonProperty("addedDate") LocalDate addedDate) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.stars = stars;
        this.roomsCount = roomsCount;
        this.rating = rating;
        this.addedDate = addedDate != null ? addedDate : LocalDate.now();
    }

    // Getters
    public long getId(){ return id; }
    public String getName(){ return name; }
    public String getCity(){ return city; }
    public int getStars(){ return stars; }
    public int getRoomsCount(){ return roomsCount; }
    public double getRating(){ return rating; }

    // Setters
    public void setName(String name){
        if (name == null || name.isBlank()) {
            throw new InvalidBookDataException("Наименование не может быть пустым");
        }
        this.name = name;
    }
    public void setCity(String city){
        if (city == null || city.isBlank()) {
            throw new InvalidBookDataException("Город не может быть пустым");
        }
        this.city = city;
    }
    public void setStars(int stars){
        if (stars < 1 || stars > 5) {
            throw new InvalidBookDataException("Количество звезд не соответствует реальности");
        }
        this.stars = stars;
    }
    public void setRoomsCount(int roomsCount){
        if (roomsCount <= 0) {
            throw new InvalidBookDataException("Количество комнат не может быть < 0");
        }
        this.roomsCount = roomsCount;
    }
    public void setRating(double rating){
        if (rating <= 0.0 || rating > 10.0) {
            throw new InvalidBookDataException("Нереалистичный рейтинг");
        }
        this.rating = rating;
    }
    public String toCsvString() {
        String safeTitle = name.contains(",") ? "\"" + name + "\"" : name;
        return id + "," + safeTitle + "," + city + "," + stars + "," + roomsCount + "," + rating;
    }
    @Override
    public String toString() {
        return "Hotel{" + "id=" + id + ", Name='" + name + '\'' + ", stars=" + stars + ", roomsCount='" + roomsCount + ", rating='" + rating + '\'' + '}';
    }
}