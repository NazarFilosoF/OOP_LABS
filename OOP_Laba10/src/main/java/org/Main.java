package org;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Main {

    private static final String[] FILES = {
            "hotels_1.csv", "hotels_2.csv", "hotels_3.csv", "hotels_4.csv", "hotels_5.csv"
    };
    private static final int ROWS_PER_FILE = 8_000;
    private static final int THREADS = 3;
    private static final long TIMEOUT_SECONDS = 25;

    public static void main(String[] args) throws Exception {

        // 2. Последовательная загрузка
        System.out.println("=== Последовательная загрузка ===");
        long seqStart = System.currentTimeMillis();
        List<Hotel> sequentialResult = new ArrayList<>();
        for (String file : FILES) {
            HotelLoadTask task = new HotelLoadTask(file, "seq");
            try {
                sequentialResult.addAll(task.call());
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
        long seqTime = System.currentTimeMillis() - seqStart;

        long totalSeq = sequentialResult.size();
        long filteredSeq = sequentialResult.stream()
                .filter(h -> h.getRating() > 4.0)
                .count();

        System.out.println("Всего отелей: " + totalSeq);
        System.out.println("После фильтрации (rating > 4.0): " + filteredSeq);
        System.out.println("Время: " + seqTime + " мс");
        System.out.println();

        // 3. Параллельная загрузка
        System.out.println("=== Параллельная загрузка (пул из " + THREADS + " потоков) ===");
        long parStart = System.currentTimeMillis();
        List<Hotel> parallelResult = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        List<Future<List<Hotel>>> futures = new ArrayList<>();

        for (String file : FILES) {
            HotelLoadTask task = new HotelLoadTask(file, "par");
            futures.add(executor.submit(task));
        }

        for (int i = 0; i < futures.size(); i++) {
            Future<List<Hotel>> future = futures.get(i);
            String fileName = FILES[i];
            try {
                List<Hotel> hotels = future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
                parallelResult.addAll(hotels);
            } catch (TimeoutException e) {
                System.err.printf("[TIMEOUT] Файл %s не загружен за %d сек — задача отменена%n",
                        fileName, TIMEOUT_SECONDS);
                future.cancel(true);
            } catch (ExecutionException e) {
                System.err.printf("[ERROR] Файл %s: %s%n",
                        fileName, e.getCause().getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[INTERRUPTED] Загрузка прервана");
                break;
            }
        }

        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }

        long parTime = System.currentTimeMillis() - parStart;

        long totalPar = parallelResult.size();

        // Удаление дубликатов по id
        Map<Long, Hotel> uniqueById = parallelResult.stream()
                .collect(Collectors.toMap(Hotel::getId, h -> h, (a, b) -> a, LinkedHashMap::new));
        List<Hotel> unique = new ArrayList<>(uniqueById.values());

        // Фильтрация по рейтингу
        List<Hotel> filtered = unique.stream()
                .filter(h -> h.getRating() > 4.0)
                .collect(Collectors.toList());

        System.out.println("Всего отелей: " + totalPar);
        System.out.println("После удаления дубликатов: " + unique.size());
        System.out.println("После фильтрации (rating > 4.0): " + filtered.size());
        System.out.println("Время: " + parTime + " мс");
        System.out.println();

        // 4. Сравнение
        System.out.println("=== Сравнение ===");
        double speedup = seqTime / (double) Math.max(parTime, 1);
        System.out.printf("Ускорение: %.2fx%n", speedup);
    }
}