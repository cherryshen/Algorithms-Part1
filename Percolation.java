import java.util.Arrays;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private boolean[][] grid;
	private int gridSize;
	private int topRow;
	private int bottomRow;
	private WeightedQuickUnionUF unionFind;

	public Percolation(int n) {
		// create N-by-N grid, with all sites blocked
		this.gridSize = n;
		this.grid = new boolean[n][n];
		// we need two extras for the topRow and bottomRow tracker
		this.unionFind = new WeightedQuickUnionUF(n * n + 2);
		this.topRow = 0;
		this.bottomRow = (n * n + 1);

	}

	public void open(int i, int j) {
		this.validateInput(i, j);
		this.grid[i-1][j-1] = true; // this opens up the site
		// check if we are on top row or bottom row, connect to top/bottom
		if (i == 1) {
			unionFind.union(axisToIndex(i, j), topRow);
		}
		if (i == this.gridSize) {
			unionFind.union(axisToIndex(i, j), bottomRow);
		}
		
		// open site (row i, column j) if it is not open already
		// check to see if adjacent sites are open, connect if open
		if (i > 1 && isOpen(i - 1, j)) {
			// check top
			this.grid[i - 1-1][j-1] = true;
			unionFind.union(axisToIndex(i, j), axisToIndex(i - 1, j));
		}
		if (j < this.gridSize && isOpen(i, j + 1)) {
			// check right
			this.grid[i-1][j + 1-1] = true;
			unionFind.union(axisToIndex(i, j), axisToIndex(i, j + 1));
		}
		if (j > 1 && isOpen(i, j - 1)) {
			// check left
			this.grid[i-1][j - 1-1] = true;
			unionFind.union(axisToIndex(i, j), axisToIndex(i, j - 1));
		}
		if (i < this.gridSize && isOpen(i + 1, j)) {
			// check bottom
			this.grid[i + 1-1][j-1] = true;
			unionFind.union(axisToIndex(i, j), axisToIndex(i + 1, j));
		}
	}

	public boolean isOpen(int i, int j) {
		this.validateInput(i, j);
		return this.grid[i-1][j-1] == true;
		// is site (row i, column j) open?
	}

	/**
	 * Is site (row i, column j) full? A full site is an open site that can be
	 * connected to an open site in the top row via a chain of neighboring
	 * (left, right, up, down) open sites.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j) {
		this.validateInput(i, j);
		return unionFind.connected(axisToIndex(i, j), topRow);
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public int axisToIndex(int i, int j) {
		this.validateInput(i, j);
		return ((i - 1) * this.gridSize) + j + 1;
	}

	/**
	 * Checks if i, j are in the range of N
	 * 
	 * @param i
	 * @param j
	 */
	private void validateInput(int i, int j) {
		if (i < 1 || j < 1) {
			throw new IllegalArgumentException("i, j must be > 0");
		}
	}

	public boolean percolates() {
		// does the system percolate?
		return unionFind.connected(topRow, bottomRow);
	}

	public static void main(String[] args) {
		// test client (optional)
	}

}
