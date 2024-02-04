package io.github.agache41.ormpipes.pipes.base.othogonal;

import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;

public @interface OrthogonalFile {
    String[] header() default {};

    boolean useFirstLineAsHeader() default true;

    int skipFirstNLines() default 0;

    NamingMethod namingMethod() default NamingMethod.AccessorNames;

    PositionMethod positionMethod() default PositionMethod.CSVFields;

    Model model() default Model.VariablePositions;
}
