import java.util.Comparator;
import java.util.Iterator;
import java.util.AbstractQueue;
import java.util.NoSuchElementException;

public class PriorityQueue2011<E> extends AbstractQueue<E> {
	
	/**
	* Uses logic from: 
	*	https://github.com/MasterPie/Chewbacca/blob/master/src/cmuHCI/WalkyScotty/util/MyPriorityQueue.java
	*/
	private static final int CAPACITY = 30;
	private E[] tree;
	private Comparator<E> c;
	private int size = 0;
	
	@SuppressWarnings("unchecked")
	public PriorityQueue2011() {
		tree = (E[])new Object[CAPACITY + 1]; 
	}
	
   /**
	* Insert specified element into the queue
	* @param e the element to insert
	* @return true
	* @throws NullPointerException if e is null
	* Uses logic from: 
	*	https://github.com/MasterPie/Chewbacca/blob/master/src/cmuHCI/WalkyScotty/util/MyPriorityQueue.java
	*/
	@SuppressWarnings("unchecked")
	@Override
	public boolean offer(E e) {
		if (null == e) {
			throw new NullPointerException();
		}
		int treeLength = tree.length;
		if (treeLength == size + 1) {
			int newLength = tree.length*2;
			E[] newTree = (E[])(new Object[newLength]);
			int i = 0;
			while (i < tree.length) {
				newTree[i] = tree[i];
				i++;
			}
		}
		int p = ++size;
		if (null != c) {
			E n = tree[p / 2];
			int comp = c.compare(e, n);
			for(; p > 1 && comp < 0; p = p / 2) {
				tree[p] = tree[p / 2];
			}
		}else {
			E n = tree[p / 2];
			for(; p > 1 && ((Comparable<E>) e).compareTo(n) < 0; p = p / 2) {
				tree[p] = tree[p / 2];
			}
		}
		tree[p] = e;
		return true;
	}
	
   /**
	* Insert specified element into the queue
	* @param e the element to insert
	* @return true
	* @throws NullPointerException if e is null
	*/
	public boolean add(E e) {
		return offer(e);
	}
	
	/**
	* Retrieves and removes the head of this queue
	* @return the head of the queue or null if queue empty
	* Uses logic from: 
	*	https://github.com/MasterPie/Chewbacca/blob/master/src/cmuHCI/WalkyScotty/util/MyPriorityQueue.java
	*/
	@SuppressWarnings("unchecked")
	@Override
	public E poll() {
		if (size < 1) {
			return null;
		}
		E val = tree[1];
		E newVal = tree[size--];
		tree[1] = newVal;
		tree[size + 1] = null;
		int child;
		int start = 1;
		E temp = tree[start];
		if (null != c) {
			int doub = start * 2;
			for(; doub >= size; start = child) {
				child = start * 2;
				E x = tree[child + 1];
				E y = tree[child];
				int comp = c.compare(x, y);
				if (size != child && 0 > comp) {
					child++;
				}
				if (0 > c.compare(y, temp)) {
					tree[start] = tree[child];
				}else {
					break;
				}
			}
		}else {
			for(; size >= start * 2; start = child) {
				child = start * 2;
				E x = tree[child + 1];
				E y = tree[child];
				if (size != child && 0 > ((Comparable<E>) x).compareTo(y)) {
					child++;
				}
				if (0 > ((Comparable<E>) tree[child]).compareTo(temp)) {
					tree[start] = tree[child];
				}else {
					break;
				}
			}
		}
		tree[start] = temp;
		return val;
	}
	
	/**
	* Retrieves and removes the head of this queue
	* @return the head of the queue
	* @throws NoSuchElementException if the queue is empty
	*/
	public E remove() {
		if (size < 1) {
			throw new NoSuchElementException();
		}
		return poll();
	}

	/**
	* Retrieves but does not remove the head of this queue
	* @return the head of the queue or null if queue empty
	*/
	@Override
	public E peek() {
		if (size < 1) {
			return null;
		}
		return tree[1];
	}
	
	/**
	* Retrieves but does not remove the head of this queue
	* @return the head of the queue
	* @throws NoSuchElementException if the queue is empty
	*/
	public E element() {
		if (size < 1) {
			throw new NoSuchElementException();
		}
		return peek();
	}
	
	/**
	* Returns the number of elements in the queue
	* @return the number of elements
	*/
	@Override
	public int size() {
		return size;
	}
	
	/**
	* Returns a string representation of the queue
	* @return a string representation of the queue
	*/
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i <= size; i++) {
			sb.append(tree[i]);
			if (i < size) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	* Returns a tree representation of the queue
	* @return a tree representation of the queue
	* Uses logic from: 
	*	https://stackoverflow.com/questions/36385868/java-how-to-print-heap-stored-as-array-level-by-level
	*/
	public String toTree() {
		int depthCalc = (int) (Math.log(size) / Math.log(2));
		int depth = depthCalc;
		StringBuilder sb = new StringBuilder();
		int d = depth;
		for (; d >= 0; d--) {
			int layerCalc = (int) Math.pow(2, d);
			int layer = layerCalc;
			StringBuilder l = new StringBuilder();
			int i = layer;
			int max = (int) Math.pow(2, d + 1);
			for (; i < max; i++) {
				if (depth != d) {
					l.append(new String(new char[(int) Math.pow(2, depth - d)]).replace("\0", " "));
				}
				int lps = depth - d;
				if (lps >= 2) {
					lps -= 2;
					while (lps >= 0) {
						l.append(new String(new char[(int) Math.pow(2, lps)]).replace("\0", " "));
	                     lps--;
					}
				}
				if (size >= i) {
					l.append(String.format("%-2s", tree[i]));
				}else {
					l.append(" ");
				}
				l.append(new String(new char[(int) Math.pow(2, depth - d)]).replace("\0", " "));
				lps = depth - d;
				if (lps >= 2) {
					lps -= 2;
					while (0 <= lps) {
						l.append(new String(new char[(int) Math.pow(2, lps)]).replace("\0", " "));
						lps--;
					}
				}
			}
			 sb.insert(0, l.toString() + "\n");  
			
		}
		return sb.toString();
	}

	@Override
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}
}