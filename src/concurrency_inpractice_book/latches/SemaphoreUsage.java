package concurrency_inpractice_book.latches;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreUsage {	
	private static class Task implements Runnable {
		private final Semaphore availablePermits;
		private static final AtomicInteger count = new AtomicInteger(0);

		public Task(Semaphore availablePermits) {
			this.availablePermits = availablePermits;
		}
		
		@Override
		public void run() {
			try {
				availablePermits.acquire();
				System.out.println("Completing task " + count.incrementAndGet());
				Thread.sleep(2_000);				
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			} finally {
				availablePermits.release();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Semaphore availablePermits = new Semaphore(10);
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for (int i = 0; i < 30; i++) {
			executor.submit(new Task(availablePermits));
		}
		
		executor.shutdown();
		executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.HOURS);
		System.out.println("Done");
	}
	
	
	/**
	 * 
no fair output new Semaphore(10);

Completing task 2
Completing task 3
Completing task 1
Completing task 4
Completing task 5
Completing task 6
Completing task 7
Completing task 8
Completing task 9
Completing task 10
Completing task 11
Completing task 12
Completing task 13
Completing task 15
Completing task 14
Completing task 16
Completing task 17
Completing task 18
Completing task 19
Completing task 20
Completing task 21
Completing task 22
Completing task 23
Completing task 25
Completing task 24
Completing task 28
Completing task 27
Completing task 26
Completing task 30
Completing task 29
Done

output with fairness new Semaphore(10, true);
Completing task 1
Completing task 3
Completing task 2
Completing task 4
Completing task 5
Completing task 6
Completing task 7
Completing task 8
Completing task 9
Completing task 10
Completing task 11
Completing task 12
Completing task 13
Completing task 14
Completing task 15
Completing task 16
Completing task 17
Completing task 18
Completing task 19
Completing task 20
Completing task 21
Completing task 23
Completing task 22
Completing task 24
Completing task 25
Completing task 26
Completing task 27
Completing task 29
Completing task 28
Completing task 30
Done
	 */
}
