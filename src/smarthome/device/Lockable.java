package smarthome.device;

/** A device that can be locked and unlocked. */
public interface Lockable {
    boolean isLocked();
    void lock();
    void unlock();
}
