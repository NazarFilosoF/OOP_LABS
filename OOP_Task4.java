import java.util.*;
import java.util.stream.Collectors;

public class OOP_Task4 {
    public static class VideoGame {
        private long id;
        private String title;
        private String genre;
        private double raiting;
        private double price;
        private static int Count;
        public VideoGame(String title, String genre, double raiting, double price){
            this.id = ++Count;
            this.title = title;
            this.genre = genre;
            this.raiting = raiting;
            this.price = price;
        }
        public static void setCount(int Count){
            VideoGame.Count = Count;
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
            if (raiting < 0 || raiting > 10) throw new IllegalArgumentException("Рейтинг не может быть пустым!");
            this.raiting = raiting;
        }
        public void setPrice(double price){
            if (price < 0 || price > 10000) throw new IllegalArgumentException("Что за цена!");
            this.price = price;
        }
        public static int getCount(){return Count;}
        public long getId(){return id;}
        public String getTitle(){return title;}
        public String getGenre(){return genre;}
        public double getRaiting(){return raiting;}
        public double getPrice(){return price;}
        public String getDescription(){
            return "Игра \"" + title + "\" (жанр " + genre + ", рейтинг " + raiting + ") - " + price + " руб. ";
        }
        @Override 
        public String toString() {
            return String.format("Игра \"" + title + "\" (жанр " + genre + ", рейтинг " + raiting + ") - " + price + " руб. ");
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VideoGame game = (VideoGame) o;
            return price == game.price &&
                raiting == game.raiting &&
                Objects.equals(title, game.title) &&
                Objects.equals(genre, game.genre);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, genre, raiting, price);
        }
    }
    public static void printGames(String Title, List<VideoGame> games) {
        System.out.println(Title + ": ");
        for (VideoGame g : games) {
            System.out.println(g);
        }
    }
    public static void main(String[] args){
        List<VideoGame> games = new ArrayList<>();
        games.add(new VideoGame("The Witcher 3", "RPG", 9.5, 1000.00));
        games.add(new VideoGame("Sons Of The Forest", "Survival", 8.7, 1500.00));
        games.add(new VideoGame("Terraria", "Sandbox", 9.2, 300.00));
        games.add(new VideoGame("Escape From Tarkov", "ExtractionShooter", 7.9, 2200.00));
        games.add(new VideoGame("The Forest", "Survival", 9.2, 400.00));
        games.add(new VideoGame("Detroit: Become Human", "InteractiveCinema", 9.6, 800.00));
        games.add(new VideoGame("Hotline Maiami", "Shooter", 10.0, 400.00));
        games.add(new VideoGame("TombRaider", "Survival", 8.2, 500.00));
        games.add(new VideoGame("KingdomeCome", "RPG", 8.9, 2000.00));
        games.add(new VideoGame("Dark Souls 3", "SoulsLike", 7.5, 1200.00));


        List<VideoGame> sortGames = new ArrayList<>(games);  // Создание сортированных списков
        Set<VideoGame> SetGames = new HashSet<>(games);
        sortGames.sort(Comparator.comparing(VideoGame::getTitle));
        printGames("Сортировка по Названию", sortGames);
        sortGames.sort(Comparator.comparing(VideoGame::getGenre));
        printGames("Сортировка по Жанру", sortGames);
        sortGames.sort(Comparator.comparing(VideoGame::getRaiting));
        printGames("Сортировка по Рейтингу", sortGames);


        // Map<String, List<VideoGame>> groupGames = new HashMap<>();  // Создание сгруппированного словаря
        // for (VideoGame game : games){
        //     groupGames.computeIfAbsent(game.getGenre(), k -> new ArrayList<>()).add(game);
        // }

        Map<String, List<VideoGame>> groupGames = games.stream().collect(Collectors.groupingBy(VideoGame::getGenre)); // Альтернативный вариант группировки

        for (Map.Entry<String, List<VideoGame>> pair : groupGames.entrySet()) {
            System.out.println("Жанр: " + pair.getKey());
            for (VideoGame game : pair.getValue()) {
                System.out.println("  " + game);
            }
        }


        for (Map.Entry<String, List<VideoGame>> pair : groupGames.entrySet()) {  // Агреруем среднюю цену по жанрам
            System.out.println("Средняя цена: " + pair.getValue().stream().mapToDouble(VideoGame::getPrice).average().orElse(0.0) + " руб. по жанру: " + pair.getKey());
        }


        List<VideoGame> filteredGames = games.stream().filter(game -> game.getRaiting() >= 8.0).collect(Collectors.toList());  // Фильтруем по рейтингу >= 8.0
        printGames("Игры рейтинг которых >= 8.0", filteredGames);

        
    }
}
// Поля: id, title, genre, rating, price. Сортировка: по названию, по жанру, по рейтингу. 
// Группировка: по жанру. Фильтрация: игры с рейтингом ≥ 8.0. Агрегация: средняя цена по жанрам.