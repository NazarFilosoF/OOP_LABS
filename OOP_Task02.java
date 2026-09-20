public class OOP_Task02 {
    public static class VideoGame {
        private int id;
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
        public static createVideoGame(String title, String genre, double raiting, double price){
            return new VideoGame(title, genre, raiting, price);
        }
        public VideoGame(String title, String genre, double price){
            this(title, genre, "Не указан", price);
        }
        public VideoGame(String title, double raiting, double price){
            this(title, "Не указан", raiting, price);
        }
        public VideoGame(String title, double price){
            this(title, "Не указан", "Не указан", price);
        }
        public static setCount(int Count){
            VideoGame.Count =  Count;
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
        public static int getCount(int Count){
            return Count;
        }
        public long getId(){
            return id;
        }
        public String getDescription(){
            return "Игра \"" + title + "\" (жанр " + genre + ", рейтинг " + raiting + ") - " + price + " руб. ";
        }
        public String getDescription(boolean Withoutprice){
            if(Withoutprice){
                return "Игра \"" + title + "\" (жанр " + genre + ", рейтинг " + raiting + ")";
            }
            return getDescription();
        }
        public String getDescription(boolean Name, boolean Price){
            if(Name && Price == false){
                return "Игра \"" + title + "\"";
            }
            if(Name == false && Price){
                return price + " руб. ";
            }
            if(Name == true && Price == true) {
                return "Игра \"" + title + "\" стоит: " + price + " руб. ";
            }
            return getDescription();
        }
    }
    public static void main(String[] args) {
        VideoGame game = new VideoGame("The Witcher 3", "RPG", 9.5, 999.00);
        System.out.println(game.getId() + "." + " " + game.getDescription());
        game = new VideoGame("Sons Of The Forest", "Survival", 8.7, 1300.00);
        System.out.println(game.getId() + "." + " " + game.getDescription(true));
        game = new VideoGame("Terraria", "Sandbox", 9.2, 300.00);
        System.out.println(game.getId() + "." + " " + game.getDescription(true, true));
    }
}