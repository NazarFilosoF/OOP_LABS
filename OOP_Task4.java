// Поля: id, title, genre, rating, price. Сортировка: по названию, по жанру, по рейтингу. 
// Группировка: по жанру. Фильтрация: игры с рейтингом ≥ 8.0. Агрегация: средняя цена по жанрам.

import java.util.ArrayList;
import java.util.List;

public class OOP_Task4 {
    public static class VideoGame {
        private int id;
        private String title;
        private String genre;
        private double raiting;
        private double price;
        public VideoGame(int id, String title, String genre, double raiting, double price){
            this.id = id;
            this.title = title;
            this.genre = genre;
            this.raiting = raiting;
            this.price = price;
        }
        public void setId(int id){
            this.id = id;
        }
        public void setTitle(String title){
            if (title == null) throw new IllegalArgumentException("Название не может быть пустым!");
            this.title = title;
        }
        public void setGenre(String genre){
            if (genre == null) throw new IllegalArgumentException("Жанр не может быть пустым!");
            this.genre = genre;
        }
        public void setRaiting(double raiting){
            if (raiting == 0) throw new IllegalArgumentException("Рейтинг не может быть пустым!");
            this.raiting = raiting;
        }
        public void setPrice(double price){
            if (price < 0 || price > 10000) throw new IllegalArgumentException("Что за цена!");
            this.price = price;
        }
        public long getId(){
            return id;
        }
        public String getDescription(){
            return "Игра \"" + title + "\" (жанр " + genre + ", рейтинг " + raiting + ") - " + price + " руб. ";
        }
    }
    public static void main(String[] args){
        List<VideoGame> games = new ArrayList<>();
        games.add(new VideoGame(1, "The Witcher 3", "RPG", 9.5, 999.00));
        games.add(new VideoGame(2, "Sons Of The Forest", "Survival", 8.7, 1300.00));
        games.add(new VideoGame(3, "Terraria", "Sandbox", 9.2, 300.00));
        games.add(new VideoGame(4, "Escape From Tarkov", "ExtractionShooter", 7.9, 2200.00));
        games.add(new VideoGame(5, "The Forest", "Survival", 9.2, 400.00));
        games.add(new VideoGame(6, "Detroit: Become Human", "InteractiveCinema", 9.6, 800.00));
        games.add(new VideoGame(7, "Hotline Maiami", "Shooter", 10.0, 400.00));
        games.add(new VideoGame(8, "TombRaider", "Survival", 8.2, 500.00));
        games.add(new VideoGame(9, "KingdomeCome", "RPG", 8.9, 2000.00));
        games.add(new VideoGame(10, "Dark Souls 3", "SoulsLike", 7.5, 1200.00));
    }
}
