import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats
{
    private int n;
    private int T; // number of independent computational experiments
    private double mean;
    private double std;
    private double[] fraction;

    public PercolationStats(int N, int trials) 
    {
       if (N <= 0 || trials <= 0) {
           throw new java.lang.IllegalArgumentException("Illegal Entry");
       }
       n = N;
       T = trials; 
       fraction = new double[T];
       
       Percolation perc = null;

       // Trials
       for (int i = 0; i < T; i++) {
            perc = new Percolation(n); 
            int count = 0; 
            while (perc.percolates() == false) {
                // Don't pick already open

                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                while (perc.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                }
                count++; 
                perc.open(row, col);
            }    

            fraction[i] = ((double) count) / (n * n);
        }
        mean = StdStats.mean(fraction);
        std = StdStats.stddev(fraction);
    }

    public double mean()
    {   return mean; }

    public double stddev()
    {   return std; }

    public double confidenceLo()
    {
        return mean - 1.96 * std / Math.sqrt(T);
    }

    public double confidenceHi()
    {
        return mean + 1.96 * std / Math.sqrt(T);
    }
    
    public static void main(String []args)
    {
        int n = StdIn.readInt();
        int T = StdIn.readInt();

        PercolationStats ps = new PercolationStats(n, T);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLo());
        System.out.println(ps.confidenceHi());
    }

}   
