package com.comp2042.events;

import com.comp2042.view.data.ViewData;
import com.comp2042.model.logic.MovingDownData;

public interface InputEventListener {

    MovingDownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateEvent(MoveEvent event);

    ViewData onHoldEvent(MoveEvent event);

    void createNewGame();
}
