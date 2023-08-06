package com.orm.pipes.spreadSheet.customStyler;

import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.pipes.spreadSheet.base.Styler;
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
