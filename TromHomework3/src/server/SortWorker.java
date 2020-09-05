/**
 * SortWorker.java
 */
package server;

import api.Worker;

import java.util.*;


/**
 * @author Derek Trom
 *
 */
class SortWorker<T extends Comparable<? super T>> extends Worker {
	private static final long serialVersionUID = -4051898108461870504L;
	private List<T> list = new ArrayList<T>();
	
	/**
	 * @param id 
	 * @param arr an array of items to be sorted 
	 */
	@SafeVarargs
	SortWorker(UUID id, T ...arr) {
		super(id, "SortWorker");
		list.addAll(Arrays.asList(arr));
	}


	@Override
	public void doWork() {
		Collections.sort(list);
	}
		
	/**
	 * 
	 * @return 
	 */
	List<T> getList() {
		return list;
	}

	/**
	 * Test harness
	 * @param args There are no arguments.
	 */
	public static void main(String[] args) {
		UUID uid = UUID.randomUUID();
		SortWorker<String> sw1 = new SortWorker<String>(uid, "cat", "mouse", "dog");
		sw1.doWork();
		System.out.println(sw1.list);
		
		SortWorker<Integer> sw2 = new SortWorker<Integer>(uid, 3, 1, 5, 4);
		sw2.doWork();
		System.out.println(sw2.list);
	}
}
