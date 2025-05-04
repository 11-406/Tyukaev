import java.io.*;
import java.util.*;

public class PrimAlgorithm {
    private static int iterations = 0;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java PrimAlgorithm <input_file>");
            return;
        }

        String inputFile = args[0];
        try {
            // Чтение графа из файла
            Graph graph = readGraphFromFile(inputFile);

            // Запуск алгоритма Прима
            long startTime = System.nanoTime();
            List<Edge> mst = primMST(graph);
            long endTime = System.nanoTime();

            // Вывод результатов
            System.out.println("Minimum Spanning Tree edges (" + mst.size() + "):");
            int totalWeight = 0;
            for (Edge edge : mst) {
                totalWeight += edge.weight;
                System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight);
            }
            System.out.println("Total weight: " + totalWeight);

            // Вывод метрик
            System.out.println("\nPerformance metrics:");
            System.out.println("Time taken: " + (endTime - startTime) + " ns");
            System.out.println("Time taken: " + (endTime - startTime)/1_000_000 + " ms");
            System.out.println("Iterations: " + iterations);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static List<Edge> primMST(Graph graph) {
        iterations = 0; // Сброс счетчика итераций
        int V = graph.V;
        List<Edge> mst = new ArrayList<>();
        boolean[] inMST = new boolean[V];
        inMST[0] = true; // Начинаем с вершины 0

        // Приоритетная очередь для хранения ребер
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.addAll(graph.adj[0]);

        while (!pq.isEmpty() && mst.size() < V - 1) {
            iterations++; // Увеличение счетчика итераций

            Edge edge = pq.poll();
            int nextVertex = inMST[edge.src] ? edge.dest : edge.src;

            if (!inMST[nextVertex]) {
                mst.add(edge);
                inMST[nextVertex] = true;
                pq.addAll(graph.adj[nextVertex]);
            }
        }

        return mst;
    }

    private static Graph readGraphFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int V = Integer.parseInt(reader.readLine());
        int E = Integer.parseInt(reader.readLine());

        Graph graph = new Graph(V);
        for (int i = 0; i < E; i++) {
            String[] parts = reader.readLine().split(" ");
            int src = Integer.parseInt(parts[0]);
            int dest = Integer.parseInt(parts[1]);
            int weight = Integer.parseInt(parts[2]);
            graph.addEdge(src, dest, weight);
        }

        reader.close();
        return graph;
    }

    static class Graph {
        int V;
        List<Edge>[] adj;

        Graph(int V) {
            this.V = V;
            adj = new ArrayList[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayList<>();
            }
        }

        void addEdge(int src, int dest, int weight) {
            Edge edge = new Edge(src, dest, weight);
            adj[src].add(edge);
            adj[dest].add(edge); // Для неориентированного графа
        }
    }

    static class Edge {
        int src, dest, weight;

        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }
}