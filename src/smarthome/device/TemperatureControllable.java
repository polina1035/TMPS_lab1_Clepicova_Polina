package smarthome.device;

/** A device whose target temperature can be set. */
public interface TemperatureControllable {
    double getTargetTemperature();
    void setTargetTemperature(double temperature);
}
