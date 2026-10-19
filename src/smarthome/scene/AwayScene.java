package smarthome.scene;

import smarthome.device.*;
import smarthome.service.DeviceService;

/** Leaving home: switch everything off and lock the doors. */
public class AwayScene implements Scene {
    @Override
    public String getName() {
        return "Away from home";
    }

    @Override
    public void apply(DeviceService devices) {
        for (Device d : devices.getAll()) {
            if (d instanceof Switchable) devices.switchOff(d.getId());
            if (d instanceof Lockable) devices.lock(d.getId());
        }
    }
}
