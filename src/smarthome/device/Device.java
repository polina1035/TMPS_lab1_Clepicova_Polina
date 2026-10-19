package smarthome.device;

/** Base contract for any smart home device. */
public interface Device {
    int getId();
    String getName();
    String getStatus();
}
