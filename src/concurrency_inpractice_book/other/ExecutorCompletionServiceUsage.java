package concurrency_inpractice_book.other;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * Using CompletionService to retrieve the future tasks
 *
 * @author Mirna
 *
 */
public class ExecutorCompletionServiceUsage {
	private static class MathTask implements Callable<Integer>{
		private final int a;
		private final int b;
		private final int seconds;
		
		public MathTask(int a, int b, int seconds) {
			this.a = a;
			this.b = b;
			this.seconds = seconds;
		}

		@Override
		public Integer call() throws Exception {
			Thread.sleep(seconds * 1000);
			return a + b;
		}
	}
	
	public static void main(String[] args) {
		final int n = 5;
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<Integer> compService = new ExecutorCompletionService<>(executor);
		
		submitTasks(n, compService);		
		printResults(n, compService);
		
		try {
			executor.shutdown();
			executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.HOURS);
			System.out.println("done");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			executor.shutdownNow();
		}
	}

	private static void printResults(final int n, CompletionService<Integer> compService) {
		for (int i = 1; i <= n; i++) {
			try {
				Integer result = compService.take().get();
				System.out.println(String.format("Result is %d", result) );
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private static void submitTasks(final int n, CompletionService<Integer> compService) {
		for (int i = 1; i <= n; i++) {
			compService.submit(new MathTask(5, 2 * i, i));
		}
	}
}
