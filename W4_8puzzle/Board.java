import edu.princeton.cs.algs4.Stack;
import java.lang.Math;
import java.lang.StringBuilder;
import java.util.Arrays;


public class Board {

    private final int[][] blocks; 
    private int n;
    private int man = -1;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        if (blocks == null) throw new java.lang.IllegalArgumentException();

        this.blocks = blocks;
        this.n = this.blocks.length;

        this.man = manhattan(); 
    }



    public int dimension()  {   return n;  }                // board dimension n



    public int hamming()                   // number of blocks out of place
    {
    // Not checking if block is 0, also the last check (n^2) is always wrong unless its the 0 block, in which case it does not add anything

        int ham = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int expected = i * n + (j + 1); 
                // System.out.println(expected + " " + blocks[i][j]);
                if ((expected != blocks[i][j]) && (blocks[i][j] != 0))  {   ham++;  }
            }
        }

        return ham;
    }



    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        if (this.man != -1) return this.man; // Already been calculated

        int man = 0;        

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int block = blocks[i][j];

                int expi = (blocks[i][j] - 1) / n; //expected i
                int expj = (blocks[i][j] - 1) % n; //expected j
                // System.out.println(block + " " + expi + " " + expj);
               
                int dist = Math.abs(expi - i) + Math.abs(expj - j); 
                // System.out.println("Dist: " + dist);

                if (block != 0) {   man += dist;   }

            }
        }

        return man;
    }


    public boolean isGoal()                // is this board the goal board?
    {   return this.hamming() == 0; }


    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        // I need to make copies of each individual arrays, otherwise, i'm just making a new array of the same array pointers

        int[][] twinblocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            twinblocks[i] = Arrays.copyOf(this.blocks[i], n); // 
        }

        // Ugly solution for small problem: don't want to swap zero. This loop goes on for at most 3 iterations
        outerloop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((twinblocks[i][j] != 0) && (twinblocks[i][(j + 1) % n] != 0))    {
                    int swap = twinblocks[i][j];
                    twinblocks[i][j] = twinblocks[i][(j + 1) % n];
                    twinblocks[i][(j + 1) % n] = swap; 

                    break outerloop;
                }
            }
        }
        
        // System.out.println(Arrays.deepToString(twinblocks));
        // System.out.println(Arrays.deepToString(this.blocks));

        return new Board(twinblocks);

    }



    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        // I can access private Board.blocks since it belongs to this class
        if (that.n != this.n) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.blocks[i][j] != this.blocks[i][j]) return false;
            }
        }

        return true;

    }



    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack stack = new Stack<Board>();

        //locate 0 - empty space

        int i0 = 0;
        int j0 = 0;

        outerloop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {i0 = i; j0 = j; break outerloop;} 
            }
        }

        // Up
        if (i0 != 0) {

            int[][] blocks = new int[n][n];
            for (int i = 0; i < n; i++) {   blocks[i] = Arrays.copyOf(this.blocks[i], n);   }

            // Exchange
            blocks[i0][j0] = blocks[i0 - 1][j0];
            blocks[i0 - 1][j0] = 0;
            stack.push(new Board(blocks));
        }


        // Down
        if (i0 != n - 1) {

            int[][] blocks = new int[n][n];
            for (int i = 0; i < n; i++) {   blocks[i] = Arrays.copyOf(this.blocks[i], n);   }

            // Exchange
            blocks[i0][j0] = blocks[i0 + 1][j0];
            blocks[i0 + 1][j0] = 0;
            stack.push(new Board(blocks));
        }
         
        // Left
        if (j0 != 0) {

            int[][] blocks = new int[n][n];
            for (int i = 0; i < n; i++) {   blocks[i] = Arrays.copyOf(this.blocks[i], n);   }

            // Exchange
            blocks[i0][j0] = blocks[i0][j0 - 1];
            blocks[i0][j0 - 1] = 0;
            stack.push(new Board(blocks));
        }

        // Right
        if (j0 != n - 1) {
 
            int[][] blocks = new int[n][n];
            for (int i = 0; i < n; i++) {   blocks[i] = Arrays.copyOf(this.blocks[i], n);   }

            // Exchange
            blocks[i0][j0] = blocks[i0][j0 + 1];
            blocks[i0][j0 + 1] = 0;
            stack.push(new Board(blocks));
        }

        return (Iterable<Board>) stack;
    }



    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] blocks = new int[][] {{1, 13, 5, 6},
                                      {15, 12, 14, 11},      
                                      {3, 2, 8, 9},
                                      {7, 10, 0, 4}};
        
        int[][] sol = new int[][] {{1, 2, 3, 4},
                                   {5, 6, 7, 8},
                                   {9, 10, 11, 12},
                                   {13, 14, 15, 0}};

        Board board = new Board(blocks);
        Board solboard = new Board(sol);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.isGoal());

        System.out.println(solboard.hamming());
        System.out.println(solboard.manhattan());
        System.out.println(solboard.isGoal());
        Board a = board.twin();

        System.out.println(solboard.equals(a));
        System.out.println(solboard.equals(new Board(sol)));
        System.out.println(solboard.equals("hello"));


        System.out.println(board.toString());

        for (Board b: solboard.neighbors()) {
            System.out.println(b.toString());
        }

    }           
}
