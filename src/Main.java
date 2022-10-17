import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Graph[] g = new Graph[5];
        for (int i = 0 ; i < 5 ; i++) g[i] = new Graph();

        System.out.println("DFS: ");
        g[0].DFS("Харьков", "Ниж.Новгород");
        g[0].printResult("Ниж.Новгород");

        System.out.println("BFS: ");
        g[1].BFS("Харьков", "Ниж.Новгород");
        g[1].printResult("Ниж.Новгород");

        System.out.println("DLS: ");
        g[2].DLS("Харьков","Ниж.Новгород", 0, 5);
        g[2].printResult("Ниж.Новгород");

        System.out.println("IDDFS: ");
        g[3].IDDFS("Харьков", "Ниж.Новгород", 0, 1);

        System.out.println("BSA: ");
        g[4].BSA("Харьков", "Ниж.Новгород");
        g[4].printResult();

        WeightedGraph weightedGraph1 = new WeightedGraph();
        WeightedGraph weightedGraph2 = new WeightedGraph();
        weightedGraph1.task1();
        weightedGraph2.task2();
    }
}