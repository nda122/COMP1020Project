package Termproject2;
import Termproject.DisjointSet;
import java.util.*;
@SuppressWarnings("unchecked")
public class Graph{
    protected class Pair implements Comparable<Pair>{
        int v,w;
        Pair(int v,int w){
            this.v = v;
            this.w = w;
        }
        @Override
        public int compareTo(Pair arg0) {
            return Integer.compare(w, arg0.w);
        }
        
    }
    protected class WeightedEdge implements Comparable<WeightedEdge>{
        int u,v,w;
        WeightedEdge(int u,int v,int w){
            this.u = u;
            this.v = v;
            this.w = w;
        }
        @Override
        public int compareTo(WeightedEdge arg0) {
            return Integer.compare(w, arg0.w);
        }
    }
    protected int V,E;
    protected boolean isDirected, isWeighted;
    protected ArrayList<Pair>[] adjacencyList;
    protected ArrayList<WeightedEdge> Edges;

    public Graph(int V){
        this.V = V;
        this.isWeighted = false;
        this.isDirected = true;
        adjacencyList = new ArrayList[V];
    }
    public Graph(int V, boolean isWeighted, boolean isDirected){
        this(V);
        
        this.isWeighted = isWeighted;
        this.isDirected = isDirected;
        
    }
    public Graph(int V,boolean isWeighted, boolean isDirected, int E){
        this(V, isWeighted, isDirected);
        Scanner input = new Scanner(System.in);
        for(int i=0;i<E;i++){
            int u = input.nextInt();
            int v = input.nextInt();
            int w = 1;
            if(isWeighted) w = input.nextInt();
            addEdge(u, v, w);
        }
        input.close();
        
    }
    
    public void addEdge(int u, int v, int w) {     
        this.E++;
        adjacencyList[u].add(new Pair(v, w));
        if(isDirected) adjacencyList[v].add(new Pair(u, w));
        Edges.add(new WeightedEdge(u, v, w));
    }
    public void addEdge(int u, int v) {     
        int w = 1;
        this.addEdge(u, v, w);
    }


    static public ArrayList<Integer> BFS(Graph G, int start){
        ArrayList<Integer> ret = new ArrayList<>();
        boolean[] visisted = new boolean[G.V];
        Queue<Integer> Q = new LinkedList<>();
        Q.add(start);
        ret.add(start);
        visisted[start] = true;
        while (!Q.isEmpty()) {
            int curr = Q.poll();
            for(Pair x : G.adjacencyList[curr]){
                int v = x.v;
                if(!visisted[v]){
                    visisted[v] = true;
                    Q.add(v);
                    ret.add(v);
                }
            }
        }
        return ret;
    }
    static private ArrayList<Integer> DFS_recur(Graph G, int curr, boolean[] visited, ArrayList<Integer> ret) {
        visited[curr] = true;
        ret.add(curr);  // Add the current vertex to the result list
        for (Pair x : G.adjacencyList[curr]) {
            int v = x.v;
            if (!visited[v]) {
                Graph.DFS_recur(G, v, visited, ret);
            }
        }
        return ret;
    }
    static public ArrayList<Integer> DFS(Graph G, int start){
        ArrayList<Integer> ret = new ArrayList<>();
        boolean[] visited = new boolean[G.V];
        DFS_recur(G,start, visited, ret);
        return ret;
    }
    static private boolean checkCycleRecur(Graph G, int u, boolean[] visited, boolean[] recur){// for directed graph
        if (recur[u])
        return true;
        if (visited[u])
        return false;

        visited[u] = true;
        recur[u] = true;
        for (Pair x : G.adjacencyList[u]){
            int v = x.v;
            if (Graph.checkCycleRecur(G, v, visited, recur))
                return true;    
        }
        recur[u] = false;

        return false;
    }

