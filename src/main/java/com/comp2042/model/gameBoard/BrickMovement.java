package com.comp2042.model.gameBoard;

public interface BrickMovement {
    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateBrickLeft();

    void holdBrick();
}
