package smarthome;

import smarthome.logging.ConsoleEventLogger;
import smarthome.logging.EventLogger;
import smarthome.repository.DeviceRepository;
import smarthome.repository.InMemoryDeviceRepository;
import smarthome.scene.AwayScene;
import smarthome.scene.MorningScene;
import smarthome.scene.NightScene;
import smarthome.service.DeviceService;
import smarthome.service.SceneService;
import smarthome.ui.ConsoleMenu;

/** Entry point: all dependencies are wired together here (composition root). */
public class Main {
    public static void main(String[] args) {
        DeviceRepository repository = new InMemoryDeviceRepository();
        EventLogger logger = new ConsoleEventLogger();

        DeviceService deviceService = new DeviceService(repository, logger);
        SceneService sceneService = new SceneService(deviceService, logger);

        sceneService.register(new MorningScene());
        sceneService.register(new NightScene());
        sceneService.register(new AwayScene());

        // demo data
        deviceService.addLamp("Living room lamp");
        deviceService.addLamp("Bedroom lamp");
        deviceService.addThermostat("Thermostat");
        deviceService.addLock("Front door");

        new ConsoleMenu(deviceService, sceneService).run();
    }
}
