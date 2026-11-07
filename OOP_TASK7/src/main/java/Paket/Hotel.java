package Paket;

public class Hotel {
    public int id;
    public String name;
    public String city;
    public int stars;
    public int roomsCount;
    public double rating;
    public Hotel(int id, String name, String city, int stars, int roomsCount, double rating) {
        this.id = id;
        setName(name);
        setCity(city);
        setStars(stars);
        setRoomsCount(roomsCount);
        setRating(rating);
    }

    // Getters
    public int getId(){ return id; }
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
    public String getUniqueKey() { return name + "_" + city; }
    public String toCsvString() {
        String safeTitle = name.contains(",") ? "\"" + name + "\"" : name;
        return id + "," + safeTitle + "," + city + "," + stars + "," + roomsCount + "," + rating;
    }
    @Override
    public String toString() {
        return "Hotel{" + "id=" + id + ", Name='" + name + '\'' + ", stars=" + stars + ", roomsCount='" + roomsCount + ", rating='" + rating + '\'' + '}';
    }
}