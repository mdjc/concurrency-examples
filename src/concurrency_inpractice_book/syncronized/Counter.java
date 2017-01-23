package concurrency_inpractice_book.syncronized;

import concurrency_inpractice_book.ThreadSafe;

//Example of the java monitor pattern

@ThreadSafe
public class Counter {
	private long value = 0;
	
	public synchronized void increment() {
		if (value == Long.MAX_VALUE) {
			throw new IllegalStateException("Counter overflow");
		}
		
		value++;
	}
	
	public synchronized long getValue() {
		return value;
	}
}
