package com.comp2042.model.logic;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LevelUpTest {

    @BeforeAll
    static void startJavaFx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    private LevelUp levelUp;

    @BeforeEach
    void setUp() {
        levelUp = new LevelUp();
    }

    @Test
    void testLevelUp_AdvancementAndCarryOver() {
        // Arrange: Start at Level 1
        assertEquals(1, levelUp.getLevel(), "Game should start at Level 1.");

        // Simulate clearing 9 lines (not enough to level up)
        levelUp.checkAndAdvance(9);
        assertEquals(1, levelUp.getLevel(), "Level should remain at 1 after 9 lines cleared.");

        // Simulate clearing 2 more lines (total 11 lines cleared)
        levelUp.checkAndAdvance(2);

        // Assert: Level should advance, and internal counter should carry over 1 line (9+2 = 11. 11-10 = 1).
        assertEquals(2, levelUp.getLevel(), "Level should advance to 2 after 11 lines cleared.");

        // Simulate clearing 10 lines (should level up immediately, with 1 carried over)
        levelUp.checkAndAdvance(10);

        // Assert: Level should advance again (1 + 10 = 11. 11-10 = 1. New level is 3).
        assertEquals(3, levelUp.getLevel(), "Level should advance to 3 after 10 more lines cleared.");

        // Simulate advancing to max level (Level 5)
        levelUp.checkAndAdvance(50);
        assertEquals(5, levelUp.getLevel(), "Level should advance to max level (5).");

        // Test clearing more lines at max level (more than 50 lines cleared)
        levelUp.checkAndAdvance(1);
        assertEquals(5, levelUp.getLevel(), "Level should remain at 5 when more lines are cleared at max level.");

        // Test reset
        levelUp.reset();
        assertEquals(1, levelUp.getLevel(), "Level should reset to 1 on reset.");
    }
}