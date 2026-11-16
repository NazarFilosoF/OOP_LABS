package org;

import java.util.Objects;

public class Hotel {
    private final long id;
    private final String name;
    private final String city;
    private final double rating;
    private final double pricePerNight;
    private final int stars;

    public Hotel(long id, String name, String city,
                 double rating, double pricePerNight, int stars) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.rating = rating;
        this.pricePerNight = pricePerNight;
        this.stars = stars;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public double getRating() { return rating; }
    public double getPricePerNight() { return pricePerNight; }
    public int getStars() { return stars; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        return id == hotel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Hotel{id=%d, name='%s', city='%s', rating=%.1f, price=%.2f, stars=%d}",
                id, name, city, rating, pricePerNight, stars);
    }
}