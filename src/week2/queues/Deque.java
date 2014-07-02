package week2.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class Deque<Item> implements Iterable<Item> {
	private int N; // size of deque
	private Node<Item> first, last;

	private class Node<Item> {
		private Item item;
		private Node<Item> next;
		private Node<Item> prev;
	}

	public Deque() {
		// construct an empty deque
		first = null;
		last = null;
		N = 0;
	}

	public boolean isEmpty() {
		// is the deque empty?
		return N == 0;
	}

	public int size() {
		// return the number of items on the deque
		return N;
	}

	public void addFirst(Item item) {
		// insert the item at the front
		if (null == item)
			throw new NullPointerException();
		Node<Item> oldFirst = first;
		first = new Node<Item>();
		first.item = item;
		first.next = oldFirst;
		first.prev = null;
		if (null != oldFirst)
			oldFirst.prev = first;
		N++;
		if (N == 1)
			last = first;
	}

	public void addLast(Item item) {
		// insert the item at the end
		if (null == item)
			throw new NullPointerException();
		Node<Item> oldLast = last;
		last = new Node<Item>();
		last.item = item;
		last.next = null;
		last.prev = oldLast;
		if (null != oldLast)
			oldLast.next = last;
		N++;
		if (N == 1)
			first = last;
	}

	public Item removeFirst() {
		// delete and return the item at the front
		if (isEmpty())
			throw new java.util.NoSuchElementException("Deque Underflow");
		Item item = first.item;
		first = first.next;
		if (null != first)
			first.prev = null;
		N--;
		return item;
	}

	public Item removeLast() {
		// delete and return the item at the end
		if (isEmpty())
			throw new java.util.NoSuchElementException("Deque Underflow");
		Item item = last.item;
		last = last.prev;
		if (null != last)
			last.next = null;
		N--;
		return item;
	}

	public Iterator<Item> iterator() {
		// return an iterator over items in order from front to end
		return new ListIterator<Item>(first);
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;

		public ListIterator(Node<Item> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	private void printDeque() {
		for (Item item : this) {
			System.out.print(item + " ");
		}

	}

	public static void main(String[] args) {
		// unit testing
		Deque<String> d = new Deque<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-"))
				d.addLast(item);
			else if (!d.isEmpty())
				StdOut.print("\n" + d.removeFirst() + " removed");
			d.printDeque();
		}
		
		StdOut.println("(" + d.size() + " left on Deque)");

	}
}