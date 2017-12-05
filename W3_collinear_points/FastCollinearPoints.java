import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class FastCollinearPoints {

    private final Point[] points;
    private final Point[][] cp;
    private final LineSegment[] ls;


    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        
        this.points = points;

        if (points == null) throw new java.lang.IllegalArgumentException();

        for (Point p: points){
            if (p == null) throw new java.lang.IllegalArgumentException();
        }

        for (Point p: points){
            for (Point q: points){
                if ((p != q) && (p.compareTo(q) == 0)) { throw new java.lang.IllegalArgumentException(); }
            }
        }

        int n = points.length;
        
        this.cp = new Point[n][2];

        Point placeholder = new Point(-9999, -9999);

        Arrays.fill(cp, new Point[] {placeholder, placeholder });

        int cp_ind = 0;
        
        for (int i = 0; i < n; i++) {
            //Order Points in slope with respect to point i and return copy
            Point[] tmp_points = Arrays.copyOf(points, points.length);
            Arrays.sort(tmp_points, points[i].slopeOrder()); 

            // System.out.println();
            // System.out.println("Point i "+points[i]);
            for (Point tp : tmp_points) {
                // System.out.println(tp);
            }

            Point[] tmp = new Point[n]; 

            tmp[0] = points[i];
            int ind = 1; // Start at 1, first point is point i

            double old_slope = -99.999;

            for (int j = 0; j < n; j++) {
                // Checking for duplicates to point i or to the points beside it
                // if ((points[i].compareTo(tmp_points[j]) == 0) ||
                //     ((j > 0) && (tmp_points[j].compareTo(tmp_points[j-1]) == 0)))
                if (tmp_points[j] == points[i]) {    continue;  }     
                
                double slope = points[i].slopeTo(tmp_points[j]);
                // System.out.println();
                // System.out.println("Point i: "+points[i]+"     Point j : "+tmp_points[j]);
                // System.out.println("Slope: "+slope);
                // System.out.println("Old Slope: "+old_slope);
                // System.out.println("Before Ind: "+ind);
                // System.out.println();

                if ((slope == old_slope)) { // Another collinear Point
                    // System.out.println("A Collinear Point");
                    tmp[ind++] = tmp_points[j];

                }


                else if ((slope != old_slope) && (ind >= 4)) { 
                   
                    int len = 0;
                    for (Point p : tmp) {
                        if (p == null) { break; }
                        len++;
                    } 
               
                    // System.out.println(len);
                    tmp = Arrays.copyOf(tmp, len);

                    Arrays.sort(tmp);

                    boolean duplicate = false;
                    for (Point[] pq : cp) {
                        if ((pq[0].compareTo(tmp[0]) == 0) && (pq[1].compareTo(tmp[tmp.length - 1]) == 0)) {
                            duplicate = true;
                        } 
                    }
                    if (!duplicate) {
                        // System.out.println("\nNOT DUPLICATE"); 
                        cp[cp_ind++] = new Point[] {tmp[0], tmp[tmp.length - 1]}; } // Endpoints only

                    tmp = new Point[n];
                    tmp[0] = points[i];
                    ind = 1;
                    tmp[ind++] = tmp_points[j];
                }

                else if ((slope != old_slope) && (ind == 1)) { // Start of new chain 
                    // System.out.println("New Chain");
                    tmp[ind++] = tmp_points[j];
                }

                else if (slope != old_slope) { // Not enough collinear points
                    // System.out.println("Not Enough Collinear Points");
                    tmp = new Point[n];
                    tmp[0] = points[i];
                    ind = 1;
                    tmp[ind++] = tmp_points[j];

                } // wipe tmp    

                old_slope = slope;
                // System.out.println("After Ind: "+ind);

            }
            // One final check at thend, in case 4-tuple ends on the end

            if ((ind >= 4)) { 
                
                // System.out.println("4 COLLINEAR");
                // Removing null point in tmp  
                int len = 0;
                for (Point p : tmp) {
                    if (p == null) { break; }
                    len++;
                } 
               
                // System.out.println(len);
                tmp = Arrays.copyOf(tmp, len);

                Arrays.sort(tmp);

                boolean duplicate = false;
                for (Point[] pq : cp) {
                    if ((pq[0].compareTo(tmp[0]) == 0) && (pq[1].compareTo(tmp[tmp.length - 1]) == 0)) {
                        duplicate = true;
                    } 
                }
                if (!duplicate) {
                    // System.out.println("\nNOT DUPLICATE"); 
                    cp[cp_ind++] = new Point[] {tmp[0], tmp[tmp.length - 1]}; } // Endpoints only
            }
        }

        // Creating line segment array
        // Not ideal, two loops, one to first figure out the size
        // System.out.println();
        int len = 0;
        for (Point[] pq : cp) {
            if (pq[0] != placeholder) { 
                len++;
            } 
        }
        
        this.ls = new LineSegment[len];
        // System.out.println(len);

        for (int i = 0; i < len; i++) {
            ls[i] = new LineSegment(cp[i][0], cp[i][1]);
            // System.out.println(ls[i]); 
        }
    }    
    public int numberOfSegments()        // the number of line segments
    {
        return ls.length;
    }
    public LineSegment[] segments()                // the line segments
    {
        return ls; 
    }


    public static void main(String[] args) {
        Point[] points = new Point[] {new Point(-1, 0),
                                      new Point(0, 0),
                                      new Point(0, 1),
                                      new Point(0, 2),
                                      new Point(1, 1),
                                      new Point(1, 0),
                                      new Point(2, 0),
                                      new Point(3, 0),
                                      new Point(2, 2),
                                      new Point(3, 3)
                                      };   

        
        FastCollinearPoints fcp = new FastCollinearPoints(points);

        LineSegment[] ls_array = fcp.segments();

        for (LineSegment ls: ls_array) {
            System.out.println(ls);
        }

    // read the n points from a file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // Point[] points = new Point[n];
        // for (int i = 0; i < n; i++) {
        //     int x = in.readInt();
        //     int y = in.readInt();
        //     points[i] = new Point(x, y);
        // }
    
        // // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();
    
        // // print and draw the line segments
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment);
        //     segment.draw();
        // }
        // StdDraw.show();
    
    }
}
