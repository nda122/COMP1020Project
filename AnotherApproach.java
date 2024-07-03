package Termproject2;
import java.util.*;
@SuppressWarnings("unchecked")
class Graph{
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
    public 
    public Graph(int V,int E){
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

}

public class tmp {
    
}
