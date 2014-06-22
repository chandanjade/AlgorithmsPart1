package week1.percolation;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
	private double[] x;
	private int T;

	public PercolationStats(int N, int T) {
		// perform T independent computational experiments on an N-by-N grid
		if (N <= 0 || T <= 0)
			throw new java.lang.IllegalArgumentException();

		this.T = T;
		
		x = new double[T];

		// perform T experiments
		for (int k = 0; k < T; k++) {
			Percolation p = new Percolation(N);
			int openCount = 0;
			while (!p.percolates()) {
				int i = StdRandom.uniform(1, N + 1);
				int j = StdRandom.uniform(1, N + 1);
				if (!p.isOpen(i, j)) {
					p.open(i, j);
					openCount++;
				}
			}
			x[k] = (double) openCount/(N*N);
		}

	}

	public double mean() {
		// sample mean of percolation threshold
		return StdStats.mean(x);
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		return StdStats.stddev(x);
	}

	public double confidenceLo() {
		// returns lower bound of the 95% confidence interval
		return mean() - (1.96 * stddev()) / Math.sqrt(T);
	}

	public double confidenceHi() {
		// returns upper bound of the 95% confidence interval
		return mean() + (1.96 * stddev()) / Math.sqrt(T);
	}

	public static void main(String[] args) {
		// test client, described below
		if(args.length == 2) {
		PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		System.out.println("mean \t\t\t=" + ps.mean());
		System.out.println("StdDev \t\t\t=" + ps.stddev());
		System.out.println("95% confidence interval\t=" + ps.confidenceLo() + "," + ps.confidenceHi());
		} else {
			throw new java.lang.IllegalArgumentException();
		}

	}
}