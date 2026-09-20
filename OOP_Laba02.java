public class OOP_Laba02 {
    public static class Book {
        private long id;
        private String title;
        private String author;
        private int year;
        private static int objcount = 0;
        public Book(String title, String author, int year){
            this.id = ++objcount;
            this.title = title;
            this.author = author;
            this.year = year;
        }
        public static createBook(String title, String author, int year){
            return new Book(title, author, year);
        }
        public static int getCount(){
            return objcount;
        }
        public static void setCount(int objcount){
            Book.objcount = objcount;
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
        public String getDescription(boolean shortFormat) {
            if (shortFormat) {
                return title + " (" + year + ")";
            }
            return getDescription();
        }
    }
    public static void main(String[] args) {
        Book book = new Book("Война и мир", "Толстой Л.Н.", 1869);
        System.out.println(book.getId() + "." + " " + book.getDescription());
        book = new Book("Мастер и Маргарита", "Булгаков М.А.", 1967);
        System.out.println(book.getId() + "." + " " + book.getDescription(true));
        book = new Book("Преступление и наказание", "Достоевский Ф.М.", 1866);
        System.out.println(book.getId() + "." + " " + book.getDescription());
    }
}