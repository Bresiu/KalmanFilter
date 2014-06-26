import com.google.common.eventbus.EventBus;

import java.util.List;

public class GPSDataFactory {

    private List<String> gpsDataLines;
    private EventBus bus;

    public GPSDataFactory() {
        Importer importer = new Importer();
        gpsDataLines = importer.readData();
        registerBus();
        startFactory();
    }

    private void registerBus() {
        bus = BusProvider.getInstance();
    }

    private void startFactory() {
        Thread thread = new Thread() {
            public void run() {
                for (String string : gpsDataLines) {
                    GPSSingleData gpsSingleData = proccessLine(string);
                    bus.post(gpsSingleData);

                    /*
                    // Simulate GPS intervals
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        };
        thread.start();
    }

    private GPSSingleData proccessLine(String gpsLine) {
        String[] gpsParts = gpsLine.split(" ");
        return new GPSSingleData(Float.valueOf(gpsParts[1]), Double.valueOf(gpsParts[2]),
                Double.valueOf(gpsParts[3]), Long.valueOf(gpsParts[4]));
    }
}
