package concurrency_inpractice_book.inmutability;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Delegating thread safety to a ConcurrentHashMap
 */
public class DelegatingVehicleTracker {
	private final Map<String, Point> locations;
	private final Map<String, Point> unmodifiableLocations;
	
	public DelegatingVehicleTracker(Map<String, Point> locations) {
		this.locations = new ConcurrentHashMap<>(locations);
		this.unmodifiableLocations = Collections.unmodifiableMap(this.locations);
	}
	
	public Map<String, Point> getLocationsCopy() {
		return Collections.unmodifiableMap(new HashMap<>(locations));
	}
	
	public Map<String, Point> getLocationsLive() {
		return unmodifiableLocations;
	}
	
	public Point getLocation(String vehicle) {
		Point location = locations.get(vehicle);
		return location != null? location: new Point(-1, -1);
	}
	
	public void setLocation(String vehicle, int x, int y) {
		if (locations.replace(vehicle, new Point(x, y)) == null) {
			throw new IllegalArgumentException("invalid vehicle");
		}
	}
}
