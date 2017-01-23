package scjp_book;

public class DeadLockRiskExample {
	/*
	 * Code like this almost never results in deadlock because the CPU has to
	 * switch from the reader thread to the writer thread at a particular point
	 * in the code, and the chances of deadlock occurring are very small. The
	 * application may work fine 99.9 percent of the time.
	 * 
	 */
	public static class Resource {
		public int value;
	}

	private Resource resourceA = new Resource();
	private Resource resourceB = new Resource();

	public int read() {
		synchronized (resourceA) {// May deadlock here
			synchronized (resourceB) {
				return resourceA.value + resourceB.value;
			}
		}
	}

	public void write(int a, int b) {
		synchronized (resourceB) { // May deadlock here
			synchronized (resourceA) {
				resourceA.value = a;
				resourceB.value = b;
			}
		}
	}
}
