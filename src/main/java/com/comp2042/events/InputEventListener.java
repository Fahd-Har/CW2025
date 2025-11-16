package com.comp2042.events;

import com.comp2042.gameLogic.ViewData;
import com.comp2042.gameLogic.MovingDownData;

public interface InputEventListener {

    MovingDownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateEvent(MoveEvent event);

    void createNewGame();
}
