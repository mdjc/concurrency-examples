package scjp_book;

import java.util.stream.IntStream;

/*
 * notifyAll and wait methods usage
 */
public class Reader extends Thread {
	private static class Calculator extends Thread {
		private int total;
		private boolean calculationCompleted;

		@Override
		public void run() {
			synchronized(this) {
				IntStream.range(0, 10001).forEach((i) -> total += i);
				calculationCompleted = true;
				notifyAll();
			}			
		}
	}

	private Calculator calculator;

	public Reader(Calculator calculator) {
		this.calculator = calculator;
	}

	public void run() {
		synchronized (calculator) {
			try {
				System.out.println("Waiting for calculations");

				while (!calculator.calculationCompleted) {
					calculator.wait();
				}
				System.out.println("total is " + calculator.total);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	public static void main(String[] args) {
		Calculator calculator = new Calculator();
		new Reader(calculator).start();
		new Reader(calculator).start();
		new Reader(calculator).start();
		calculator.start();
	}
}
