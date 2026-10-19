package smarthome.scene;

import smarthome.device.*;
import smarthome.service.DeviceService;

/** Morning: bright light, warm temperature, locks open. */
public class MorningScene implements Scene {
    @Override
    public String getName() {
        return "Morning";
    }

    @Override
    public void apply(DeviceService devices) {
        for (Device d : devices.getAll()) {
            if (d instanceof Dimmable) devices.setBrightness(d.getId(), 70);
            if (d instanceof TemperatureControllable) {
                devices.setTemperature(d.getId(), 22);
                devices.switchOn(d.getId());
            }
            if (d instanceof Lockable) devices.unlock(d.getId());
        }
    }
}
