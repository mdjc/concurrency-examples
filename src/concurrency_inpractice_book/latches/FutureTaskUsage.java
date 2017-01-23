package concurrency_inpractice_book.latches;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskUsage {
	public static class MyCallableTask implements Callable<String> {
		private final String text;

		public MyCallableTask(String text) {
			this.text = text;
		}

		@Override
		public String call() throws Exception {
			Thread.sleep(3_000);
			return text;
		}
		
	}

	public static void main(String[] args) {		
		ExecutorService executor = null;
		try {
			executor = Executors.newCachedThreadPool();
			FutureTask<String> futureTask1 = new FutureTask<String>(new MyCallableTask("task1"));
			executor.submit(futureTask1);
			System.out.println(futureTask1.get());
			System.out.println("done");
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdownNow();
		}
	}
}
