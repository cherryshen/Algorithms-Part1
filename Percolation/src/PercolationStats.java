import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import static java.lang.Math.sqrt;

public class PercolationStats {
    private double[] openArray;
    private int sampleSize;

    public PercolationStats(int N, int T) {
        // perform T independent experiments on an N-by-N grid
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException("i, j must be > 0");
        }
        this.sampleSize = T;
        this.openArray = new double[T];
        for (int ind = 0; ind < T; ++ind) {
            Percolation percolation = new Percolation(N);
            int openCount = 0;
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, N + 1);
                int j = StdRandom.uniform(1, N + 1);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    ++openCount;
                }
            }
            this.openArray[ind] = (double) openCount / (double) (N * N);
        }
    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(this.openArray);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(this.openArray);

    }

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        double mean = this.mean();
        return mean - (1.96* (this.stddev()/sqrt(this.sampleSize)));
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        double mean = this.mean();
        return mean + (1.96* (this.stddev()/sqrt(this.sampleSize)));
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean is =" + ps.mean());
        StdOut.println("stddev is: " + ps.stddev());
        StdOut.println("confidence level is is: " + ps.confidenceLo() + "," + ps.confidenceHi());
    }

}
