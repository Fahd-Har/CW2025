package com.comp2042.model.brickShapeGenerator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A concrete implementation of {@code BrickGenerator} that provides a stream of randomly
 * selected Tetris bricks using a queue mechanism.
 *
 * <p>It ensures that there is always a brick available to play and a next brick available
 * for preview.</p>
 *
 * <p>Design Patterns Implemented:</p>
 * <ul>
 * <li>**Concrete Creator (Factory Method)**: Implements the generation logic defined in the {@code BrickGenerator} interface.</li>
 * <li>**Queue/Bag Randomizer**: Uses a {@code Deque} to store upcoming pieces, helping to smooth out the randomness of brick generation.</li>
 * </ul>
 */
public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Constructs the RandomBrickGenerator, initializing the list of all seven standard
     * Tetris pieces (I, J, L, O, S, T, Z) and pre-filling the
     * queue with the first two random bricks.
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    /**
     * Retrieves and removes the next brick from the queue.
     * If the queue size drops to one or less, a new random brick is generated and added
     * to ensure a continuous supply and a next-brick preview.
     *
     * @return The next {@code Brick} object.
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }

    /**
     * Peeks at the next brick in the queue without removing it, used for displaying the
     * "Next Brick Preview" in the GUI.
     *
     * @return The next {@code Brick} object to be retrieved by a subsequent call to {@code getBrick()}.
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}