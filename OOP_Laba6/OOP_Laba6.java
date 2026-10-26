import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OOP_Laba6 {
    public static class BookNotFoundException extends Exception {
        public BookNotFoundException(String message) { super(message); }
    }
    public static class InvalidBookDataException extends RuntimeException {
        public InvalidBookDataException(String message) { super(message); }
    }
    public static class Book {
        private static final Logger log = LoggerFactory.getLogger(Book.class);
        private long id;
        private String title;
        private String author;
        private int year;

        public Book(long id, String title, String author, int year) {
            this.id = id;
            setTitle(title);
            setAuthor(author);
            setYear(year);
        }

        public long getId() { return id; }
        public void setTitle(String title) {
            if (title == null || title.isBlank()) {
                log.error("Ошибка валидации: title не может быть пустым");
                throw new InvalidBookDataException("title не может быть пустым");
            }
            this.title = title;
        }
        public void setAuthor(String author) {
            if (author == null || author.isBlank()) {
                log.error("Ошибка валидации: author не может быть пустым");
                throw new InvalidBookDataException("author не может быть пустым");
            }
            this.author = author;
        }
        public void setYear(int year) {
            int currentYear = Year.now().getValue();
            if (year <= 0) {
                log.error("Ошибка валидации: year должно быть > 0");
                throw new InvalidBookDataException("year должно быть > 0");
            }
            if (year > currentYear) {
                log.error("Ошибка валидации: year не может быть больше {}", currentYear);
                throw new InvalidBookDataException("year не может быть больше " + currentYear);
            }
            this.year = year;
        }
    }
    public static class Repository {
        private static final Logger log = LoggerFactory.getLogger(Repository.class);
        private final List<Book> books = new ArrayList<>();

        public void save(Book book) {
            books.add(book);
            log.info("Книга успешно сохранена: id={}", book.getId());
        }

        public Book findById(long id) throws BookNotFoundException {
            for (Book b : books) {
                if (b.getId() == id) return b;
            }
            log.warn("Попытка получить книгу с id {}", id);
            throw new BookNotFoundException("Книга с id " + id + " не найдена");
        }
    }
    public static void main(String[] args) {
        Repository repo = new Repository();

        System.out.println("=== 1. Сохранение ===");
        try {
            repo.save(new Book(1, "Война и мир", "Толстой Л.Н.", 1869));
            repo.save(new Book(2,"Мастер и Маргарита","Булгаков М.А",1967));
            repo.save(new Book(3,"Ошибка года","Автор",9999));
        } catch (InvalidBookDataException e) {
            System.out.println("Поймали ошибку при создании: " + e.getMessage());
        }
        

        System.out.println("\n=== 2. Поиск (WARN + checked Exception) ===");
        try {
            repo.findById(999);
        } catch (BookNotFoundException e) {
            System.out.println("Поймали ошибку: " + e.getMessage());
        }

        System.out.println("\n=== 3. Валидация (ERROR + unchecked Exception) ===");
        try {
            new Book(2, "Ошибка", "Автор", -5);
        } catch (InvalidBookDataException e) {
            System.out.println("Поймали ошибку: " + e.getMessage());
        }

        System.out.println("\n=== 4. try-with-resources (Чтение файла) ===");
        try (BufferedReader reader = new BufferedReader(new FileReader("books.csv"))) {
            String line = reader.readLine();
            System.out.println("Прочитана строка: " + line);
        } catch (IOException e) {
            System.out.println("Файл не найден или ошибка чтения: " + e.getMessage());
        }
    }
}