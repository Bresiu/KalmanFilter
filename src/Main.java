import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // TODO: refactor constructor, earlier play with variables;
        KalmanLatLong kalmanFilter = new KalmanLatLong(40);
        kalmanFilter.SetState(10, 9, 1, 0);
        GPSDataFactory dataFactory = new GPSDataFactory();
    }
}
