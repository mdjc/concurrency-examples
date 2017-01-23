package concurrency_inpractice_book.latches;

import java.util.concurrent.CountDownLatch;

public class CountDownUsage {
	private static final CountDownLatch initialGate = new CountDownLatch(1);
	
	private static class Task implements Runnable {
		@Override
		public void run() {
			try {
				initialGate.await();
				doTheJob();
			} catch (InterruptedException e) {
				System.out.println("interrupted");
				Thread.currentThread().interrupt();
			}
			
		}

		private void doTheJob() {
			System.out.println("Doing the job");
		}
	}
	
	private static class Prerequisite implements Runnable {
		public void run() {
			System.out.println("Completing Prerequisite");
			initialGate.countDown();
		}
	}
	
	public static void main(String[] args) {
		for (int i = 1; i < 4; i++) {
			new Thread(new Task()).start();;
		}
		
		new Thread(new Prerequisite()).start();;
	}
}
