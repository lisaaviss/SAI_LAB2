import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class WeightedGraph extends Graph {
    private ArrayList<Vertex> vertexes = new ArrayList<>();
    private final int V = 27;
    private boolean[] visited = new boolean[V];

    static class Vertex {
        public String name;
        public int id;
        public LinkedList<Edge> adj = new LinkedList<>(); // distance to other other vertex
        public int h; // heuristic weight
        public int f;
        public int g;
    }

    static class Edge {
        public int id;
        int w;
    }

    private void addVertex(int vid, String vname, int hw) {
        this.vertexes.add(vid, new Vertex() {{
            name = vname;
            id = vid;
            h = hw;
        }});
    }

    private void addEdge(String city1, String city2, int weight) {
        int id1 = stringToInt(city1);
        int id2 = stringToInt(city2);
        this.vertexes.get(id1).adj.add(new Edge() {{
            id = id2;
            w = weight;
        }});
        this.vertexes.get(id2).adj.add(new Edge() {{
            id = id1;
            w = weight;
        }});
    }

    public WeightedGraph() {
        this.addVertex(stringToInt("Вильнюс"), "Вильнюс", 1189);
        this.addVertex(stringToInt("Брест"), "Брест", 1390);
        this.addVertex(stringToInt("Витебск"), "Витебск", 911);
        this.addVertex(stringToInt("Воронеж"), "Воронеж", 606);
        this.addVertex(stringToInt("Волгоград"), "Волгоград", 847);
        this.addVertex(stringToInt("Ниж.Новгород"), "Ниж.Новгород", 0);
        this.addVertex(stringToInt("Даугавпилс"), "Даугавпилс", 1081);
        this.addVertex(stringToInt("Калининград"), "Калининград", 1482);
        this.addVertex(stringToInt("Каунас"), "Каунас", 1267);
        this.addVertex(stringToInt("Киев"), "Киев", 1103);
        this.addVertex(stringToInt("Житомир"), "Житомир", 1218);
        this.addVertex(stringToInt("Донецк"), "Донецк", 1015);
        this.addVertex(stringToInt("Кишинев"), "Кишинев", 1465);
        this.addVertex(stringToInt("С.Петербург"), "С.Петербург", 895);
        this.addVertex(stringToInt("Рига"), "Рига", 1212);
        this.addVertex(stringToInt("Москва"), "Москва", 411);
        this.addVertex(stringToInt("Казань"), "Казань", 328);
        this.addVertex(stringToInt("Минск"), "Минск", 1076);
        this.addVertex(stringToInt("Мурманск"), "Мурманск", 1507);
        this.addVertex(stringToInt("Орел"), "Орел", 631);
        this.addVertex(stringToInt("Одесса"), "Одесса", 1425);
        this.addVertex(stringToInt("Таллинн"), "Таллинн", 1184);
        this.addVertex(stringToInt("Харьков"), "Харьков", 871);
        this.addVertex(stringToInt("Симферополь"), "Симферополь", 1437);
        this.addVertex(stringToInt("Ярославль"), "Ярославль", 287);
        this.addVertex(stringToInt("Уфа"), "Уфа", 771);
        this.addVertex(stringToInt("Самара"), "Самара", 523);

        this.addEdge("Вильнюс","Брест", 531);
        this.addEdge("Витебск","Брест", 638);
        this.addEdge("Витебск","Вильнюс", 360);
        this.addEdge("Воронеж","Витебск", 869);
        this.addEdge("Воронеж","Волгоград", 581);
        this.addEdge("Волгоград","Витебск", 1455);
        this.addEdge("Витебск","Ниж.Новгород", 911);
        this.addEdge("Вильнюс","Даугавпилс", 211);
        this.addEdge("Калининград","Брест", 699);
        this.addEdge("Калининград","Вильнюс", 333);
        this.addEdge("Каунас","Вильнюс", 102);
        this.addEdge("Киев","Вильнюс", 734);
        this.addEdge("Киев","Житомир", 131);
        this.addEdge("Житомир","Донецк", 863);
        this.addEdge("Житомир","Волгоград", 1493);
        this.addEdge("Кишинев","Киев", 467);
        this.addEdge("Кишинев","Донецк", 812);
        this.addEdge("С.Петербург","Витебск", 602);
        this.addEdge("С.Петербург","Калининград", 736);
        this.addEdge("С.Петербург","Рига", 641);
        this.addEdge("Москва","Казань", 815);
        this.addEdge("Москва","Ниж.Новгород", 411);
        this.addEdge("Москва","Минск", 690);
        this.addEdge("Москва","Донецк", 1084);
        this.addEdge("Москва","С.Петербург", 664);
        this.addEdge("Мурманск","С.Петербург", 1412);
        this.addEdge("Мурманск","Минск", 2238);
        this.addEdge("Орел","Витебск", 522);
        this.addEdge("Орел","Донецк", 709);
        this.addEdge("Орел","Москва", 368);
        this.addEdge("Одесса","Киев", 487);
        this.addEdge("Рига","Каунас", 267);
        this.addEdge("Таллинн","Рига", 308);
        this.addEdge("Харьков","Киев", 471);
        this.addEdge("Харьков","Симферополь", 639);
        this.addEdge("Ярославль","Воронеж", 739);
        this.addEdge("Ярославль","Минск", 940);
        this.addEdge("Уфа","Казань", 525);
        this.addEdge("Уфа","Самара", 461);
    }

    public void task1() {
        String start = "Харьков";
        String finish = "Ниж.Новгород";
        int n = stringToInt(start);
        visited[n] = true;
        boolean running = true;
        int sum = 0;
        int step = 0;

        System.out.println("Жадный поиск по первому соответствию:");
        System.out.print("\t");
        while (running) {
            System.out.print(this.vertexes.get(n).name + " -> ");

            int min = 9999;
            for (Edge e : vertexes.get(n).adj) {
                if (vertexes.get(e.id).h < min && !visited[e.id]) {
                    min = vertexes.get(e.id).h;
                    step = e.w;
                    n = e.id;
                }
            }
            visited[n] = true;
            sum += step;
            System.out.print(step + " -> ");
            if (n == stringToInt(finish)) {
                System.out.print(intToString(n));
                running = false;
            }
        }
        System.out.println(", sum: " + sum);
    }

    public void task2() {
        String start = "Харьков";
        String finish = "Ниж.Новгород";
        int n = stringToInt(start);
        visited[n] = true;
        boolean running = true;
        int sum = 0;
        int step = 0;

        System.out.println("A*:");
        System.out.print("\t");
        while (running) {
            System.out.print(this.vertexes.get(n).name + " -> ");

            int min = 9999;
            for (Edge e : vertexes.get(n).adj) {
                if (vertexes.get(e.id).h + e.w < min && !visited[e.id]) {
                    min = vertexes.get(e.id).h + e.w;
                    step = e.w;
                    n = e.id;
                }
            }
            visited[n] = true;
            sum += step;
            System.out.print(step + " -> ");
            if (n == stringToInt(finish)) {
                System.out.print(intToString(n));
                running = false;
            }
        }
        System.out.println(", sum: " + sum);
    }
}