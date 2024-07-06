package Termproject;
import java.util.*;
@SuppressWarnings("unchecked")
class WeightedGraph implements Graph{
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
    protected ArrayList<Pair>[] adjacencyList;
    protected ArrayList<WeightedEdge> Edges;
    public WeightedGraph(int V){
        this.V = V;
        adjacencyList = new ArrayList[V];
    }
    public WeightedGraph(int V,int E){
        Scanner input = new Scanner(System.in);
        this.V = V;
        adjacencyList = new ArrayList[V];
        for(int i=0;i<E;i++){
            int u = input.nextInt();
            int v = input.nextInt();
            int w = input.nextInt();
            addEdge(u, v, w);
        }
        input.close();
        
    }
    public void addEdge(int u, int v, int w) {     
        this.E++;
        adjacencyList[u].add(new Pair(v, w));
        adjacencyList[v].add(new Pair(u, w));
        Edges.add(new WeightedEdge(u, v, w));
    }

    @Override
    public ArrayList<Integer> BFS(int start) {
        ArrayList<Integer> ret = new ArrayList<>();
        boolean[] visisted = new boolean[V];
        Queue<Integer> Q = new LinkedList<>();
        Q.add(start);
        ret.add(start);
        visisted[start] = true;
        while (!Q.isEmpty()) {
            int curr = Q.poll();
            for(Pair x : adjacencyList[curr]){
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
    private ArrayList<Integer> DFS_recur(int curr, boolean[] visited, ArrayList<Integer> ret) {
        visited[curr] = true;
        ret.add(curr);  // Add the current vertex to the result list
        for (Pair x : adjacencyList[curr]) {
            int v = x.v;
            if (!visited[v]) {
                DFS_recur(v, visited, ret);
            }
        }
        return ret;
    }
    
    @Override
    public ArrayList<Integer> DFS(int start) {
        ArrayList<Integer> ret = new ArrayList<>();
        boolean[] visited = new boolean[V];
        DFS_recur(start, visited, ret);
        return ret;
    }
    
    public int[] ShortestPath(int u){ // Dijkstra
        int[] distance = new int[V];
        for(int i=0;i<V;i++) distance[i] = Integer.MAX_VALUE;
        distance[u] = 0;
        PriorityQueue<Pair> PQ = new PriorityQueue<>();
        boolean[] visisted = new boolean[V];
        PQ.add(new Pair(u, 0));
        while (!PQ.isEmpty()) {
            Pair currPair = PQ.poll();
            int curr = currPair.v;
            visisted[curr] = true;
            int dis = currPair.w;
            for(Pair x:adjacencyList[curr]){
                int v = x.v;
                int w = x.w;
                if(!visisted[v]){
                    int newDistance = dis+w;
                    if (newDistance<distance[v]){
                        distance[v] = newDistance;
                        PQ.add(new Pair(v, newDistance));
                    }
                }
            }
        }
        return distance;
    }
    
    
}
@SuppressWarnings("unchecked")
class UnweightedGraph implements Graph{
    protected class UnweightedEdge{
        int u,v;
        UnweightedEdge(int u,int v){
            this.u = u;
            this.v = v;
        }
    }
    protected int V,E;
    protected ArrayList<Integer>[] adjacencyList;
    protected ArrayList<UnweightedEdge> Edges;
    public UnweightedGraph(int V){
        this.V = V;
        adjacencyList = new ArrayList[V];
    }
    public UnweightedGraph(int V, int E){
        Scanner input = new Scanner(System.in);
        this.V = V;
        this.E = E;
        adjacencyList = new ArrayList[V];
        for(int i=0;i<E;i++){
            int u = input.nextInt();
            int v = input.nextInt();

            addEdge(u, v);
        }
        input.close();
        
    }
    public void addEdge(int u, int v) {   
        this.E++;  
        adjacencyList[u].add(v);
        adjacencyList[v].add(u);
        Edges.add(new UnweightedEdge(u, v));
        
    }
    @Override
    public ArrayList<Integer> BFS(int start) {
        ArrayList<Integer> ret = new ArrayList<>();
        boolean[] visisted = new boolean[V];
        Queue<Integer> Q = new LinkedList<>();
        Q.add(start);
        ret.add(start);
        visisted[start] = true;
        while (!Q.isEmpty()) {
            int curr = Q.poll();
            for(int v : adjacencyList[curr]){
                if(!visisted[v]){
                    visisted[v] = true;
                    Q.add(v);
                    ret.add(v);
                }
            }
        }
        return ret;
        
    }
    private ArrayList<Integer> DFS_recur(int curr, boolean[] visited, ArrayList<Integer> ret) {
        visited[curr] = true;
        ret.add(curr);  // Add the current vertex to the result list
        for (int v : adjacencyList[curr]) {
            if (!visited[v]) {
                DFS_recur(v, visited, ret);
            }
        }
        return ret;
    }
    
    @Override
    public ArrayList<Integer> DFS(int start) {
        ArrayList<Integer> ret = new ArrayList<>();
        boolean[] visited = new boolean[V];
        DFS_recur(start, visited, ret);
        return ret;
    }
}
