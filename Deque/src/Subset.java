import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

    public static void main(String[] args) {
        int numToOutput = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
        rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < numToOutput; ++i) {
        StdOut.println(rq.dequeue());
        }
        }


}
