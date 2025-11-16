package com.comp2042.gameLogic;

public final class MovingDownData {
    private final ClearFullRow clearFullRow;
    private final ViewData viewData;

    public MovingDownData(ClearFullRow clearFullRow, ViewData viewData) {
        this.clearFullRow = clearFullRow;
        this.viewData = viewData;
    }

    public ClearFullRow getClearRow() {
        return clearFullRow;
    }

    public ViewData getViewData() {
        return viewData;
    }
}
