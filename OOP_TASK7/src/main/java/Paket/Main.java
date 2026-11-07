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
        String inputFile = "src/main/resources/hotels.csv";
        String outputFile = "src/main/resources/hotels_clean.csv";

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

        Set<String> seenKeys = new HashSet<>();
        List<Hotel> validHotels = new ArrayList<>();

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
                    String name = line[1].trim();
                    String city = line[2].trim();
                    int stars = Integer.parseInt(line[3].trim());
                    int roomsCount = Integer.parseInt(line[4].trim());
                    double rating = Double.parseDouble(line[5].trim());

                    Hotel hotel = new Hotel(id, name, city, stars, roomsCount, rating);

                    if (seenKeys.contains(hotel.getUniqueKey())) {
                        duplicateCount++;
                        continue;
                    }

                    seenKeys.add(hotel.getUniqueKey());
                    validHotels.add(hotel);
                    successCount++;

                } catch (NumberFormatException e) {
                    errorCount++;
                    System.out.println("Ошибка (нечисловые данные): строка " + totalLines);
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
        saveToCsv(validHotels, outputFile);

        filterByCity(validHotels, "Москва");
    }

    // --- МЕТОД 2: Stream API ---
    private static void parseWithStreamApi(String filePath) {
        int totalLines = 0;
        int successCount = 0;
        int errorCount = 0;
        int duplicateCount = 0;

        Set<String> seenKeys = new HashSet<>();
        List<Hotel> validHotels = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<String> dataLines = lines.subList(1, lines.size());
            totalLines = dataLines.size();

            for (String line : dataLines) {
                try {
                    String[] parts = splitCsvLine(line);

                    if (parts.length < 6) throw new InvalidBookDataException("Недостаточно полей");

                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String city = parts[2].trim();
                    int stars = Integer.parseInt(parts[3].trim());
                    int roomsCount = Integer.parseInt(parts[4].trim());
                    double rating = Double.parseDouble(parts[5].trim());

                    Hotel hotel = new Hotel(id, name, city, stars, roomsCount, rating);

                    if (seenKeys.contains(hotel.getUniqueKey())) {
                        duplicateCount++;
                        continue;
                    }

                    seenKeys.add(hotel.getUniqueKey());
                    validHotels.add(hotel);
                    successCount++;

                } catch (NumberFormatException e) {
                    errorCount++;
                    System.out.println("Ошибка (нечисловые данные): " + line);
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

    private static void filterByCity(List<Hotel> hotels, String targetCity) {
        System.out.println("\n=== ВЫБОРКА ОТЕЛЕЙ В ГОРОДЕ: " + targetCity + " ===");
        boolean found = false;

        // Используем Stream API для фильтрации (современно и коротко)
        List<Hotel> filtered = hotels.stream()
                .filter(h -> h.getCity().equalsIgnoreCase(targetCity))
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("Отелей в этом городе не найдено.");
        } else {
            filtered.forEach(System.out::println);
        }
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

    private static void saveToCsv(List<Hotel> hotel, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("id,name,city,stars,roomsCount,rating\n");
            for (Hotel h : hotel) {
                writer.write(h.toCsvString() + "\n");
            }
            System.out.println("Результат сохранен в " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }
    }
}