package com.orm.pipes.spreadSheet.cellFormat;

import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.pipes.spreadSheet.base.Styler;
import org.apache.poi.ss.usermodel.Cell;

public class FormatStyler extends Styler<SpreadSheet.ContentFormat> implements AnnotablePipe<SpreadSheet.ContentFormat, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> {
    @Override
    public void configure(SpreadSheet.ContentFormat cfg) {
        super.configure(cfg);
        this.configureStyle = (style, workbook) -> {
            if (cfg.dataFormat() != -1) style.setDataFormat(cfg.dataFormat());
            else if (!cfg.value()
                         .isEmpty()) style.setDataFormat(workbook.getCreationHelper()
                                                                 .createDataFormat()
                                                                 .getFormat(cfg.value()));
            return style;
        };
    }
}
