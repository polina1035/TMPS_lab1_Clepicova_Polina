package smarthome.device;

/** A device that can be turned on and off. */
public interface Switchable {
    boolean isOn();
    void turnOn();
    void turnOff();
}
