package scjp_book;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SyncronizedClassIssues {
	// If we run this code several times we may get a
	// java.lang.IndexOutOfBoundsException. Which means our class is not thread
	// safe, even though names list is.
	// Solution: synchronized add and remove method
	private static class NameList {
		private List<String> names = Collections.synchronizedList(new LinkedList<>());

		public void add(String name) {
			names.add(name);
		}

		public String removeFirst() {
			if (names.size() > 0) {
				return names.remove(0);
			}

			return null;
		}
	}

	public static void main(String[] args) {
		final NameList n1 = new NameList();
		n1.add("Mirna");
		Runnable nameDropper = () -> System.out.println(n1.removeFirst());
		new Thread(nameDropper).start();
		new Thread(nameDropper).start();
	}
}
