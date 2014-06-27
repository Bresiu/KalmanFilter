import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

public class Exporter {
    public void writeData(String line) {
        line += "\n";
        try {
            Files.append(line, new File(Constants.GPS_FILE_EXPORT), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