    static public boolean checkCycle(Graph G){
        if(G.isDirected){
            boolean[] visisted = new boolean[G.V+5];
            boolean[] recur = new boolean[G.V+5];
            for(int i=0;i<G.V;i++){
                if(Graph.checkCycleRecur(G, i, visisted, recur)) return true;
            }
            return false;
        }
        DisjointSet DS = new DisjointSet(G.V);
        for(WeightedEdge x:G.Edges){
            if(!DS.join(x.u, x.v)){
                return false;
            }
        }
        return true;
    }
    static private void topoSortRecur(Graph G, int u,boolean[] visisted, Stack<Integer> ST){//dfs
        for(Pair x:G.adjacencyList[u]){
            int v = x.v;
            if(!visisted[v]){
                Graph.topoSortRecur(G, v, visisted, ST);
            }
            visisted[u] = true;
            ST.push(u);
        }
    }
    static public ArrayList<Integer> TopologicalSorting(Graph G){
        if(!G.isDirected){
            throw new IllegalArgumentException("Topological sorting is not applicable for undirected graphs.");
        }
        if(checkCycle(G)){
            throw new IllegalStateException("Graph contains a cycle, topological sorting is not possible.");
        }
        Stack<Integer> ST = new Stack<>();
        boolean[] visisted = new boolean[G.V+5];
        for(int i=0;i<G.V;i++){
            if (!visisted[i]) {
                Graph.topoSortRecur(G,i, visisted, ST);
            }
        }
        ArrayList<Integer> ret = new ArrayList<>();
        while (!ST.isEmpty()) {
            ret.add(ST.pop());
        }
        return ret;
    }
    static public int[] ShortestPath(Graph G, int start){
        if(!G.isWeighted){
            int[] visisted = new int[G.V];
            for(int i=0;i<G.V;i++) visisted[i] = -1;
            Queue<Integer> Q = new LinkedList<>();
            Q.add(start);
            visisted[start] = 0;
            while (!Q.isEmpty()) {
                int curr = Q.poll();
                for(Pair x : G.adjacencyList[curr]){
                    int v = x.v;
                    if(visisted[v]==-1){
                        visisted[v] = visisted[curr]+1;
                        Q.add(v);
                    }
                }
            }
            return visisted;
        }
        // Using Dijkstra algo
        int[] distance = new int[G.V];
        for(int i=0;i<G.V;i++) distance[i] = Integer.MAX_VALUE;
        distance[start] = 0;
        PriorityQueue<Pair> PQ = new PriorityQueue<>();
        boolean[] visisted = new boolean[G.V];
        PQ.add(G.new Pair(start, 0));
        while (!PQ.isEmpty()) {
            Pair currPair = PQ.poll();
            int curr = currPair.v;
            visisted[curr] = true;
            int dis = currPair.w;
            for(Pair x:G.adjacencyList[curr]){
                int v = x.v;
                int w = x.w;
                if(!visisted[v]){
                    int newDistance = dis+w;
                    if (newDistance<distance[v]){
                        distance[v] = newDistance;
                        PQ.add(G.new Pair(v, newDistance));
                    }
                }
            }
        }
        return distance;
    }
    static public int MST(Graph G){
        if (!G.isWeighted) {
            throw new UnsupportedOperationException("Minimum Spanning Tree requires the graph to be weighted.");
        }
        if (G.isDirected) {
            throw new UnsupportedOperationException("Minimum Spanning Tree is only applicable to undirected graphs.");
        }
        int ret = 0;
        int cnt = 0;
        DisjointSet DS = new DisjointSet(G.V+5);
        for(WeightedEdge x:G.Edges){
            if(DS.join(x.u, x.v)){
                cnt++;
                ret+=x.w;
            }
            if(cnt==G.V-1){
                break;
            }
        }
        return ret;
        
    }
    static private boolean isBipartiteUtil(Graph G, int start, int[] colors) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        colors[start] = 0; 

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (Pair x : G.adjacencyList[u]) {
                int v = x.v;
                if (colors[v] == -1) {
                    colors[v] = 1 - colors[u];
                    queue.add(v);
                } else if (colors[v] == colors[u]) {
                    return false;
                }
            }
        }
        return true;
    }
    static public boolean isBipartite(Graph G){
        if (G.isDirected) {
            throw new UnsupportedOperationException("Bipartiteness check is only applicable to undirected graphs.");
        }

        int[] colors = new int[G.V];
        for (int i = 0; i < G.V; i++) {
            colors[i] = -1; // -1 indicates that the vertex has not been colored yet
        }

        // Check each component of the graph
        for (int i = 0; i < G.V; i++) {
            if (colors[i] == -1) {
                if (!Graph.isBipartiteUtil(G, i, colors)) {
                    return false;
                }
            }
        }
        return true;
    }
    
}


