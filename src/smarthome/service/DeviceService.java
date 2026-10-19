package smarthome.service;

import smarthome.device.*;
import smarthome.logging.EventLogger;
import smarthome.repository.DeviceRepository;

import java.util.List;

/**
 * Business logic for managing devices.
 * Depends on the DeviceRepository and EventLogger interfaces (DIP).
 * Reports errors via exceptions; printing is the UI's job (SRP).
 */
public class DeviceService {
    private final DeviceRepository repository;
    private final EventLogger logger;

    public DeviceService(DeviceRepository repository, EventLogger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    // ---------- add / remove / view ----------

    public Device addLamp(String name) {
        return register(new Lamp(repository.nextId(), name));
    }

    public Device addThermostat(String name) {
        return register(new Thermostat(repository.nextId(), name));
    }

    public Device addLock(String name) {
        return register(new SmartLock(repository.nextId(), name));
    }

    private Device register(Device device) {
        repository.add(device);
        logger.log("Device added: " + describe(device));
        return device;
    }

    public void remove(int id) {
        Device device = get(id);
        repository.remove(id);
        logger.log("Device removed: " + describe(device));
    }

    public List<Device> getAll() {
        return repository.findAll();
    }

    /** All devices that have the given capability (e.g. Switchable.class). */
    public <T> List<T> findByCapability(Class<T> capability) {
        return repository.findAll().stream()
                .filter(capability::isInstance)
                .map(capability::cast)
                .toList();
    }

    // ---------- control ----------

    public void switchOn(int id) {
        Device device = get(id);
        require(device, Switchable.class, "cannot be switched on/off").turnOn();
        logger.log(describe(device) + " switched on");
    }

    public void switchOff(int id) {
        Device device = get(id);
        require(device, Switchable.class, "cannot be switched on/off").turnOff();
        logger.log(describe(device) + " switched off");
    }

    public void setBrightness(int id, int level) {
        Device device = get(id);
        require(device, Dimmable.class, "does not support brightness").setBrightness(level);
        logger.log(describe(device) + ": brightness " + level + "%");
    }

    public void setTemperature(int id, double temperature) {
        Device device = get(id);
        require(device, TemperatureControllable.class, "does not support temperature")
                .setTargetTemperature(temperature);
        logger.log(describe(device) + ": target temperature " + temperature + " C");
    }

    public void lock(int id) {
        Device device = get(id);
        require(device, Lockable.class, "is not a lock").lock();
        logger.log(describe(device) + " locked");
    }

    public void unlock(int id) {
        Device device = get(id);
        require(device, Lockable.class, "is not a lock").unlock();
        logger.log(describe(device) + " unlocked");
    }

    // ---------- helpers ----------

    private Device get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device with id " + id + " not found"));
    }

    private <T> T require(Device device, Class<T> capability, String errorText) {
        if (!capability.isInstance(device)) {
            throw new IllegalArgumentException("Device \"" + device.getName() + "\" " + errorText);
        }
        return capability.cast(device);
    }

    private String describe(Device device) {
        return "\"" + device.getName() + "\" (id " + device.getId() + ")";
    }
}
