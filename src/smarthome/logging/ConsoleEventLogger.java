package smarthome.logging;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Writes events to the console with a timestamp. */
public class ConsoleEventLogger implements EventLogger {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void log(String message) {
        System.out.println("  [LOG " + LocalTime.now().format(FORMAT) + "] " + message);
    }
}
