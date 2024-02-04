package io.github.agache41.ormpipes.pipes.csv.csvFile.impl;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalHandler;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.csv.csvField.CSVAccessor;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.annotator.accessor.Accessor;

public class CSVFileHandler extends OrthogonalHandler<CSVFile, CSVAccessor, CSVEntry, String, ThrowingConsumer<Object>, Object, String> {
    public CSVFileHandler(Class<?> onClass, CSVFile cfg) {
        super(onClass, cfg, CSVAccessor.class);
    }

    @Override
    protected CSVEntry newEntry(Accessor<?> accessor, NamingMethod namingMethod, PositionMethod positionMethod) {
        return new CSVEntry(accessor, namingMethod, positionMethod);
    }

    @Override
    protected CSVEntry newEntry(String name, int position) {
        return new CSVEntry(name, position);
    }
}
