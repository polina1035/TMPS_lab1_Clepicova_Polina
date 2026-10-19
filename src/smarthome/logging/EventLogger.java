package smarthome.logging;

/** Abstraction of the event log. */
public interface EventLogger {
    void log(String message);
}
