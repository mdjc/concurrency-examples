package concurrency_inpractice_book.latches;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class CountDownLatchUsage {
	private static final int N_THREADS = Runtime.getRuntime().availableProcessors();
	static final CountDownLatch startGate = new CountDownLatch(1);
	static final CountDownLatch endGate = new CountDownLatch(N_THREADS);

	public static void runLonTimeTasks(final Runnable task) {

		IntStream.range(0, N_THREADS).forEach(i -> {
			new Thread(() -> {
				try {
					startGate.await();
					task.run();
				} catch (InterruptedException ignore) {
					return;
				} finally {
					endGate.countDown();
				}
			}).start();
		});
	}

	public static void main(String[] args) throws InterruptedException {
		runLonTimeTasks(() -> System.out.println("executing tasks " + Thread.currentThread().getName()));
		System.out.println("Waiting for startGate to open");
		Thread.sleep(5000);
		startGate.countDown();
		endGate.await();
		System.out.println("done");
	}
}
