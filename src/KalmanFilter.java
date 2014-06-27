import com.google.common.eventbus.Subscribe;

public class KalmanFilter {


    private long timeStamp; // millis
    private double latitude; // degree
    private double longitude; // degree
    private float variance; // P matrix. Initial

    public KalmanFilter() {
        registerBus();
        variance = -1;
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onLocationUpdate(GPSSingleData singleData) {
        // if gps receiver is able to return 'accuracy' of position, change last variable
        process(singleData.getSpeed(), singleData.getLatitude(), singleData.getLongitude(), singleData.getTimestamp(),
                Constants.MIN_ACCURACY);
    }

    // Init method (use this after constructor, and before process)
    // if you are using last known data from gps)
    public void setState(float speed, double latitude, double longitude, long timeStamp, float accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
        this.variance = accuracy * accuracy;
    }

    /**
     * Kalman filter processing for latitude and longitude
     *
     * newLatitude - new measurement of latitude
     * newLongitude - new measurement of longitude
     * accuracy - measurement of 1 standard deviation error in meters
     * newTimeStamp - time of measurement in millis
     */
    public void process(float newSpeed, double newLatitude, double newLongitude, long newTimeStamp, float newAccuracy) {
        // Uncomment this, if you are receiving accuracy from your gps
        // if (newAccuracy < Constants.MIN_ACCURACY) {
        //      newAccuracy = Constants.MIN_ACCURACY;
        // }
        if (variance < 0) {
            // if variance < 0, object is unitialised, so initialise with current values
            setState(newSpeed, newLatitude, newLongitude, newTimeStamp, newAccuracy);
        } else {
            // else apply Kalman filter
            long timestamp = newTimeStamp - this.timeStamp;
            if (timestamp > 0) {
                // time has moved on, so the uncertainty in the current position increases
                variance += timestamp * newSpeed * newSpeed / 1000;
                timeStamp = newTimeStamp;
            }

            // Kalman gain matrix 'k' = Covariance * Inverse(Covariance + MeasurementVariance)
            // because 'k' is dimensionless,
            // it doesn't matter that variance has different units to latitude and longitude
            float k = variance / (variance + newAccuracy * newAccuracy);
            // apply 'k'
            latitude += k * (newLatitude - latitude);
            longitude += k * (newLongitude - longitude);
            // new Covariance matrix is (IdentityMatrix - k) * Covarariance
            variance = (1 - k) * variance;

            // Export new points
            exportNewPoint(newSpeed, longitude, latitude, timestamp);
        }
    }

    private void exportNewPoint(float speed, double longitude, double latitude, long timestamp) {
        GPSSingleData newGPSdata = new GPSSingleData(speed, longitude, latitude, timestamp);
        Exporter exporter = new Exporter();
        exporter.writeData(newGPSdata.toString());
    }
}