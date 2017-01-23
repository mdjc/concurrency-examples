package scjp_book;

public class SyncronizedJoinUsage extends Thread {
	private final StringBuffer sb;
	
	public SyncronizedJoinUsage(StringBuffer sb) {
		this.sb = sb;	
	}
	
	@Override
	public void run() {
		synchronized (sb) {
			char letter = sb.length() > 0 ? sb.charAt(sb.length() - 1): 'A' - 1;
			letter++;
			
			for (int i = 0; i < 100; i++) {
				sb.append(letter);
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		StringBuffer sb = new StringBuffer();
		SyncronizedJoinUsage thread1 = new SyncronizedJoinUsage(sb);
		SyncronizedJoinUsage thread2 = new SyncronizedJoinUsage(sb);
		SyncronizedJoinUsage thread3 = new SyncronizedJoinUsage(sb);
		thread1.start();
		thread2.start();
		thread3.start();
		
		thread1.join();
		thread2.join();
		thread3.join();
		
		//main thread waits until thread[1,2,3] finish.
		System.out.println(sb);
	}
}
