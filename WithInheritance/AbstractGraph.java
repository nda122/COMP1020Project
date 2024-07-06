package Termproject;
import java.util.*;
interface Graph{
    public ArrayList<Integer> BFS(int start);
    public ArrayList<Integer> DFS(int start);
    
}

interface DirectedGraph extends Graph{
    public boolean checkCycle();
    public ArrayList<Integer> TopologicalSorting();
    
}
interface UndirectedGraph extends Graph{
    public boolean checkCycle();
    
}
