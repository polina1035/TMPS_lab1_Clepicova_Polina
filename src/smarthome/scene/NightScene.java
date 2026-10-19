package smarthome.scene;

import smarthome.device.*;
import smarthome.service.DeviceService;

/** Night: dim light, locks closed, cooler temperature. */
public class NightScene implements Scene {
    @Override
    public String getName() {
        return "Night";
    }

    @Override
    public void apply(DeviceService devices) {
        for (Device d : devices.getAll()) {
            if (d instanceof Dimmable) devices.setBrightness(d.getId(), 10);
            if (d instanceof Lockable) devices.lock(d.getId());
            if (d instanceof TemperatureControllable) devices.setTemperature(d.getId(), 18);
        }
    }
}
