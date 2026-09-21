// Вариант 14. Иерархия «Печатные издания»
// Базовый класс Publication: title, publisher, year. Метод read(). Подкласс Book: pagesCount. Переопределение read(). Подкласс Magazine: issueNumber. Переопределение read().

public class OOP_Laba3 {
    public static class Publication {
        private String title;
        private String publisher;
        private int year;
        public Publication(String title, String publisher, int year){
            this.title = title;
            this.publisher = publisher;
            this.year = year;
        }
        public String read(boolean read){
            if (read){ 
                return "Читал"; 
            }
            return "Не читал";
        }
        public String getDescription(){
            return "Название: \"" + title + "\"  Автор: " + publisher + " Год выпуска: " + year + " год";
        }
    public static class Book_pagesCount extends Publication{
        private int pageCount;
        public Book_pagesCount(String title, String publisher, int year, int pageCount){
            super(title, publisher, year);
            this.pageCount = pageCount;
        }
        @Override
        public String read(boolean read){
            if (read){ 
                System.out.println("Сколько страниц прочитал?"); 
            }
            return "Не читал";
        }
        @Override
        public String getDescription(){
            
            return "Название: \"" + title + "\"  Автор: " + publisher + " Год выпуска: " + year + " год";
        }
    }
    public static class Magazine_issueNumber extends Publication{
        private int issueNumber;
        public Magazine_issueNumber(String title, String publisher, int year, int issueNumber){
            super(title, publisher, year);
            this.issueNumber = issueNumber;
        }
        @Override
        public String read(boolean read){
            if (read){ 
                System.out.println("Какое количество ошибок?"); 
            }
            return "Не читал";
        }
    }
    }
    public static void main(String[] args){
        Publication[] publications = {
            new Publication("Война и мир", "Толстой Л.Н.", 1869),
            new Book_pagesCount("Мастер и Маргарита", "Булгаков М.А.", 1967,215),
            new Magazine_issueNumber("Преступление и наказание", "Достоевский Ф.М.", 1866, 5)
        };
        for (Publication p : publications){
            System.out.println(p.); 
        }
    }
}
