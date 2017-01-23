package concurrency_inpractice_book.syncronized;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import concurrency_inpractice_book.ThreadSafe;

@ThreadSafe

/*
 * This implementation maintains thread safety in part by copying mutable data before returning it to the client (defensive copy),
 * encapsulating the object's state and synchronizing the access to this state.
 */
public class MonitorVehicleTracker {
	private final Map<String, MutablePoint> locations;

	public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
		this.locations = deepcopy(locations);
	}
	
	public synchronized void setLocation(String vehicle, int x, int y) {
		MutablePoint location = locations.get(vehicle);
		
		if (location == null) {
			throw new IllegalArgumentException();
		}
		
		location.x = x;
		location.y = y;
	}
	
	public synchronized Map<String, MutablePoint> getLocations() {
		return deepcopy(this.locations);
	}
	
	public synchronized MutablePoint getLocation(String vehicle) {
		MutablePoint location = locations.get(vehicle);
		return location != null ? new MutablePoint(location) : new MutablePoint(-1,-1);
	}

	private static Map<String, MutablePoint> deepcopy(Map<String, MutablePoint> locations) {
		Map<String, MutablePoint> result = new HashMap<>();
		
		locations.forEach((k, v) -> {
			result.put(k, new MutablePoint(v));
		});
		
		return Collections.unmodifiableMap(result);
	}
}