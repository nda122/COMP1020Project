package Termproject;

import java.util.*;

class UnweightedUndirectedGraph extends UnweightedGraph implements UndirectedGraph{
    public UnweightedUndirectedGraph(int V){
        super(V);
    }
    public UnweightedUndirectedGraph(int V,int E){
        super(V,E);
    }
    @Override
    public boolean checkCycle() {
        DisjointSet DS = new DisjointSet(V);
        for(UnweightedEdge x:Edges){
            if(!DS.join(x.u, x.v)){
                return false;
            }
        }
        return true;
    }
    private void checkBipartiteRecur(int i,int c, int[] color){
        
        color[i] = c;
        for(int v:adjacencyList[i]){
            if(color[v]==-1){
                checkBipartiteRecur(i, c, color);
            }
        }
    }
    public boolean checkBipartite(){
        int[] color = new int[V+5];
        Arrays.fill(color, -1);
        for(int i=0;i<V;i++){
            if(color[i] == -1){
                checkBipartiteRecur(i,0,color);
            }
        }
        
        for(int i=0;i<E;i++){
            if(color[Edges.get(i).u]==color[Edges.get(i).v]){
                return false;
            }
        }
        return true;
    }
    public boolean isBipartite(){
        
        return false;
    }
}
class UnweightedDirectedGraph extends UnweightedGraph implements DirectedGraph{
    public UnweightedDirectedGraph(int V){
        super(V);
    }
    public UnweightedDirectedGraph(int V,int E){
        super(V,E);
    }
    private boolean checkCycleRecur(int u, boolean[] visited, boolean[] recur){
        if (recur[u])
        return true;
        if (visited[u])
        return false;

        visited[u] = true;
        recur[u] = true;
        for (int v : adjacencyList[u]){
            if (checkCycleRecur(v, visited, recur))
                return true;    
        }
        recur[u] = false;

        return false;
    }

    @Override
    public boolean checkCycle() {
        boolean[] visisted = new boolean[V+5];
        boolean[] recur = new boolean[V+5];
        for(int i=0;i<V;i++){
            if(checkCycleRecur(i, visisted, recur)) return true;
        }
        return false;
    }
    private void topoSortRecur(int u,boolean[] visisted, Stack<Integer> ST){//dfs
        for(int v:adjacencyList[u]){
            if(!visisted[v]){
                topoSortRecur(v, visisted, ST);
            }
            visisted[u] = true;
            ST.push(u);
        }
    }
    @Override
    public ArrayList<Integer> TopologicalSorting() {
        Stack<Integer> ST = new Stack<>();
        boolean[] visisted = new boolean[V+5];
        for(int i=0;i<V;i++){
            if (!visisted[i]) {
                topoSortRecur(i, visisted, ST);
            }
        }
        ArrayList<Integer> ret = new ArrayList<>();
        while (!ST.isEmpty()) {
            ret.add(ST.pop());
        }
        return ret;
    }

}
class WeightedUndirectedGraph extends WeightedGraph implements UndirectedGraph{
    public WeightedUndirectedGraph(int V){
        super(V);
    }
    public WeightedUndirectedGraph(int V,int E){
        super(V,E);
    }
    @Override
    public boolean checkCycle() {
        DisjointSet DS = new DisjointSet(V);
        for(WeightedEdge x:Edges){
            if(!DS.join(x.u, x.v)){
                return false;
            }
        }
        return true;
    }
    
    public int MST() {
        int ret = 0;
        int cnt = 0;
        DisjointSet DS = new DisjointSet(V);
        for(WeightedEdge x:Edges){
            if(DS.join(x.u, x.v)){
                cnt++;
                ret+=x.w;
            }
            if(cnt==V-1){
                break;
            }
        }
        return ret;
    }

}
class WeightedDirectedGraph extends WeightedGraph implements DirectedGraph{
    public WeightedDirectedGraph(int V){
        super(V);
    }
    public WeightedDirectedGraph(int V,int E){
        super(V,E);
    }
    private boolean checkCycleRecur(int u, boolean[] visited, boolean[] recur){
        if (recur[u])
        return true;
        if (visited[u])
        return false;

        visited[u] = true;
        recur[u] = true;
        for (Pair x : adjacencyList[u]){
            int v = x.v;
            if (checkCycleRecur(v, visited, recur))
                return true;    
        }
        recur[u] = false;

        return false;
    }

    @Override
    public boolean checkCycle() {
        boolean[] visisted = new boolean[V+5];
        boolean[] recur = new boolean[V+5];
        for(int i=0;i<V;i++){
            if(checkCycleRecur(i, visisted, recur)) return true;
        }
        return false;
    }
    
    private void topoSortRecur(int u,boolean[] visisted, Stack<Integer> ST){//dfs
        for(Pair x:adjacencyList[u]){
            int v = x.v;
            if(!visisted[v]){
                topoSortRecur(v, visisted, ST);
            }
            visisted[u] = true;
            ST.push(u);
        }
    }
    @Override
    public ArrayList<Integer> TopologicalSorting() {
        Stack<Integer> ST = new Stack<>();
        boolean[] visisted = new boolean[V+5];
        for(int i=0;i<V;i++){
            if (!visisted[i]) {
                topoSortRecur(i, visisted, ST);
            }
        }
        ArrayList<Integer> ret = new ArrayList<>();
        while (!ST.isEmpty()) {
            ret.add(ST.pop());
        }
        return ret;
    }
}
