// Вариант 14. Иерархия «Отели»
// Сущность Hotel: id, name, city, stars, roomsCount, rating
// Исключения:
// HotelNotFoundException (checked)
// InvalidHotelDataException (unchecked)
// Валидация: name не null/пустой, stars ∈ [1, 5], roomsCount > 0

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OOP_Task6 {
    public static class HotelNotFoundException extends Exception {
        public HotelNotFoundException(String message) { super(message); }
        public HotelNotFoundException(String message, Throwable cause) { super(message, cause); }
    }
    public static class InvalidHotelDataException extends RuntimeException {
        public InvalidHotelDataException(String message) { super(message); }
        public InvalidHotelDataException(String message, Throwable cause) { super(message, cause); }
    }

    public static class Hotel {
        private static final Logger log = LoggerFactory.getLogger(Hotel.class);
        private long id;
        private String name;
        private String city;
        private int stars;
        private int roomsCount;
        private double rating;

        public Hotel(long id, String name, String city, int stars, int roomsCount, double rating) {
            this.id = id;
            setName(name);
            this.city = city;
            setStars(stars);
            setRoomsCount(roomsCount);
            this.rating = rating;
        }

        public long getId() { return id; }
        public String getName() { return name; }

        public void setName(String name) {
            if (name == null || name.isBlank()) {
                log.error("Ошибка валидации: name не может быть пустым");
                throw new InvalidHotelDataException("name не может быть пустым");
            }
            this.name = name;
        }

        public void setStars(int stars) {
            if (stars < 1 || stars > 5) {
                log.error("Ошибка валидации: stars должно быть от 1 до 5. Передано: {}", stars);
                throw new InvalidHotelDataException("stars должно быть от 1 до 5");
            }
            this.stars = stars;
        }

        public void setRoomsCount(int roomsCount) {
            if (roomsCount <= 0) {
                log.error("Ошибка валидации: roomsCount должно быть > 0. Передано: {}", roomsCount);
                throw new InvalidHotelDataException("roomsCount должно быть > 0");
            }
            this.roomsCount = roomsCount;
        }

        @Override
        public String toString() {
            return String.format("Hotel{id=%d, name='%s', city='%s', stars=%d, rooms=%d, rating=%.1f}",
                    id, name, city, stars, roomsCount, rating);
        }
    }

    public static class HotelRepository {
        private static final Logger log = LoggerFactory.getLogger(HotelRepository.class);
        private final List<Hotel> hotels = new ArrayList<>();

        public void save(Hotel hotel) {
            hotels.add(hotel);
            log.info("Отель успешно сохранен: id={}, название='{}'", hotel.getId(), hotel.getName());
        }

        public Hotel findById(long id) throws HotelNotFoundException {
            for (Hotel h : hotels) {
                if (h.getId() == id) return h;
            }
            log.warn("Попытка получить отель с id {} — не найден", id);
            throw new HotelNotFoundException("Отель с id " + id + " не найден");
        }

        public void delete(long id) throws HotelNotFoundException {
            for (int i = 0; i < hotels.size(); i++) {
                if (hotels.get(i).getId() == id) {
                    hotels.remove(i);
                    log.info("Отель с id {} успешно удален", id);
                    return;
                }
            }
            log.warn("Попытка удалить отель с id {} — не найден", id);
            throw new HotelNotFoundException("Отель с id " + id + " не найден для удаления");
        }
    }

    public static void main(String[] args) {
        HotelRepository repo = new HotelRepository();

        System.out.println("=== 1. Сохранение отелей (INFO) ===");
        repo.save(new Hotel(1, "Ритц Карлтон", "Москва", 5, 300, 9.8));
        repo.save(new Hotel(2, "Ибис", "Санкт-Петербург", 3, 150, 8.2));

        System.out.println("\n=== 2. Поиск несуществующего отеля (WARN + Checked Exception) ===");
        try {
            repo.findById(999);
        } catch (HotelNotFoundException e) {
            System.out.println("Поймали исключение: " + e.getMessage());
        }

        System.out.println("\n=== 3. Валидация (ERROR + Unchecked Exception) ===");
        try {
            new Hotel(3, "Плохой отель", "Сочи", 0, 50, 5.0);
        } catch (InvalidHotelDataException e) {
            System.out.println("Поймали исключение: " + e.getMessage());
        }

        System.out.println("\n=== 4. Удаление (INFO) ===");
        try {
            repo.delete(1);
            System.out.println("Отель с id 1 удален.");
            
            repo.delete(1);
        } catch (HotelNotFoundException e) {
            System.out.println("Поймали исключение: " + e.getMessage());
        }
    }
}
