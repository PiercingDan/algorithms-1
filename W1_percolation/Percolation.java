import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation 
{
    private WeightedQuickUnionUF grid;
    private int n;
    private boolean[][] openness;
    private int opencount = 0;

    public Percolation(int N)
    {
        // Created n^2 grid UF Data Structure, + 2 end nodes
        if (N <= 0) throw new java.lang.IllegalArgumentException("Size should be positive");

        n = N;
        int size = n * n + 2;

        // open array
        openness = new boolean[n][n];

        // UF 
        grid = new WeightedQuickUnionUF(size);
        
        // Union Top Layer with second last
        for (int i = 0; i < n; i++)
            grid.union(i, size - 2);

        // Union Bottom Layer with last
        for (int i = n * (n - 1); i < n * n; i++)
            grid.union(i, size - 1);

        // System.out.println(grid.count()); 
    }
    public void open(int row, int col)
    {
        // Catch exception
        if (row < 1 || row > n || col < 1 || col > n) {
           throw new java.lang.IllegalArgumentException("Illegal Entry");
        } 
        
        // what is the index
        int ind = n * (row - 1) + (col - 1); 
        if (openness[row - 1][col - 1] == false);
            openness[row - 1][col - 1] = true;
            opencount++;
        // Up
        if (row > 1 && openness[row - 2][col - 1]) {
            // System.out.println("up");
            grid.union(ind, ind - n);
        }
        // Down
        if (row < n && openness[row][col - 1]) {
            // System.out.println("down");
            grid.union(ind, ind + n);
        }
        //Left
        if (col > 1 && openness[row - 1][col - 2]) {
            // System.out.println("left");
            grid.union(ind, ind - 1);
        }
        // Right
        if (col < n && openness[row - 1][col]) {
            // System.out.println("right");
            grid.union(ind, ind + 1);
        }
    }
    public boolean isOpen(int row, int col)
    {
        // Catch exception
        if (row < 1 || row > n || col < 1 || col > n) {
           throw new java.lang.IllegalArgumentException("Illegal Entry");
        } 
        return openness[row - 1][col - 1];
    }


    public boolean isFull(int row, int col)
    {
        // Catch exception
        if (row < 1 || row > n || col < 1 || col > n) {
           throw new java.lang.IllegalArgumentException("Illegal Entry");
        } 
        return (openness[row - 1][col - 1] && grid.connected(n * (row - 1) + (col - 1), n * n));
    }   
    public int numberOfOpenSites()
    {   
        return opencount;
    }

    public boolean percolates()
    {   
        if (n == 1) {
            return openness[0][0];
        }
        else if (n == 2){
            return grid.connected(n * n, n * n + 1);
        }
        else{    
            return grid.connected(n * n, n * n + 1);
        }
    }

    public static void main(String []args) {
        int n = StdIn.readInt();
        Percolation perc = new Percolation(n);
        System.out.println(perc.isFull(1, 1));
        System.out.println(perc.isOpen(1, 1));

        System.out.println(perc.grid.count());
        System.out.println(perc.numberOfOpenSites());
        System.out.println(perc.percolates());

    }    

}
