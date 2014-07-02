package week2.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q;
	private int N = 0, first = 0, last = 0;
	
	
	public RandomizedQueue() {
		// construct an empty randomized queue
		q = (Item[]) new Object[2];
		N = 0;
	}

	public boolean isEmpty() {
		// is the queue empty?
		return N == 0;
	}

	public int size() {
		// return the number of items on the queue
		return N;
	}

	// resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = N;
    }
    
	public void enqueue(Item item) {
		// add the item
		if(null == item) throw new NullPointerException();
		if(N == q.length) resize(2 * N);
		q[last++] = item;
		if(last == q.length) last = 0;
		N++;
	}

	public Item dequeue() {
		// delete and return a random item
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		int random = getRandomIndex();
		exch(q, first, random);
		Item item = q[first];
		q[first++] = null; // to avoid loitering  
		N--;
		if(first == q.length) first = 0;
		if (N > 0 && N == q.length/4) resize(q.length/2); 
        return item;
	}

	private int getRandomIndex() {
		int random;
		if(first < last) {
			random = StdRandom.uniform(first, last);
		} else if (last == 0 ) {
			random = StdRandom.uniform(first, q.length);
		} else {
			int r1 = StdRandom.uniform(0,last);
			int r2 = StdRandom.uniform(first, q.length);
			random = StdRandom.uniform() < 0.5 ? r1 : r2; 
		}
		return random;
	}

	private void exch(Item[] arr, int first, int random) {
		Item temp = arr[first];
		arr[first] = arr[random];
		arr[random] = temp;
	}

	public Item sample() {
		// return (but do not delete) a random item
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		int random = getRandomIndex();
		return q[random];
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new RandomIterator();
	}
	
	// an iterator, doesn't implement remove() since it's optional
    private class RandomIterator implements Iterator<Item> {
    	private Item[] s = getShuffled();
        private int i = 0;
        private Item[] getShuffled() {
        	Item[] temp = (Item[]) new Object[q.length];
            for (int i = 0; i < N; i++) {
                temp[i] = q[(first + i) % q.length];
            }
            for (int i = 0; i < N; i++) {
            	int random = StdRandom.uniform(0, i + 1);
                exch(temp, random, i); 
            }
			return temp;
		}
        public boolean hasNext()  { return i < N;                               }
		public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = s[i++];
            return item;
        }
    }
    
    private void printRandomizedQueue() {
    	System.out.print("\n Queue : ");
    	for(Item item : this) {
  	   		System.out.print(item + " ");
    	}
    	
    }

	public static void main(String[] args) {
		// unit testing
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
            if (!item.equals("-")) rq.enqueue(item);
            else if (!rq.isEmpty()) StdOut.print( "\n" + rq.dequeue()+ " removed");
            rq.printRandomizedQueue();
        }
        StdOut.println("(" + rq.size() + " left on Deque)");
	}
}