package com.comp2042.events;

import com.comp2042.view.data.ViewData;
import com.comp2042.model.logic.MovingDownData;

/**
 * Defines the contract for classes responsible for handling all user input and system-generated move events.
 *
 * <p>This interface is key to the **Command Pattern** implementation, decoupling the objects
 * that initiate a request ({@code KeyInputHandler}, {@code GameFlowManager}) from the
 * object that executes the request ({@code GameController}).</p>
 */
public interface InputEventListener {

    /**
     * Handles the event for moving the brick down one unit (soft drop or automated drop).
     *
     * @param event The {@code MoveEvent} details.
     * @return A {@code MovingDownData} object containing information about cleared rows and the updated view data.
     */
    MovingDownData onDownEvent(MoveEvent event);

    /**
     * Handles the event for moving the brick left one unit.
     *
     * @param event The {@code MoveEvent} details.
     * @return The updated {@code ViewData}.
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles the event for moving the brick right one unit.
     *
     * @param event The {@code MoveEvent} details.
     * @return The updated {@code ViewData}.
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles the event for rotating the brick.
     *
     * @param event The {@code MoveEvent} details.
     * @return The updated {@code ViewData}.
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Handles the event for holding/swapping the current brick with the piece in the hold queue.
     *
     * @param event The {@code MoveEvent} details.
     * @return The updated {@code ViewData}.
     */
    ViewData onHoldEvent(MoveEvent event);

    /**
     * Handles the event for slamming (instant drop) the current brick to the bottom.
     *
     * @param event The {@code MoveEvent} details.
     * @return A {@code MovingDownData} object resulting from the final landing of the brick.
     */
    MovingDownData onSlamEvent(MoveEvent event);

    /**
     * Handles the command to reset and start a new game.
     */
    void createNewGame();
}