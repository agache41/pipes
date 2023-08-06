package com.orm.pipes.csv.csvFile.impl;

import com.orm.functional.ThrowingConsumer;
import com.orm.pipes.base.othogonal.OrthogonalHandler;
import com.orm.pipes.base.othogonal.enums.NamingMethod;
import com.orm.pipes.base.othogonal.enums.PositionMethod;
import com.orm.pipes.csv.csvField.CSVAccessor;
import com.orm.pipes.csv.csvFile.CSVFile;
import com.orm.reflection.accessor.Accessor;

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
