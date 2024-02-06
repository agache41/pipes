package io.github.agache41.ormpipes.pipes.spreadSheet.base;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * <pre>
 * The enum Line.
 * </pre>
 */
public enum Line {
    /**
     * <pre>
     * The Even lines.
     * </pre>
     */
    EVEN_LINES(ab -> {
        ab.set(!ab.get());
        return ab.get();
    }),
    /**
     * <pre>
     * Odd lines line.
     * </pre>
     */
    ODD_LINES(ab -> {
        ab.set(!ab.get());
        return !ab.get();
    }),
    /**
     * <pre>
     * All lines line.
     * </pre>
     */
    ALL_LINES(ab -> true);

    private final Function<AtomicBoolean, Boolean> toggle;

    Line(Function<AtomicBoolean, Boolean> toggle) {
        this.toggle = toggle;
    }

    /**
     * <pre>
     * Gets toggle.
     * </pre>
     *
     * @return the toggle
     */
    public Function<AtomicBoolean, Boolean> getToggle() {
        return this.toggle;
    }
}
