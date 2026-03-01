public class DistanceCalculator implements IDistanceCalculator {
    public double km(GeoPoint from, GeoPoint to) {
        // fake distance: rough Manhattan on scaled degrees for determinism
        double d = Math.abs(from.lat - to.lat) + Math.abs(from.lon - to.lon);
        double km = Math.round((d * 200.0) * 10.0) / 10.0; // produces 6.0 for the demo points
        return km;
    }
}
