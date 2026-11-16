package org;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class HotelLoadTask implements Callable<List<Hotel>> {
    private final String fileName;
    private final String threadName;

    public HotelLoadTask(String fileName, String threadName) {
        this.fileName = fileName;
        this.threadName = threadName;
    }

    @Override
    public List<Hotel> call() throws Exception {
        long start = System.currentTimeMillis();
        List<Hotel> hotels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // заголовок
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                Hotel hotel = new Hotel(
                        Long.parseLong(parts[0].trim()),
                        parts[1].trim(),
                        parts[2].trim(),
                        Double.parseDouble(parts[3].trim()),
                        Double.parseDouble(parts[4].trim()),
                        Integer.parseInt(parts[5].trim())
                );
                hotels.add(hotel);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.printf("[%s] Ошибка чтения %s: %s%n",
                    threadName, fileName, e.getMessage());
            throw e;
        }

        long duration = System.currentTimeMillis() - start;
        System.out.printf("%s: %d отелей, %d мс (%s)%n",
                fileName, hotels.size(), duration,
                Thread.currentThread().getName());
        return hotels;
    }
}