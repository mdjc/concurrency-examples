package concurrency_inpractice_book.inmutability;

import java.util.HashSet;
import java.util.Set;

import concurrency_inpractice_book.Immutable;

//Simple immutable object
@Immutable
public final class ThreeStooges {
	private final Set<String> stooges = new HashSet<>();
	public ThreeStooges() {
		stooges.add("Mary");
		stooges.add("Samanta");
		stooges.add("Kim");
	}
	
	public boolean isStooge(String name) {
		return stooges.contains(name);
	}
	
	@Override
	public String toString() {
		return stooges.toString();
	}
}
