public class OOP_Laba01 {
    public static class Book {
        private int id;
        private String title;
        private String author;
        private int year;
        public Book(int id, String title, String author, int year){
            this.id = id;
            this.title = title;
            this.author = author;
            this.year = year;
        }
        public void setId(int id){
            this.id = id;
        }
        public void setTitle(String title){
            if (title == null || title.isBlank()) throw new IllegalArgumentException("Название не может быть пустым!");
            this.title = title;
        }
        public void setAuthor(String author){
            if (author == null || author.isBlank()) throw new IllegalArgumentException("Автор не может быть пустым!");
            this.author = author;
        }
        public void setYear(int year){
            if (year < 0 || year > java.time.Year.now().getValue()) throw new IllegalArgumentException("Название не может быть пустым!");
            this.year = year;
        }
        public long getId(){
            return id;
        }
        public String getDescription(){
            return "\"" + title + "\" - " + author + " (" + year + ")";
        }
    }
    public static void main(String[] args) {
        Book firstbook = new Book(1, "Война и мир", "Толстой Л.Н.", 1869);
        Book secondbook = new Book(2, "Мастер и Маргарита", "Булгаков М.А.", 1967);
        Book thirdbook = new Book(3, "Преступление и наказание", "Достоевский Ф.М.", 1866);
        System.out.println(firstbook.getId() + "." + " " + firstbook.getDescription());
        System.out.println(secondbook.getId() + "." + " " + secondbook.getDescription());
        System.out.println(thirdbook.getId() + "." + " " + thirdbook.getDescription());
    }
}