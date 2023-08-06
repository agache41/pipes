package com.orm.pipes.spreadSheet.base;

public @interface LineSelector {
    Line useOn() default Line.ALL_LINES;
}
