package com.orm.pipes.spreadSheet.sheet;

import com.orm.functional.ThrowingConsumer;
import com.orm.pipes.base.othogonal.OrthogonalHandler;
import com.orm.pipes.base.othogonal.enums.NamingMethod;
import com.orm.pipes.base.othogonal.enums.PositionMethod;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.reflection.accessor.Accessor;
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
