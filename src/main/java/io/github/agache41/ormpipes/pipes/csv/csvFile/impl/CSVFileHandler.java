package io.github.agache41.ormpipes.pipes.csv.csvFile.impl;

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalHandler;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.csv.csvField.CSVAccessor;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;

/**
 * <pre>
 * The type Csv file handler.
 * </pre>
 */
public class CSVFileHandler extends OrthogonalHandler<CSVFile, CSVAccessor, CSVEntry, String, ThrowingConsumer<Object>, Object, String> {
    /**
     * <pre>
     * Instantiates a new Csv file handler.
     * </pre>
     *
     * @param onClass the on class
     * @param cfg     the cfg
     */
    public CSVFileHandler(Class<?> onClass,
                          CSVFile cfg) {
        super(onClass, cfg, CSVAccessor.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CSVEntry newEntry(Accessor<?> accessor,
                                NamingMethod namingMethod,
                                PositionMethod positionMethod) {
        return new CSVEntry(accessor, namingMethod, positionMethod);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CSVEntry newEntry(String name,
                                int position) {
        return new CSVEntry(name, position);
    }
}
