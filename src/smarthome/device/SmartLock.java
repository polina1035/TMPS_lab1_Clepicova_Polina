package smarthome.device;

/** Smart lock: can only be locked and unlocked (it is NOT Switchable!). */
public class SmartLock extends BaseDevice implements Lockable {
    private boolean locked = true;

    public SmartLock(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void lock() {
        locked = true;
    }

    @Override
    public void unlock() {
        locked = false;
    }

    @Override
    public String getStatus() {
        return locked ? "locked" : "unlocked";
    }
}
