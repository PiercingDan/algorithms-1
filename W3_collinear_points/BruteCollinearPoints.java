import java.util.Arrays;

public class BruteCollinearPoints {

    private final Point[] points;
    private final Point[][] cp; // Array of Collinear points
    private final LineSegment[] ls;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points - 3 SAME SLOPES from ONE POINT
    {

        this.points = points;

        // check for null array or points
        if (points == null) throw new java.lang.IllegalArgumentException();

        for (Point p: points){
            if (p == null) throw new java.lang.IllegalArgumentException();
        }
        // Check for duplicates
        for (Point p: points){
            for (Point q: points){
                if ((p != q) && (p.compareTo(q) == 0)) { throw new java.lang.IllegalArgumentException(); }
            }
        }


        int n = points.length;
        
        this.cp = new Point[n][2];

        Point placeholder = new Point(-9999, -9999);

        Arrays.fill(cp, new Point[] {placeholder, placeholder });

        // for (Point[] pq : cp) {
        //     pq = new Point[] { placeholder, placeholder }; 
        //     System.out.println(pq[0].toString());
        // }


        int ind = 0;

        for (int i = 0; i < n; i++) {

            Point[] tmp = new Point[4]; // Holding the points

            for (int j = 0; j < n; j++) {
                if (j != i) {
                    // System.out.println("COMPARE: "+points[j].compareTo(points[i]));
                    double slope1 = points[i].slopeTo(points[j]);
                    //System.out.println();
                    //System.out.println(points[i] + " " + points[j]);
                    //System.out.println(slope1);
            
                    for (int k = 0; k < n; k++) {
                        if ((k != i) && (k != j)) {
                        // if ((points[k].compareTo(points[i]) != 0) && (points[k].compareTo(points[j]) != 0)){
                            double slope2 = points[i].slopeTo(points[k]);

                            for (int l = 0; l < n; l++) {
                                if ((l != i) && (l != j) && (l != k)) {
                                // if ((points[l].compareTo(points[i]) != 0) && (points[l].compareTo(points[j]) != 0) && (points[l].compareTo(points[k]) != 0)) {

                                    double slope3 = points[i].slopeTo(points[l]);
                                   
                                    //Collinear
                                    if ((slope1 == slope2) && (slope2 == slope3)) {
                                        // System.out.println("COLLINEAR");
                                        // System.out.println(i+" "+j+" "+k+" "+l+" ");
                                        // System.out.println(slope1 + " " + slope2 + " " + slope3); 

                                        tmp[0] = points[i];
                                        tmp[1] = points[j];
                                        tmp[2] = points[k];
                                        tmp[3] = points[l];
                                        Arrays.sort(tmp);
                                        // for (Point p : tmp) { System.out.println(p);   } 

                                        // System.out.println(tmp[0] + " " + tmp[3]);

                                        boolean duplicate = false;
                                        for (Point[] pq : cp) {
                                            // System.out.println(pq[0].toString() + " " + pq[1].toString());
                                            // System.out.println(pq[0]);
                                            if ((pq[0].compareTo(tmp[0]) == 0) && (pq[1].compareTo(tmp[3]) == 0)) {
                                                // System.out.println(tmp[0] + " " + tmp[3] + " UH OH");
                                                duplicate = true;
                                            } 
                                        }
                                        if (!duplicate) {
                                            // System.out.println("\nNOT DUPLICATE"); 
                                            // for (Point p : tmp) { System.out.println(p);   } 
                                            // System.out.println(slope1 + " " + slope2 + " " + slope3);
                                            // System.out.println("Index = "+ind);                 
                                            // System.out.println(i+" "+j+" "+k+" "+l+" ");
                                            cp[ind++] = new Point[] {tmp[0], tmp[3]}; } // Endpoints only
                                            // System.out.println();

                                    }
                                }
                            }
                        }
                    }
                }
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
        return this.ls;
    }
    public static void main(String[] args) {
        Point[] points = new Point[] {new Point(0, 0),
                                      new Point(0, 1),
                                      new Point(0, 2),
                                      new Point(1, 1),
                                      new Point(1, 0),
                                      new Point(2, 0),
                                      new Point(3, 0),
                                      new Point(0, 3),
                                      new Point(1, 3),
                                      new Point(2, 2),
                                      new Point(3, 3)
                                      };   

        
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        
        Point a = new Point(1, 0);
        Point b = new Point(0, 1);

        LineSegment[] ls_array = bcp.segments();

        for (LineSegment ls: ls_array) {
            System.out.println(ls);
        }

        // System.out.println(a.slopeTo(b));

    }
}
