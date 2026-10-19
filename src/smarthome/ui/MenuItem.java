package smarthome.ui;

/** A menu item: a title plus an action. */
public record MenuItem(String title, Runnable action) {
}
