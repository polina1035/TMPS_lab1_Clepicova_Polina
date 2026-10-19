package smarthome.device;

/** Common part of all devices: id and name. */
public abstract class BaseDevice implements Device {
    private final int id;
    private final String name;

    protected BaseDevice(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
