import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        Point[] pointsCopy = points.clone();
        checkDuplicatePoints(pointsCopy);
        ArrayList<LineSegment> collinearSegments = new ArrayList<>();
        Arrays.sort(pointsCopy);

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {
                for (int k = j + 1; k < pointsCopy.length - 1; k++) {
                    for (int l = k + 1; l < pointsCopy.length; l++) {
                        Point p = pointsCopy[i];
                        Point q = pointsCopy[j];
                        Point r = pointsCopy[k];
                        Point s = pointsCopy[l];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            collinearSegments.add(new LineSegment(p, s));
                        }

                    }
                }
            }
        }
        this.segments = collinearSegments.toArray(new LineSegment[collinearSegments.size()]);
    }

    public int numberOfSegments() {
        return this.segments.length;
    }

    public LineSegment[] segments() {
        return this.segments.clone();
    }

    private void checkDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == null) {
                    throw new NullPointerException("One of the points in the array is null");
                }
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("duplicate points");
                }
            }
        }
    }

    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
