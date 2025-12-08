package com.comp2042.model.logic;

import com.comp2042.view.data.ViewData;

/**
 * A final data class used to bundle the complete results of a move-down event (either automated or player-initiated).
 */
public final class MovingDownData {
    private final ClearFullRow clearFullRow;
    private final ViewData viewData;

    /**
     * Constructs the MovingDownData container.
     *
     * @param clearFullRow The result of the row clearance operation (may be null if no rows were cleared, or a valid object if the piece landed).
     * @param viewData The updated view data containing the brick's new position, shadow, and previews.
     */
    public MovingDownData(ClearFullRow clearFullRow, ViewData viewData) {
        this.clearFullRow = clearFullRow;
        this.viewData = viewData;
    }

    /**
     * Gets the row clearance data.
     *
     * @return The {@code ClearFullRow} object, or null.
     */
    public ClearFullRow getClearRow() {
        return clearFullRow;
    }

    /**
     * Gets the updated view data.
     *
     * @return The {@code ViewData} object.
     */
    public ViewData getViewData() {
        return viewData;
    }
}