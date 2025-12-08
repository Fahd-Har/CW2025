package com.comp2042.events;

/**
 * A final class that encapsulates all information required for a single brick control action
 * or game timing event.
 *
 * <p>It combines the specific {@code EventType} with the {@code EventSource}.</p>
 *
 * <p>Design Pattern Used:</p>
 * <ul>
 * <li>**Command Pattern**: Acts as the concrete **Command** object, bundling all necessary
 * parameters to be executed later by the {@code InputEventListener} (the **Receiver**).</li>
 * </ul>
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * Constructs a new MoveEvent.
     *
     * @param eventType The type of action requested (e.g., DOWN, ROTATE, SLAM).
     * @param eventSource The originator of the event (USER or THREAD).
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the type of the event.
     *
     * @return The {@code EventType}.
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Gets the source of the event.
     *
     * @return The {@code EventSource}.
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}