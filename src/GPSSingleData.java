public class GPSSingleData {
    private float speed;
    private double lon;
    private double lat;
    private long timestamp;

    public GPSSingleData(float speed, double lon, double lat, long timestamp) {
        this.speed = speed;
        this.lon = lon;
        this.lat = lat;
        this.timestamp = timestamp;
    }

    public float getSpeed() {
        return speed;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return speed + " " + lon + " " + lat + " " + timestamp;
    }
}
