package scjp_book;

public class DeadLockRiskSolved {
	/*
	 * There are design approaches that can help avoid deadlock, including
	 * strategies for always acquiring locks in a predetermined order like here:
	 * acquiring A lock and then B Lock in both read and write method
	 */
	public static class Resource {
		public int value;
	}

	private Resource resourceA = new Resource();
	private Resource resourceB = new Resource();

	public int read() {
		synchronized (resourceA) {
			synchronized (resourceB) {
				return resourceA.value + resourceB.value;
			}
		}
	}

	public void write(int a, int b) {
		synchronized (resourceA) {
			synchronized (resourceB) {
				resourceA.value = a;
				resourceB.value = b;
			}
		}
	}
}
