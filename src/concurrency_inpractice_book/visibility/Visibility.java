package concurrency_inpractice_book.visibility;

import concurrency_inpractice_book.ThreadSafe;

@ThreadSafe
public class Visibility {
	private static volatile boolean ready;
	private static int myNumber;
	
	public static void main(String[] args) {
		new Thread(() -> {
			while(!ready) {
				Thread.yield();
			}
			
			System.out.println("This is my number: " + myNumber);
		}).start();
		
		myNumber = 100;
		ready = true;
	}
}
