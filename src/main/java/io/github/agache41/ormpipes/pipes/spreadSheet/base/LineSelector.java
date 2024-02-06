package io.github.agache41.ormpipes.pipes.spreadSheet.base;

/**
 * <pre>
 * The interface Line selector.
 * </pre>
 */
public @interface LineSelector {
    /**
     * <pre>
     * Use on line.
     * </pre>
     *
     * @return the line
     */
    Line useOn() default Line.ALL_LINES;
}
