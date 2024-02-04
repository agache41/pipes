package io.github.agache41.ormpipes.pipes.spreadSheet.sheet;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalHandler;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.annotator.accessor.Accessor;
import org.apache.poi.ss.usermodel.Cell;

public class SheetHandler extends OrthogonalHandler<SpreadSheet.sheet, SpreadSheet.Header, SheetEntry, Cell, ThrowingConsumer<Object>, Object, ThrowingConsumer<Cell>> {
    public SheetHandler(Class<?> onClass, SpreadSheet.sheet cfg) {
        super(onClass, cfg, SpreadSheet.Header.class);
    }

    @Override
    protected SheetEntry newEntry(Accessor<?> accessor, NamingMethod namingMethod, PositionMethod positionMethod) {
        return new SheetEntry(accessor, namingMethod, positionMethod);
    }

    @Override
    protected SheetEntry newEntry(String name, int position) {
        return new SheetEntry(name, position);
    }
}
