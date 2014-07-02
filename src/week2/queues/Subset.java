package week2.queues;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class Subset {
	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("src/week2/queues/input"));
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			rq.enqueue(item);
		}
		for (String s : rq) {
			if(k <= 0) break;
			StdOut.print(s + "\n");
			k--;
		}
	}

}
