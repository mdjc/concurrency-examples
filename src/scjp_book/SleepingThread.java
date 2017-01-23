package scjp_book;

import java.util.stream.IntStream;

public class SleepingThread {
	public static void main(String[] args) {
		Runnable job = () -> {
			IntStream.range(1, 100).forEach(i -> {
				try {
					Thread.sleep(100);
					if (i % 10 == 0) {
						System.out.println(i);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		};
		
		new Thread(job).start();;
	}
}
