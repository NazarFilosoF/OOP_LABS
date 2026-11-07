package org;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ObjectMapper jsonMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();

        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Война и мир", "Толстой Л.Н.", 1869, "978-5-17-118456-0", "АСТ", null));
        books.add(new Book(2, "Мастер и Маргарита", "Булгаков М.А.", 1866, "978-5-17-118457-7", "Эксмо", null));

        //Сериализация в JSON (Запись)
        File jsonFile = new File("src/main/resources/books.json");
        try {
            jsonMapper.writeValue(jsonFile, books);
            System.out.println("=== JSON файл создан: " + jsonFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Десериализация из JSON (Чтение)
        System.out.println("\n=== Чтение из JSON ===");
        try {
            List<Book> readBooks = jsonMapper.readValue(jsonFile, new TypeReference<List<Book>>() {});
            readBooks.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //CSV -> JSON (Имитация)
        System.out.println("\n=== Конвертация CSV в JSON ===");
        convertCsvToJson();

        //Чтение из XML
        System.out.println("\n=== Чтение из XML ===");
        readFromXml();
    }

    // Метод конвертации CSV в JSON (упрощенный)
    private static void convertCsvToJson() {
        // Для простоты создадим CSV на лету
        String csvPath = "src/main/resources/books_source.csv";
        String jsonPath = "src/main/resources/books_from_csv.json";

        try {
            // Создаем тестовый CSV
            Files.writeString(Paths.get(csvPath), "1,Преступление и наказание,Достоевский,1866,Эксмо,978-5-04-116123-4,2023-10-25\n" +
                    "2,Идиот,Достоевский,1868,АСТ,978-5-04-116123-5,2023-10-26");

            // Читаем CSV
            List<Book> csvBooks = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(csvPath));
            for (String line : lines) {
                String[] parts = line.split(",");
                csvBooks.add(new Book(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], parts[5], LocalDate.parse(parts[6])));
            }

            // Записываем в JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(jsonPath), csvBooks);

            System.out.println("Конвертация успешна! Файл: " + jsonPath);
        } catch (IOException e) {
            System.err.println("Ошибка ETL: " + e.getMessage());
        }
    }

    // Метод чтения XML
    private static void readFromXml() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule()); // Поддержка дат для XML

        File xmlFile = new File("src/main/resources/books.xml");

        try {
            // Создаем тестовый XML для чтения (в реальности он придет извне)
            String xmlData = "<ArrayList><item><id>10</id><title>Хоббит</title><author>Толкин</author><addedDate>2023-10-25</addedDate></item></ArrayList>";
            Files.writeString(xmlFile.toPath(), xmlData);

            // Читаем
            List<Book> xmlBooks = xmlMapper.readValue(xmlFile, new TypeReference<List<Book>>() {});
            xmlBooks.forEach(b -> System.out.println("Из XML: " + b));

        } catch (IOException e) {
            System.err.println("Ошибка чтения XML: " + e.getMessage());
        }
    }
}
