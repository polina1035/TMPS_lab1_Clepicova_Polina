package smarthome.device;

/** Lamp: can be switched on/off and has adjustable brightness. */
public class Lamp extends BaseDevice implements Switchable, Dimmable {
    private boolean on = false;
    private int brightness = 100;

    public Lamp(int id, String name) {
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
    public int getBrightness() {
        return brightness;
    }

    @Override
    public void setBrightness(int level) {
        if (level < 0 || level > 100) {
            throw new IllegalArgumentException("Brightness must be between 0 and 100");
        }
        brightness = level;
        on = level > 0;
    }

    @Override
    public String getStatus() {
        return on ? "on, brightness " + brightness + "%" : "off";
    }
}
