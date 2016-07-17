import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        Point[] pointsCopy = points.clone();
        checkDuplicatePoints(pointsCopy);
        ArrayList<LineSegment> collinearSegments = new ArrayList<>();
        Arrays.sort(pointsCopy);

        int N = pointsCopy.length;
        for (int i = 0; i < N - 1; i++) {
            final Point p = pointsCopy[i];
            Point[] pointsSlope = pointsCopy.clone();
            Arrays.sort(pointsSlope, p.slopeOrder());
            List<Point> slopePts = new ArrayList<Point>();
            int count = 0;
            double lastSlope = 0.0;
            Point lastPoint = null;

            for (Point q : pointsSlope) {
                double currentSlope = p.slopeTo(q);
                if (currentSlope != lastSlope) {
                    lastSlope = currentSlope;
                    if (count >= 3) {
                        slopePts.add(p);
                        Collections.sort(slopePts);
                        if (p.equals(slopePts.get(0))) {
                            collinearSegments.add(new LineSegment(p, lastPoint));
                        }
                    }
                    slopePts.clear();
                    count = 0;
                }
                slopePts.add(q);

                lastPoint = q;
                ++count;
            }
            if (count >= 3) {
                slopePts.add(p);
                Collections.sort(slopePts);
                if (p.equals(slopePts.get(0))) {
                    collinearSegments.add(new LineSegment(p, lastPoint));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
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

}
