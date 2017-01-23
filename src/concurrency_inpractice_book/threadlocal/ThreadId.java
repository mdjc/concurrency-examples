package concurrency_inpractice_book.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadId {
	private static final AtomicInteger id = new AtomicInteger();
	
	private static ThreadLocal<Integer> localId = ThreadLocal.withInitial(() -> id.incrementAndGet());
	
	public static Integer get() {
		return localId.get();
	}	
	
	public static void main(String[] args) {
		Runnable task = () -> {
			System.out.println(ThreadId.get());
		};
		
		for (int i = 0; i < 10; i++) {
			new Thread(task).start();
		}
	}
}
