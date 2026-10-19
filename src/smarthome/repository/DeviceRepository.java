package smarthome.repository;

import smarthome.device.Device;

import java.util.List;
import java.util.Optional;

/** Abstraction of the device storage. Services depend on it, not on a concrete implementation. */
public interface DeviceRepository {
    int nextId();
    void add(Device device);
    Optional<Device> findById(int id);
    List<Device> findAll();
    boolean remove(int id);
}
