package concurrency_inpractice_book.other;


public class InterruptExample {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					System.out.println("interrupted");
				}
			}
		};
		thread.start();
		Thread.sleep(5000);
		thread.interrupt();
	}
}
