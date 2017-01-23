package concurrency_inpractice_book.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Run two time tasks, on will be allowed to complete and the other one will
 * timeout method Future.cancel won't stop the tread, so we have to poll the
 * isInterrupted status to detect the interruption and actually exit the task.
 * If we are calling sleep, wait and other methods that respond to interruption
 * we won't need to poll this flag.
 * 
 * http://stackoverflow.com/questions/11158454/future-task-of-executorservice-not-truly-cancelling
 * @author Mirna
 *
 */
public class TimedRun {
	private static class LongRunningTask implements Runnable {
		public void run() {
			System.out.println("Executing long running task....");

			for (long i = 1; i < 9_000_000_000L; i++) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("interrupted");
					return;
				}
				if (i % 9372835 == 0)
					System.out.println("working  " + i);
			}
		}
	}

	private static final ExecutorService taskExec = Executors.newCachedThreadPool();

	public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
		Future<?> task = taskExec.submit(r);
		try {
			task.get(timeout, unit);
		} catch (TimeoutException e) {
			System.out.println("timeout");
		} catch (ExecutionException e) {
			throw launderThrowable(e.getCause());
		} finally {
			task.cancel(true);
			System.out.println("cancelled");
		}
	}

	private static RuntimeException launderThrowable(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else if (t instanceof Error) {
			throw (Error) t;
		}
		return new IllegalStateException("not unchecked t", t);
	}

	public static void main(String[] args) throws InterruptedException {
		timedRun(() -> {
			System.out.println("doing the job");
		}, 5, TimeUnit.SECONDS);

		System.out.println();
		timedRun(new LongRunningTask(), 1, TimeUnit.SECONDS);

		taskExec.shutdown();
		taskExec.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
	}
}