package smarthome.service;

import smarthome.logging.EventLogger;
import smarthome.scene.Scene;

import java.util.ArrayList;
import java.util.List;

/** Stores scenes and runs them. Does not know which concrete scenes exist. */
public class SceneService {
    private final List<Scene> scenes = new ArrayList<>();
    private final DeviceService devices;
    private final EventLogger logger;

    public SceneService(DeviceService devices, EventLogger logger) {
        this.devices = devices;
        this.logger = logger;
    }

    public void register(Scene scene) {
        scenes.add(scene);
    }

    public List<Scene> getScenes() {
        return List.copyOf(scenes);
    }

    public void apply(int index) {
        if (index < 0 || index >= scenes.size()) {
            throw new IllegalArgumentException("No scene with this number");
        }
        Scene scene = scenes.get(index);
        logger.log("Running scene \"" + scene.getName() + "\"");
        scene.apply(devices);
    }
}
