import java.util.*;

public class Graph {
    private LinkedList<Integer>[] adjLists;
    private boolean[] visited;
    private final int V = 27;
    private LinkedList<String> result = new LinkedList<>();
    private LinkedList<String> revResult = new LinkedList<>();

    Graph() {
        adjLists = new LinkedList[V];
        visited = new boolean[V];

        for (int i = 0; i < V; i++)
            adjLists[i] = new LinkedList<Integer>();

        this.addEdge("Вильнюс","Брест");
        this.addEdge("Витебск","Брест");
        this.addEdge("Витебск","Вильнюс");
        this.addEdge("Воронеж","Витебск");
        this.addEdge("Воронеж","Волгоград");
        this.addEdge("Волгоград","Витебск");
        this.addEdge("Витебск","Ниж.Новгород");
        this.addEdge("Вильнюс","Даугавпилс");
        this.addEdge("Калининград","Брест");
        this.addEdge("Калининград","Вильнюс");
        this.addEdge("Каунас","Вильнюс");
        this.addEdge("Киев","Вильнюс");
        this.addEdge("Киев","Житомир");
        this.addEdge("Житомир","Донецк");
        this.addEdge("Житомир","Волгоград");
        this.addEdge("Кишинев","Киев");
        this.addEdge("Кишинев","Донецк");
        this.addEdge("С.Петербург","Витебск");
        this.addEdge("С.Петербург","Калининград");
        this.addEdge("С.Петербург","Рига");
        this.addEdge("Москва","Казань");
        this.addEdge("Москва","Ниж.Новгород");
        this.addEdge("Москва","Минск");
        this.addEdge("Москва","Донецк");
        this.addEdge("Москва","С.Петербург");
        this.addEdge("Мурманск","С.Петербург");
        this.addEdge("Мурманск","Минск");
        this.addEdge("Орел","Витебск");
        this.addEdge("Орел","Донецк");
        this.addEdge("Орел","Москва");
        this.addEdge("Одесса","Киев");
        this.addEdge("Рига","Каунас");
        this.addEdge("Таллинн","Рига");
        this.addEdge("Харьков","Киев");
        this.addEdge("Харьков","Симферополь");
        this.addEdge("Ярославль","Воронеж");
        this.addEdge("Ярославль","Минск");
        this.addEdge("Уфа","Казань");
        this.addEdge("Уфа","Самара");
    }

    private void addEdge(String src, String dest) {
        adjLists[stringToInt(src)].add(stringToInt(dest));
        adjLists[stringToInt(dest)].add(stringToInt(src));
    }

    public void DFS(String start, String finish) {
        int vertex = stringToInt(start);
        int dest = stringToInt(finish);
        visited[vertex] = true;

        this.result.add(intToString(vertex));

        Iterator<Integer> ite = adjLists[vertex].listIterator();
        while (ite.hasNext()) {
            int adj = ite.next();
            if (!visited[adj] && !visited[dest]) {
                DFS(intToString(adj), intToString(dest));
            }
        }
    }

