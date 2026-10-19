package smarthome.device;

/** A device with adjustable brightness (0-100). */
public interface Dimmable {
    int getBrightness();
    void setBrightness(int level);
}
