package com.orm.pipes.base.othogonal;

import com.orm.pipes.base.othogonal.enums.Model;
import com.orm.pipes.base.othogonal.enums.NamingMethod;
import com.orm.pipes.base.othogonal.enums.PositionMethod;

public @interface OrthogonalFile {
    String[] header() default {};

    boolean useFirstLineAsHeader() default true;

    int skipFirstNLines() default 0;

    NamingMethod namingMethod() default NamingMethod.AccessorNames;

    PositionMethod positionMethod() default PositionMethod.CSVFields;

    Model model() default Model.VariablePositions;
}
