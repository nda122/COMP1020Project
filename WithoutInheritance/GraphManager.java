package Termproject2;
import java.util.*;
import java.io.*;

public class GraphManager {
    private Map<Integer, Graph> graphMap;
    private int nextGraphId;

    public GraphManager() {
        graphMap = new HashMap<>();
        nextGraphId = 1;
    }

    public int addGraph(Graph graph) {
        int id = nextGraphId++;
        graphMap.put(id, graph);
        return id;
    }

    public Graph getGraph(int id) {
        return graphMap.get(id);
    }

    public void removeGraph(int id) {
        graphMap.remove(id);
    }

    public void listAllGraphs() {
        for (Map.Entry<Integer, Graph> entry : graphMap.entrySet()) {
            System.out.println("Graph ID: " + entry.getKey());
        }
    }

    public void addEdge(int idGraph, int u, int v, int w) {
        Graph g = getGraph(idGraph);
        if (g != null) {
            g.addEdge(u, v, w);
        } else {
            System.out.println("Cannot find graph ID = " + idGraph);
        }
    }

    public static void main(String[] args) throws IOException {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.txt";

        Scanner in;
        PrintWriter out;
        try {
            in = new Scanner(new File(inputFilePath));
            out = new PrintWriter(new File(outputFilePath));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        }
        GraphManager manager = new GraphManager();

        while (in.hasNextLine()) {
            // out.print("Enter command: ");
            String line = in.nextLine();
            String[] s = line.split(" ");
            String cmd = s[0];

            if (cmd.equals("Exit")) {
                out.println("Bye bye");
                break;
            } else if (cmd.equals("MAKEGRAPH")) {
                int V = Integer.parseInt(s[1]);
                boolean isWeighted = Boolean.parseBoolean(s[2]);
                boolean isDirected = Boolean.parseBoolean(s[3]);
                int E = Integer.parseInt(s[4]);
                Graph g = new Graph(V, isWeighted, isDirected, E);
                int ID = manager.addGraph(g);
                out.println("ID of the new graph is " + ID);
            } else if (cmd.equals("ADDGRAPH")) {
                int idGraph = Integer.parseInt(s[1]);
                // Assuming G is another graph already defined
                Graph G = new Graph(5, true, false); // Example graph
                int newID = manager.addGraph(G);
                out.println("Added graph with new ID = " + newID);
            } else if (cmd.equals("ADDEDGE")) {
                int idGraph = Integer.parseInt(s[1]);
                int u = Integer.parseInt(s[2]);
                int v = Integer.parseInt(s[3]);
                int w = (s.length > 4) ? Integer.parseInt(s[4]) : 1; // Default weight is 1 if not provided
                manager.addEdge(idGraph, u, v, w);
            } else if (cmd.equals("MST")) {
                int idGraph = Integer.parseInt(s[1]);
                Graph g = manager.getGraph(idGraph);
                if (g != null) {
                    try {
                        int mstWeight = Graph.MST(g);
                        out.println("MST weight: " + mstWeight);
                    } catch (UnsupportedOperationException e) {
                        out.println(e.getMessage());
                    }
                } else {
                    out.println("Cannot find graph ID = " + idGraph);
                }
            } else if (cmd.equals("SHORTESTPATH")) {
                int idGraph = Integer.parseInt(s[1]);
                int start = Integer.parseInt(s[2]);
                Graph g = manager.getGraph(idGraph);
                if (g != null) {
                    int[] distances = Graph.ShortestPath(g, start);
                    out.println("Shortest path distances from node " + start + ": " + Arrays.toString(distances));
                } else {
                    out.println("Cannot find graph ID = " + idGraph);
                }
            } else if (cmd.equals("CHECKCYCLE")) {
                int idGraph = Integer.parseInt(s[1]);
                Graph g = manager.getGraph(idGraph);
                if (g != null) {
                    boolean hasCycle = Graph.checkCycle(g);
                    out.println("Graph " + idGraph + " has cycle: " + hasCycle);
                } else {
                    out.println("Cannot find graph ID = " + idGraph);
                }
            } else if (cmd.equals("TOPOLOGICALSORT")) {
                int idGraph = Integer.parseInt(s[1]);
                Graph g = manager.getGraph(idGraph);
                if (g != null) {
                    try {
                        ArrayList<Integer> topoSort = Graph.TopologicalSorting(g);
                        out.println("Topological Sorting: " + topoSort);
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        out.println(e.getMessage());
                    }
                } else {
                    out.println("Cannot find graph ID = " + idGraph);
                }
            } else if (cmd.equals("ISBIPARTITE")) {
                int idGraph = Integer.parseInt(s[1]);
                Graph g = manager.getGraph(idGraph);
                if (g != null) {
                    try {
                        boolean isBipartite = Graph.isBipartite(g);
                        out.println("Graph " + idGraph + " is bipartite: " + isBipartite);
                    } catch (UnsupportedOperationException e) {
                        out.println(e.getMessage());
                    }
                } else {
                    out.println("Cannot find graph ID = " + idGraph);
                }
            } else if (cmd.equals("LISTGRAPHS")) {
                manager.listAllGraphs();
            } else {
                out.println("Unknown command: " + cmd);
            }
        }
        in.close();
        out.close();
    }
}

