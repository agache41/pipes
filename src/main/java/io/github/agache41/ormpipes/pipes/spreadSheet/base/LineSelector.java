package io.github.agache41.ormpipes.pipes.spreadSheet.base;

public @interface LineSelector {
    Line useOn() default Line.ALL_LINES;
}
