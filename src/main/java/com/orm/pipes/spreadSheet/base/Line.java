package com.orm.pipes.spreadSheet.base;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public enum Line {
    EVEN_LINES(ab -> {
        ab.set(!ab.get());
        return ab.get();
    }),
    ODD_LINES(ab -> {
        ab.set(!ab.get());
        return !ab.get();
    }),
    ALL_LINES(ab -> true);

    private final Function<AtomicBoolean, Boolean> toggle;

    Line(Function<AtomicBoolean, Boolean> toggle) {
        this.toggle = toggle;
    }

    public Function<AtomicBoolean, Boolean> getToggle() {
        return this.toggle;
    }
}
