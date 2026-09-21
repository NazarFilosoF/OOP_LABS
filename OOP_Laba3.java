// Вариант 14. Иерархия «Печатные издания»
public class OOP_Laba3 {
    public static class Publication {
        public String title;
        public String publisher;
        public int year;
        public Publication(String title, String publisher, int year){
            this.title = title;
            this.publisher = publisher;
            this.year = year;
        }
        public String read(boolean read){
            if (read == false){ 
                return "Не читал"; 
            }
            return "Читал";
        }
        public String getDescription(){
            return "Название: \"" + title + "\"  Автор: " + publisher + " Год выпуска: " + year + " год";
        }
    }
    public static class Book_pagesCount extends Publication{
        private int pageCount;
        public Book_pagesCount(String title, String publisher, int year, int pageCount){
            super(title, publisher, year);
            this.pageCount = pageCount;
        }
        @Override
        public String read(boolean read){
            if (read == false){ 
                return "Не читал"; 
            }
            return "Сколько страниц прочитал?";
        }
        @Override
        public  String getDescription(){
            return "Название: \"" + title + "\"  Автор: " + publisher + " Год выпуска: " + year + " год. Страниц прочитано: " + pageCount;
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
            if (read == false){ 
                return "Не читал"; 
            }
            return "Какой это выпуск?";
        }
        @Override
        public String getDescription(){
            return "Название: \"" + title + "\"  Автор: " + publisher + " Год выпуска: " + year + " год. Выпуск: " + issueNumber;
        }
    }
    public static void main(String[] args){
        Publication[] publications = {
            new Publication("Война и мир", "Толстой Л.Н.", 1869),
            new Book_pagesCount("Мастер и Маргарита", "Булгаков М.А.", 1967,215),
            new Magazine_issueNumber("Преступление и наказание", "Достоевский Ф.М.", 1866, 5)
        };
        for (Publication p : publications){
            System.out.println(p.getDescription() + " , " + p.read(true)); 
        }
    }
}
