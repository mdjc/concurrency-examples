package concurrency_inpractice_book.syncronized;

public class MutablePoint {
	public int x, y;
	public MutablePoint() {x = 0; y = 0;}

	public MutablePoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public MutablePoint(MutablePoint p) {
		this.x = p.x;
		this.y = p.y;
	}
}
