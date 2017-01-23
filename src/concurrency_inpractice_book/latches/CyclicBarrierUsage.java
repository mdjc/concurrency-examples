package concurrency_inpractice_book.latches;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierUsage {
	private static final CyclicBarrier barrier = new CyclicBarrier(4, () -> System.out.println("Barrier met"));
	
	private static class Worker implements Runnable{
		private final String name;
		private final int seconds;
		
		public Worker(String name, int seconds) {
			this.name = name;
			this.seconds = seconds;
		}
		
		@Override
		public void run() {
			try {
				//The sleep is introduced so that every thread calls barrier method after some time.  Sleep time is also in increasing order, which means PARTY-4 should be the last one to call await.
				Thread.sleep(seconds * 1000); 
				System.out.println(name + " is calling await");
				barrier.await();
				System.out.println("continuing with " + name);
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}			
		}
	}
	
	public static void main(String[] args) {
		for (int i = 1; i < 5; i++) {
			new Thread(new Worker("Party-" + i, i)).start();
		}
	}
}