    public void BFS(String start, String finish) {
        int s = stringToInt(start);
        LinkedList<Integer> queue = new LinkedList<>();

        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            s = queue.poll();
            this.result.add(intToString(s));
            if (intToString(s).equals(finish)) return; //STOP SEARCHING WHEN WE REACH DEST

            Iterator<Integer> i = adjLists[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }

    /**
     * Using BFS from both sides
     */
    public void BSA(String start, String finish) {
        boolean[] visitedF = new boolean[V];
        int vStart = stringToInt(start);
        int vFinish = stringToInt(finish);
        LinkedList<Integer> queueStart = new LinkedList<>();
        LinkedList<Integer> queueFinish = new LinkedList<>();
        visited[vStart] = visitedF[vFinish] = true;
        queueStart.add(vStart);
        queueFinish.add(vFinish);

        while (queueStart.size() != 0 && queueFinish.size() != 0) {
            vStart = queueStart.poll();
            vFinish = queueFinish.poll();
            result.add(intToString(vStart));
            revResult.add(intToString(vFinish));
            //if (vStart == vFinish) return;

            Iterator<Integer> iStart = adjLists[vStart].listIterator();
            Iterator<Integer> iFinish = adjLists[vFinish].listIterator();

            while (iStart.hasNext() && iFinish.hasNext()) {
                int nS = iStart.next();
                int nF = iFinish.next();
                if (!visited[nS]) {
                    visited[nS] = true;
                    if (visitedF[nS]) {
                        result.add(intToString(nS));
                        return;
                    }
                    queueStart.add(nS);
                }
                if (!visitedF[nF]) {
                    visitedF[nF] = true;
                    if (visited[nF]) {
                        revResult.add(intToString(nF));
                        return;
                    }
                    queueFinish.add(nF);
                }
            }
        }

    }

    public void DLS(String start, String finish, int depth, int limit) {
        int vertex = stringToInt(start);
        int dest = stringToInt(finish);
        visited[vertex] = true;

        this.result.add(intToString(vertex));

        Iterator<Integer> ite = adjLists[vertex].listIterator();

        if (depth < limit) {
            while (ite.hasNext()) {
                int adj = ite.next();
                if (!visited[adj]) {
                    DLS(intToString(adj), intToString(dest), ++depth, limit);
                }
            }
        }
    }

    public void IDDFS(String start, String finish, int depth, int limit) {
        while (!result.contains(finish)) {
            this.DLS(start, finish, depth, limit++);

            System.out.print("\tLimit " + (limit-1) + ": ");
            printResult(finish);

            clearVisited();
            if (!result.contains(finish)) result.clear();
        }
    }

    protected int stringToInt(String s) {
        return switch (s) {
            case "Вильнюс" -> 0;
            case "Брест" -> 1;
            case "Витебск" -> 2;
            case "Воронеж" -> 3;
            case "Волгоград" -> 4;
            case "Ниж.Новгород" -> 5;
            case "Даугавпилс" -> 6;
            case "Калининград" -> 7;
            case "Каунас" -> 8;
            case "Киев" -> 9;
            case "Житомир" -> 10;
            case "Донецк" -> 11;
            case "Кишинев" -> 12;
            case "С.Петербург" -> 13;
            case "Рига" -> 14;
            case "Москва" -> 15;
            case "Казань" -> 16;
            case "Минск" -> 17;
            case "Мурманск" -> 18;
            case "Орел" -> 19;
            case "Одесса" -> 20;
            case "Таллинн" -> 21;
            case "Харьков" -> 22;
            case "Симферополь" -> 23;
            case "Ярославль" -> 24;
            case "Уфа" -> 25;
            case "Самара" -> 26;

            default -> -1;
        };
    }

    protected String intToString(int index) {
        return switch (index) {
            case 0 -> "Вильнюс"; // 1189
            case 1 -> "Брест"; // 1390
            case 2 -> "Витебск"; // 911
            case 3 -> "Воронеж"; // 606
            case 4 -> "Волгоград"; // 847
            case 5 -> "Ниж.Новгород";
            case 6 -> "Даугавпилс"; // 1081
            case 7 -> "Калининград"; // 1482
            case 8 -> "Каунас"; // 1267
            case 9 -> "Киев"; // 1103
            case 10 -> "Житомир"; // 1218
            case 11 -> "Донецк"; // 1015
            case 12 -> "Кишинев"; // 1465
            case 13 -> "С.Петербург"; // 895
            case 14 -> "Рига"; //1212
            case 15 -> "Москва"; //411
            case 16 -> "Казань"; //328
            case 17 -> "Минск"; //1076
            case 18 -> "Мурманск"; //1507
            case 19 -> "Орел"; //631
            case 20 -> "Одесса"; //1425
            case 21 -> "Таллинн"; //1184
            case 22 -> "Харьков"; //871
            case 23 -> "Симферополь"; //1437
            case 24 -> "Ярославль"; //287
            case 25 -> "Уфа"; // 771
            case 26 -> "Самара"; //523

            default -> null;
        };
    }

    public void printResult(String dest) {
        System.out.print("\t");
        for (String s : this.result) {
            if (!s.equals(dest)) {
                System.out.print(s + " -> ");
            } else {
                System.out.println(s);
                return;
            }

            if (result.indexOf(s) == result.size() - 1) {
                System.out.println("X");
                return;
            }
        }
    }

    public void printResult() {
        System.out.print("\t");
        for (String s : result) {
            System.out.print(s + " -> ");
        }
        for (int i = revResult.size() - 1 ; i > 0 ; i--) {
            System.out.print(i == revResult.size() - 1 ? revResult.get(i) : " <- " + revResult.get(i));
        }
        System.out.println();
    }

    private void clearVisited() {
        for (int i = 0 ; i < this.V; i++) this.visited[i] = false;
    }
}