package io.github.agache41.ormpipes.pipes.spreadSheet.font;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.Styler;
import org.apache.poi.ss.usermodel.*;

public class FontStyler extends Styler<SpreadSheet.ContentFont> implements AnnotablePipe<SpreadSheet.ContentFont, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> {
    @Override
    public void configure(SpreadSheet.ContentFont cfg) {
        super.configure(cfg);
        this.configureStyle = (style, workbook) -> {
            Font font = workbook.createFont();
            font.setBold(cfg.bold());
            if (FontCharset.DEFAULT != cfg.charSet())
                font.setCharSet(cfg.charSet()
                                   .getValue());
            if (IndexedColors.AUTOMATIC != cfg.color())
                font.setColor(cfg.color().index);
            if (cfg.fontHeight() != -1)
                font.setFontHeight(cfg.fontHeight());
            if (cfg.fontHeightInPoints() != -1)
                font.setFontHeightInPoints(cfg.fontHeightInPoints());
            if (!cfg.fontName()
                    .isEmpty())
                font.setFontName(cfg.fontName());
            font.setItalic(cfg.italic());
            font.setStrikeout(cfg.strikeout());
            if (cfg.typeOffset() != -1)
                font.setTypeOffset(cfg.typeOffset());
            if (cfg.underline() != FontUnderline.NONE)
                font.setUnderline(cfg.underline()
                                     .getByteValue());
            style.setFont(font);
            return style;
        };
    }
}
