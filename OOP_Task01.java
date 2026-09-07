public class OOP_Task01 {
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
    public static void main(String[] args) {
        VideoGame firstgame = new VideoGame(1, "The Witcher 3", "RPG", 9.5, 999.00);
        VideoGame secondgame = new VideoGame(2, "Sons Of The Forest", "Survival", 8.7, 1300.00);
        VideoGame thirdgame = new VideoGame(3, "Terraria", "Sandbox", 9.2, 300.00);
        System.out.println(firstgame.getId() + "." + " " + firstgame.getDescription());
        System.out.println(secondgame.getId() + "." + " " + secondgame.getDescription());
        System.out.println(thirdgame.getId() + "." + " " + thirdgame.getDescription());
    }
}