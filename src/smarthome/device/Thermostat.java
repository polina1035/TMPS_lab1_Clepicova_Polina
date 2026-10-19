package smarthome.device;

/** Thermostat: can be switched on/off and holds a target temperature. */
public class Thermostat extends BaseDevice implements Switchable, TemperatureControllable {
    private static final double MIN_TEMP = 5;
    private static final double MAX_TEMP = 30;

    private boolean on = false;
    private double targetTemperature = 21;

    public Thermostat(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void turnOn() {
        on = true;
    }

    @Override
    public void turnOff() {
        on = false;
    }

    @Override
    public double getTargetTemperature() {
        return targetTemperature;
    }

    @Override
    public void setTargetTemperature(double temperature) {
        if (temperature < MIN_TEMP || temperature > MAX_TEMP) {
            throw new IllegalArgumentException(
                    "Temperature must be between " + MIN_TEMP + " and " + MAX_TEMP);
        }
        targetTemperature = temperature;
    }

    @Override
    public String getStatus() {
        return on ? "on, target " + targetTemperature + " C" : "off";
    }
}
