package io.github.agache41.ormpipes.pipes.spreadSheet.customStyler;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.Styler;
import org.apache.poi.ss.usermodel.Cell;

public class CustomStyler extends Styler<SpreadSheet.CustomStyle> implements AnnotablePipe<SpreadSheet.CustomStyle, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> {
    @Override
    public void configure(SpreadSheet.CustomStyle cfg) {
        super.configure(cfg);
        try {
            this.configureStyle = cfg.styleProvider()
                                     .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
