import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class SegmentTreeTester {
    private static final int ARRAY_SIZE = 10_000;
    private static final int SEARCH_TESTS = 100;
    private static final int DELETE_TESTS = 1_000;
    private static final String LOG_FILE = "segment_tree_performance.log";

    public static void main(String[] args) {
        int[] testArray = generateRandomArray(ARRAY_SIZE, 1, 1000);
        System.out.println("Размер массива: " + testArray.length);

        SegmentTree st = new SegmentTree(new int[0]);

        List<Long> insertTimes = new ArrayList<>();
        long totalInsertTime = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            long startTime = System.nanoTime();
            st.append(testArray[i]);
            long duration = System.nanoTime() - startTime;
            insertTimes.add(duration);
            totalInsertTime += duration;
        }
        double avgInsertTimeNs = (double) totalInsertTime / ARRAY_SIZE;
        System.out.printf("Среднее время добавления: %.2f нс/операцию\n", avgInsertTimeNs);

        Random rand = new Random();
        List<Long> searchTimes = new ArrayList<>();
        long totalSearchTime = 0;
        for (int i = 0; i < SEARCH_TESTS; i++) {
            int index = rand.nextInt(st.getSize());
            long startTime = System.nanoTime();
            int value = st.query(index, index);
            long duration = System.nanoTime() - startTime;
            searchTimes.add(duration);
            totalSearchTime += duration;
        }
        double avgSearchTimeNs = (double) totalSearchTime / SEARCH_TESTS;
        System.out.printf("Среднее время поиска: %.2f нс/операцию\n", avgSearchTimeNs);

        List<Long> deleteTimes = new ArrayList<>();
        long totalDeleteTime = 0;
        for (int i = 0; i < DELETE_TESTS; i++) {
            int index = rand.nextInt(st.getSize());
            long startTime = System.nanoTime();
            st.remove(index);
            long duration = System.nanoTime() - startTime;
            deleteTimes.add(duration);
            totalDeleteTime += duration;
        }
        double avgDeleteTimeNs = (double) totalDeleteTime / DELETE_TESTS;
        System.out.printf("Среднее время удаления: %.2f нс/операцию\n", avgDeleteTimeNs);

        saveResultsToFile(insertTimes, searchTimes, deleteTimes);
    }

    private static int[] generateRandomArray(int size, int min, int max) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        return arr;
    }

    private static void saveResultsToFile(List<Long> insertTimes, List<Long> searchTimes, List<Long> deleteTimes) {
        try (FileWriter writer = new FileWriter(LOG_FILE)) {
            writer.write("Operation,Time(ns)\n");

            for (long time : insertTimes) {
                writer.write("INSERT," + time + "\n");
            }

            for (long time : searchTimes) {
                writer.write("SEARCH," + time + "\n");
            }

            for (long time : deleteTimes) {
                writer.write("DELETE," + time + "\n");
            }

            System.out.println("Результаты сохранены в файл: " + LOG_FILE);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}