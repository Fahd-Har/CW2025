package com.comp2042.controller;

import javafx.application.Platform;
import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class GuiControllerTest {

    @BeforeAll
    static void initJavaFx() {
        Platform.startup(() -> {}); // Initialize JavaFX once
    }

    @Test
    void testTimelineRunsAction() throws Exception {
        AtomicInteger counter = new AtomicInteger();

        GameTimeline timeline = new GameTimeline(counter::incrementAndGet);

        Platform.runLater(timeline::start);
        Thread.sleep(600);

        Platform.runLater(timeline::stop);

        assertTrue(counter.get() > 0, "Timeline should increment counter");
    }

    @Test
    void testTimelineStopsActions() throws Exception {
        AtomicInteger counter = new AtomicInteger();

        GameTimeline timeline = new GameTimeline(counter::incrementAndGet);

        Platform.runLater(timeline::start);
        Thread.sleep(600);

        Platform.runLater(timeline::stop);
        int countAfterStop = counter.get();

        Thread.sleep(600);

        assertEquals(countAfterStop, counter.get(),
                "Counter should not change after stop()");
    }
}
