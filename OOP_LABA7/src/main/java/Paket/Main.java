package Paket;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputFile = "src/main/resources/books.csv";
        String outputFile = "src/main/resources/books_clean.csv";

        System.out.println("=== ПАРСИНГ ЧЕРЕЗ OPenCSV ===");
        parseWithBufferedReader(inputFile, outputFile);

        System.out.println("\n=== ПАРСИНГ ЧЕРЕЗ STREAM API ===");
        parseWithStreamApi(inputFile);
    }

    // --- МЕТОД 1: OpenCSV ---
    private static void parseWithBufferedReader(String filePath, String outputFile) {
        int totalLines = 0;
        int successCount = 0;
        int errorCount = 0;
        int duplicateCount = 0;

        Set<String> seenIsbns = new HashSet<>();
        List<Book> validBooks = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // Пропускаем заголовок

            while ((line = reader.readNext()) != null) {
                totalLines++;
                try {
                    if (line.length < 6) {
                        throw new InvalidBookDataException("Недостаточно полей");
                    }

                    int id = Integer.parseInt(line[0].trim());
                    String title = line[1].trim();
                    String author = line[2].trim();
                    int year = Integer.parseInt(line[3].trim()); // Может выбросить NumberFormatException
                    String isbn = line[4].trim();
                    String publisher = line[5].trim();

                    // Проверка на дубликат ДО создания объекта (чтобы не тратить память)
                    if (seenIsbns.contains(isbn)) {
                        duplicateCount++;
                        continue;
                    }

                    // Создаем книгу (валидация произойдет ВНУТРИ конструктора)
                    Book book = new Book(id, title, author, year, isbn, publisher);

                    seenIsbns.add(isbn);
                    validBooks.add(book);
                    successCount++;

                } catch (NumberFormatException e) {
                    errorCount++;
                    System.out.println("Ошибка (нечисловой год): строка " + totalLines);
                } catch (InvalidBookDataException e) {
                    errorCount++;
                    System.out.println("Ошибка валидации (" + e.getMessage() + "): строка " + totalLines);
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        printStats(totalLines, successCount, errorCount, duplicateCount);
        saveToCsv(validBooks, outputFile);
    }

    // --- МЕТОД 2: Stream API ---
    private static void parseWithStreamApi(String filePath) {
        int totalLines = 0;
        int successCount = 0;
        int errorCount = 0;
        int duplicateCount = 0;

        Set<String> seenIsbns = new HashSet<>();
        List<Book> validBooks = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<String> dataLines = lines.subList(1, lines.size());
            totalLines = dataLines.size();

            for (String line : dataLines) {
                try {
                    String[] parts = splitCsvLine(line);

                    if (parts.length < 6) throw new InvalidBookDataException("Недостаточно полей");

                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    String isbn = parts[4].trim();
                    String publisher = parts[5].trim();

                    if (seenIsbns.contains(isbn)) {
                        duplicateCount++;
                        continue;
                    }

                    // Создаем книгу (валидация ВНУТРИ)
                    Book book = new Book(id, title, author, year, isbn, publisher);
                    seenIsbns.add(isbn);
                    validBooks.add(book);
                    successCount++;

                } catch (NumberFormatException e) {
                    errorCount++;
                    System.out.println("Ошибка (нечисловой год): " + line);
                } catch (InvalidBookDataException e) {
                    errorCount++;
                    System.out.println("Ошибка валидации (" + e.getMessage() + "): " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        printStats(totalLines, successCount, errorCount, duplicateCount);
    }

    // Вспомогательный метод для парсинга кавычек
    private static String[] splitCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString());
        return result.toArray(new String[0]);
    }

    private static void printStats(int total, int success, int error, int dup) {
        System.out.println("Всего строк: " + total);
        System.out.println("Успешно: " + success);
        System.out.println("Ошибок: " + error);
        System.out.println("Дубликатов: " + dup);
    }

    private static void saveToCsv(List<Book> books, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("id,title,author,year,isbn,publisher\n");
            for (Book b : books) {
                writer.write(b.toCsvString() + "\n");
            }
            System.out.println("Результат сохранен в " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }
    }
}