package concurrency_inpractice_book.atomicity;

import java.util.concurrent.atomic.AtomicLong;

import concurrency_inpractice_book.ThreadSafe;

@ThreadSafe
/**
 * AtomicVariableClassesUsage
 * @author Mirna
 *
 */
public class AtomicCounterThread extends Thread {
	private static final AtomicLong count = new AtomicLong();
	
	@Override
	public void run() {
		count.incrementAndGet();
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			AtomicCounterThread thread = new AtomicCounterThread();
			thread.start();
			thread.join();
		}
		
		System.out.println("Final count will always be " + AtomicCounterThread.count.get());
	}
}
