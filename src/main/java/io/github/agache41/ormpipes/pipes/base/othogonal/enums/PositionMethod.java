package io.github.agache41.ormpipes.pipes.base.othogonal.enums;

import java.util.function.BiFunction;

/**
 * <pre>
 * The enum Position method.
 * </pre>
 */
public enum PositionMethod implements BiFunction<Integer, Integer, Integer> {
    /**
     * <pre>
     * Csv fields position method.
     * </pre>
     */
    CSVFields((csvColumnPosition, fieldPosition) -> csvColumnPosition),
    /**
     * <pre>
     * Fields position method.
     * </pre>
     */
    Fields((csvColumnPosition, fieldPosition) -> fieldPosition);

    private final BiFunction<Integer, Integer, Integer> method;

    PositionMethod(BiFunction<Integer, Integer, Integer> method) {
        this.method = method;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Integer apply(Integer csvFieldPosition,
                         Integer fieldPosition) {
        return this.method.apply(csvFieldPosition, fieldPosition);
    }
}
