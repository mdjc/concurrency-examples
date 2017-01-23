package concurrency_inpractice_book.atomicity;

import concurrency_inpractice_book.NonThreadSafe;

@NonThreadSafe
public class NonAtomicCounterThread extends Thread{
	static int count;
	
	@Override
	public void run() {
		count++;
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 300; i++) {
			NonAtomicCounterThread thread = new NonAtomicCounterThread();
			thread.start();
			thread.join();
		}
		
		System.out.println("Final count may vary " + count);
	}
}
