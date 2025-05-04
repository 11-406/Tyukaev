import java.io.*;
import java.util.Random;

public class DataGenerator {
    public static void main(String[] args) {
        try {
            generateTestData();
            System.out.println("Test data generated successfully!");
        } catch (IOException e) {
            System.err.println("Error generating test data: " + e.getMessage());
        }
    }

    public static void generateTestData() throws IOException {
        Random random = new Random();
        // Размеры графов: от 100 до 10000 вершин
        int[] sizes = {100, 200, 500, 1000, 2000, 5000, 10000};

        // Создаем директорию для тестовых данных, если ее нет
        new File("test_data").mkdir();

        for (int size : sizes) {
            // Генерируем 10 вариантов для каждого размера
            for (int i = 0; i < 10; i++) {
                String filename = "test_data/graph_" + size + "_" + i + ".txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

                // Количество вершин
                writer.write(size + "\n");

                // Количество ребер - для плотного графа до V*(V-1)/2,
                // но мы сделаем примерно 2*V для разреженного графа
                int edges = size * 2;
                writer.write(edges + "\n");

                // Генерация ребер
                for (int j = 0; j < edges; j++) {
                    int src = random.nextInt(size);
                    int dest = random.nextInt(size);
                    // Убедимся, что это не петля
                    while (dest == src) {
                        dest = random.nextInt(size);
                    }
                    int weight = random.nextInt(100) + 1; // Вес от 1 до 100
                    writer.write(src + " " + dest + " " + weight + "\n");
                }

                writer.close();
            }
        }
    }
}
