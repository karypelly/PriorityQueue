import java.util.PriorityQueue;

public class PQSort{

   /**
	* Heap Sort algorithm that relies on the PriorityQueue implementation 
	* Uses logic from: 
	*	https://github.com/jsquared21/Intro-to-Java-Programming/blob/master/Exercise_23/Exercise_23_05/Exercise_23_05.java
	*/
	static <E extends Comparable <? super E>> void heapSort(E[] a) {
		PriorityQueue<E> pq = new PriorityQueue<>();
		int i = 0;
		for (; i < a.length; i++)
			pq.add(a[i]);
		int j = a.length - 1;
		for (; j >= 0; j--)
			a[j] = pq.remove();
	}
	
   /**
    * Heap Sort algorithm that relies on the PriorityQueue2011 implementation 
	* Uses logic from: 
	*	https://github.com/jsquared21/Intro-to-Java-Programming/blob/master/Exercise_23/Exercise_23_05/Exercise_23_05.java
	*/
	static <E extends Comparable <? super E>> void heapSort2011(E[] a) {
		PriorityQueue2011<E> pq = new PriorityQueue2011<>();
		int i = 0;
		for (; i < a.length; i++)
			pq.add(a[i]);
		int j = a.length - 1;
		for (; j >= 0; j--)
			a[j] = pq.remove();
	}
}