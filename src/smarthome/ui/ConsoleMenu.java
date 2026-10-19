package smarthome.ui;

import smarthome.device.*;
import smarthome.scene.Scene;
import smarthome.service.DeviceService;
import smarthome.service.SceneService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** All terminal input/output. Contains no device control logic (SRP). */
public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final DeviceService devices;
    private final SceneService scenes;
    private final List<MenuItem> items = new ArrayList<>();
    private boolean running = true;

    public ConsoleMenu(DeviceService devices, SceneService scenes) {
        this.devices = devices;
        this.scenes = scenes;

        items.add(new MenuItem("Show all devices", this::showDevices));
        items.add(new MenuItem("Add a device", this::addDevice));
        items.add(new MenuItem("Remove a device", this::removeDevice));
        items.add(new MenuItem("Switch a device on", this::switchOn));
        items.add(new MenuItem("Switch a device off", this::switchOff));
        items.add(new MenuItem("Set lamp brightness", this::setBrightness));
        items.add(new MenuItem("Set thermostat temperature", this::setTemperature));
        items.add(new MenuItem("Lock a door", this::lock));
        items.add(new MenuItem("Unlock a door", this::unlock));
        items.add(new MenuItem("Run a scene", this::runScene));
        items.add(new MenuItem("Exit", () -> running = false));
    }

    public void run() {
        System.out.println("=== Smart Home ===");
        while (running) {
            printMenu();
            int choice = readInt("Your choice: ");
            if (choice < 1 || choice > items.size()) {
                System.out.println("  No such menu item.");
                continue;
            }
            try {
                items.get(choice - 1).action().run();
            } catch (IllegalArgumentException e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println();
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).title());
        }
    }

    // ---------- menu actions ----------

    private void showDevices() {
        List<Device> all = devices.getAll();
        if (all.isEmpty()) {
            System.out.println("  No devices yet.");
            return;
        }
        for (Device d : all) {
            System.out.println("  [" + d.getId() + "] " + d.getName() + " - " + d.getStatus());
        }
    }

    private void addDevice() {
        System.out.println("  1. Lamp  2. Thermostat  3. Smart lock");
        int type = readInt("  Device type: ");
        String name = readLine("  Name: ");
        switch (type) {
            case 1 -> devices.addLamp(name);
            case 2 -> devices.addThermostat(name);
            case 3 -> devices.addLock(name);
            default -> System.out.println("  Unknown device type.");
        }
    }

    private void removeDevice() {
        devices.remove(chooseId(devices.getAll(), "Remove"));
    }

    private void switchOn() {
        devices.switchOn(chooseId(asDevices(devices.findByCapability(Switchable.class)), "Switch on"));
    }

    private void switchOff() {
        devices.switchOff(chooseId(asDevices(devices.findByCapability(Switchable.class)), "Switch off"));
    }

    private void setBrightness() {
        int id = chooseId(asDevices(devices.findByCapability(Dimmable.class)), "Choose a lamp");
        devices.setBrightness(id, readInt("  Brightness (0-100): "));
    }

    private void setTemperature() {
        int id = chooseId(asDevices(devices.findByCapability(TemperatureControllable.class)), "Choose a thermostat");
        devices.setTemperature(id, readDouble("  Temperature (5-30): "));
    }

    private void lock() {
        devices.lock(chooseId(asDevices(devices.findByCapability(Lockable.class)), "Lock"));
    }

    private void unlock() {
        devices.unlock(chooseId(asDevices(devices.findByCapability(Lockable.class)), "Unlock"));
    }

    private void runScene() {
        List<Scene> list = scenes.getScenes();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + list.get(i).getName());
        }
        scenes.apply(readInt("  Scene number: ") - 1);
    }

    // ---------- input helpers ----------

    /** Shows the list of suitable devices and asks for an id. */
    private int chooseId(List<Device> candidates, String action) {
        if (candidates.isEmpty()) {
            throw new IllegalArgumentException("No suitable devices");
        }
        for (Device d : candidates) {
            System.out.println("  [" + d.getId() + "] " + d.getName() + " - " + d.getStatus());
        }
        return readInt("  " + action + " (enter id): ");
    }

    private List<Device> asDevices(List<?> capable) {
        List<Device> result = new ArrayList<>();
        for (Object o : capable) {
            if (o instanceof Device d) result.add(d);
        }
        return result;
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a number.");
            }
        }
    }
}
