package smarthome.scene;

import smarthome.service.DeviceService;

/** A scene: a set of actions applied to devices. A new scene = a new class (OCP). */
public interface Scene {
    String getName();
    void apply(DeviceService devices);
}
