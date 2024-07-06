package Termproject;

public class DisjointSet {
    int capacity;
    int[] parent, rank, setSize;
    int numSet;

    DisjointSet(int capacity) {
        this.capacity = capacity;
        numSet = capacity;
        parent = new int[capacity + 5];
        rank = new int[capacity + 5];
        setSize = new int[capacity + 5];
        for (int i = 0; i < capacity; i++) {
            parent[i] = i;
            rank[i] = 0;
            setSize[i] = 1;
        }
    }

    public int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);  // Path compression
    }

    public boolean isSameSet(int i, int j) {
        return find(i) == find(j);
    }

    boolean join(int i, int j) {
        int root1 = find(i);
        int root2 = find(j);
        if (root1 != root2) {
            // Union by rank
            if (rank[root1] > rank[root2]) {
                parent[root2] = root1;
                setSize[root1] += setSize[root2];
            } else if (rank[root1] < rank[root2]) {
                parent[root1] = root2;
                setSize[root2] += setSize[root1];
            } else {
                parent[root2] = root1;
                setSize[root1] += setSize[root2];
                rank[root1]++;
            }
            numSet--;
            return true;
        }
        return false;
    }
}
