import java.lang.*;
import java.io.*;
import java.util.ArrayList;

public class Marks {
    private static String FILENAME = "./marks.txt";
    public static ArrayList<ArrayList<Integer>> marksList = new ArrayList<>();
    public static int studentSize;

    static class Graph {
        int V;
        ArrayList<ArrayList<Boolean>> adjListArray;

        Graph(int V) {
            this.V = V;

            adjListArray = new ArrayList<>();

            for (int i = 0; i < V; i++) {
                adjListArray.add(new ArrayList<>());
            }

            for(int i=0;i<V;i++) {
                for(int j=0;j<V;j++) {
                    adjListArray.get(i).add(false);
                }
            }
        }
    }

    static void addEdge(Graph graph, int src, int dest) {
        graph.adjListArray.get(src).set(dest, true);
    }


    public static void readFromFile(String pathname) {
        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(pathname));

            String sCurrentLine;
            int studentNum = 0;

            while ((sCurrentLine = br.readLine()) != null) {
                String[] split = sCurrentLine.split("\\s+");
                marksList.add(new ArrayList<Integer>());
                for (int i = 1; i < split.length; i++) {
                    marksList.get(studentNum).add(Integer.parseInt(split[i]));
                }
                studentNum++;
            }
            studentSize = studentNum;

            br.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static boolean compareMarks(ArrayList<Integer> stu1, ArrayList<Integer> stu2) {
        for(int i=0;i<stu1.size();i++) {
            if(stu1.get(i) <= stu2.get(i))
                return false;
        }
        return true;
    }

    public static ArrayList<ArrayList<Boolean>> formGraph() {
        Graph graph = new Graph(studentSize);

        for(int i=0;i<studentSize;i++) {
            for(int j=0;j<studentSize;j++) {
                if(compareMarks(marksList.get(i), marksList.get(j))) {
                    addEdge(graph, i, j);
                }
            }
        }
        return graph.adjListArray;
    }


    public static ArrayList<ArrayList<Boolean>> transitiveClosure(ArrayList<ArrayList<Boolean>> graph) {
        int noOfVertices = graph.size();
        // transitive reduction
        for (int j = 0; j < noOfVertices; ++j)
            for (int i = 0; i < noOfVertices; ++i)
                if (graph.get(i).get(j))
                    for (int k = 0; k < noOfVertices; ++k)
                        if (graph.get(j).get(k))
                            graph.get(i).set(k, false);
        return graph;

    }



    public static String getLetter(int n) {
        return Character.toString((char) (65 + n));
    }

    public static void printGraph(ArrayList<ArrayList<Boolean>> graph) {
        int noOfVertices = graph.size();
        for(int i = 0; i < noOfVertices; i++)
            for(int j = 0; j < noOfVertices; j++)
                if(graph.get(i).get(j))
                    System.out.println( getLetter(i)+ " > " + getLetter(j));

    }

    public static void main(String a[]) {
        readFromFile("./test1.txt");
        ArrayList<ArrayList<Boolean>> g = formGraph();
        g = transitiveClosure(g);
        printGraph(g);



    }
}