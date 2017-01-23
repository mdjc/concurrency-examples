package concurrency_inpractice_book.visibility;

import concurrency_inpractice_book.NonThreadSafe;

@NonThreadSafe
public class NonVisibility {
	// because we are unsafely sharing variables this program might never end,
	// since reader might not see the updated values of variables ready and
	// myNumber
	private static boolean ready;
	private static int myNumber;

	public static void main(String[] args) {
		Runnable reader = () -> {
			while (!ready) {
				Thread.yield();
			}
			System.out.println(myNumber);
		};

		new Thread(reader);
		myNumber = 46;
		ready = true;
	}
}
