package week1.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] grid, isFull;
	private int N;
	private WeightedQuickUnionUF uf;

	public Percolation(int N) {
		// create N-by-N grid, with all sites blocked
		if (N <= 0)
			throw new IllegalArgumentException();

		this.N = N;
		grid = new boolean[N + 1][N + 1];
		isFull = new boolean[N + 1][N + 1];

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				grid[i][j] = false;
				isFull[i][j] = false;
			}
		}
		uf = new WeightedQuickUnionUF(N * N + 2);
	}

	public void open(int i, int j) {
		// open site (row i, column j) if it is not already
		if (i < 1 || i > N || j < 1 || j > N)
			throw new java.lang.IndexOutOfBoundsException();

		grid[i][j] = true;
		// connect to adjacent open sites
		if (i == 1) {
			isFull[i][j] = true;
			uf.union(0, (i - 1) * N + j);
			if (i + 1 <= N && isOpen(i + 1, j) && !isFull[i + 1][j]) {
				fillSite(i + 1, j);
			}
		}
		if (i == N) {
			uf.union(N * N + 1, (i - 1) * N + j);
		}

		if (i > 1) { // connect to top site if its open
			if (isOpen(i - 1, j)) {
				uf.union((i - 1 - 1) * N + j, (i - 1) * N + j);
				if (isFull[i - 1][j] && !isFull[i][j]) {
					fillSite(i, j);
				}
			}
		}

		if (i < N) { // connect to bottom site if its open
			if (isOpen(i + 1, j)) {
				uf.union((i + 1 - 1) * N + j, (i - 1) * N + j);
				if (isFull[i + 1][j] && !isFull[i][j]) {
					fillSite(i, j);
				}
			}
		}

		if (j > 1) { // connect to left site if its open
			if (isOpen(i, j - 1)) {
				uf.union((i - 1) * N + j - 1, (i - 1) * N + j);
				if (isFull[i][j - 1] && !isFull[i][j]) {
					fillSite(i, j);
				}
			}
		}
		if (j < N) { // connect to right site if its open
			if (isOpen(i, j + 1)) {
				uf.union((i - 1) * N + j + 1, (i - 1) * N + j);
				if (isFull[i][j + 1] && !isFull[i][j]) {
					fillSite(i, j);
				}
			}
		}
	}

	private void fillSite(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N) {
			return;
		}
		if (isFull[i][j])
			return;

		isFull[i][j] = true;

		if (i > 1 && isOpen(i - 1, j) && !isFull[i - 1][j])
			fillSite(i - 1, j);
		if (i < N && isOpen(i + 1, j) && !isFull[i + 1][j])
			fillSite(i + 1, j);
		if (j > 1 && isOpen(i, j - 1) && !isFull[i][j - 1])
			fillSite(i, j - 1);
		if (j < N && isOpen(i, j + 1) && !isFull[i][j + 1])
			fillSite(i, j + 1);

	}

	public boolean isOpen(int i, int j) {
		// is site (row i, column j) open?
		if (i < 1 || i > N || j < 1 || j > N)
			throw new java.lang.IndexOutOfBoundsException();
		return grid[i][j];
	}

	public boolean isFull(int i, int j) {
		// is site (row i, column j) full?
		if (i < 1 || i > N || j < 1 || j > N)
			throw new java.lang.IndexOutOfBoundsException();
		// return isOpen(i, j) ? uf1.connected(0, (i - 1) * N + j) : false;
		return isFull[i][j];

	}

	public boolean percolates() {
		// does the system percolate?
		return uf.connected(0, N * N + 1);
	}
}
