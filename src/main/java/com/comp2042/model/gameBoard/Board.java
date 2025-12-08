package com.comp2042.model.gameBoard;

/**
 * The main composite interface for the Tetris game board model.
 *
 * <p>This interface aggregates three smaller, focused interfaces: {@code BrickMovement},
 * {@code Gameplay}, and {@code GameStats}.</p>
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**Composite Interface**: Acts as a convenient single entry point for all model operations, combining the capabilities of its constituent interfaces.</li>
 * <li>**Interface Segregation Principle (ISP)**: Maintains backward compatibility while enforcing ISP by building upon the smaller, segregated interfaces.</li>
 * </ul>
 */
public interface Board extends BrickMovement, Gameplay, GameStats {

}