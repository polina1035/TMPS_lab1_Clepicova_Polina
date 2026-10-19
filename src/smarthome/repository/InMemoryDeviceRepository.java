package smarthome.repository;

import smarthome.device.Device;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Keeps devices in memory. Can be replaced by a file or DB storage without changing other code. */
public class InMemoryDeviceRepository implements DeviceRepository {
    private final Map<Integer, Device> devices = new LinkedHashMap<>();
    private int counter = 0;

    @Override
    public int nextId() {
        return ++counter;
    }

    @Override
    public void add(Device device) {
        devices.put(device.getId(), device);
    }

    @Override
    public Optional<Device> findById(int id) {
        return Optional.ofNullable(devices.get(id));
    }

    @Override
    public List<Device> findAll() {
        return new ArrayList<>(devices.values());
    }

    @Override
    public boolean remove(int id) {
        return devices.remove(id) != null;
    }
}
