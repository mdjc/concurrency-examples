package concurrency_inpractice_book.inmutability;

import concurrency_inpractice_book.Immutable;

@Immutable
public class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}