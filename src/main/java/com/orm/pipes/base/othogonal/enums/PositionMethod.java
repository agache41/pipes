package com.orm.pipes.base.othogonal.enums;

import java.util.function.BiFunction;

public enum PositionMethod implements BiFunction<Integer, Integer, Integer> {
    CSVFields((csvColumnPosition, fieldPosition) -> csvColumnPosition),
    Fields((csvColumnPosition, fieldPosition) -> fieldPosition);

    private final BiFunction<Integer, Integer, Integer> method;

    PositionMethod(BiFunction<Integer, Integer, Integer> method) {
        this.method = method;
    }


    @Override
    public Integer apply(Integer csvFieldPosition, Integer fieldPosition) {
        return this.method.apply(csvFieldPosition, fieldPosition);
    }
}
