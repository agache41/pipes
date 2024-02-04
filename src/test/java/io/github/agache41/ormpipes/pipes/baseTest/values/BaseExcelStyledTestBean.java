package io.github.agache41.ormpipes.pipes.baseTest.values;

import io.github.agache41.annotator.annotations.Position;
import io.github.agache41.ormpipes.pipes.accessor.Accessor;
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.TypeDouble;
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.TypeFloat;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.TypeLong;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.spreadSheet.cellstyle.xslx.CustomStyle;
import io.github.agache41.ormpipes.pipes.temporal.typeDate.TypeDate;
import io.github.agache41.ormpipes.pipes.temporal.typeLocalDate.TypeLocalDate;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FontUnderline;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static io.github.agache41.ormpipes.pipes.spreadSheet.base.Line.EVEN_LINES;
import static io.github.agache41.ormpipes.pipes.spreadSheet.base.Line.ODD_LINES;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.*;
import static org.apache.poi.ss.usermodel.IndexedColors.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseExcelStyledTestBean implements TestBean {
    @Position(0)
    @SpreadSheet.Header(name = "string0", position = 0, fillForegroundColor = BLACK, fillPattern = FillPatternType.BRICKS, alignment = CENTER, fontColor = YELLOW)
    @SpreadSheet.ContentStyle(useOn = ODD_LINES, fillForegroundColor = BRIGHT_GREEN, fillPattern = FillPatternType.BRICKS, alignment = RIGHT)
    @SpreadSheet.ContentStyle(useOn = EVEN_LINES, fillForegroundColor = DARK_YELLOW, fillPattern = FillPatternType.BRICKS, alignment = LEFT)
    @SpreadSheet.ContentFont(useOn = ODD_LINES, fontName = "Arial", fontHeight = 7, color = YELLOW)
    @SpreadSheet.ContentFont(useOn = EVEN_LINES, fontName = "Arial", fontHeight = 7, color = BLUE)
    @TypeString.cellValue
    @TypeString.nullable
    @Accessor
    private String string0;
    @Position(1)
    @SpreadSheet.Header(name = "integer1", position = 1, required = true, autoSizeColumn = true, fillForegroundColor = BLACK, fillPattern = FillPatternType.BRICKS, alignment = CENTER, fontColor = YELLOW)
    @SpreadSheet.ContentStyle(useOn = ODD_LINES, fillForegroundColor = YELLOW, fillPattern = FillPatternType.DIAMONDS, alignment = LEFT)
    @SpreadSheet.ContentFont(useOn = ODD_LINES, fontName = "System", fontHeight = 10, color = BLUE)
    @SpreadSheet.ContentStyle(useOn = EVEN_LINES, fillForegroundColor = BLUE, fillPattern = FillPatternType.DIAMONDS, alignment = LEFT)
    @SpreadSheet.ContentFont(useOn = EVEN_LINES, fontName = "System", fontHeight = 10, color = YELLOW)
    @TypeInteger.cellValue
    @Accessor
    private Integer integer1;

    @Position(2)
    @SpreadSheet.Header(name = "long2", position = 2, autoSizeColumn = true, fillForegroundColor = BLACK, fillPattern = FillPatternType.BRICKS, alignment = CENTER, fontColor = YELLOW)
    @SpreadSheet.ContentStyle(fillForegroundColor = GREEN, fillPattern = FillPatternType.ALT_BARS, alignment = RIGHT)
    @SpreadSheet.ContentFont(bold = true, color = RED)
    @TypeLong.cellValue
    @Accessor
    private Long long2;

    @Position(3)
    @SpreadSheet.Header(name = "double3", position = 3, required = true, autoSizeColumn = true, fillForegroundColor = BLACK, fillPattern = FillPatternType.BRICKS, alignment = CENTER, fontColor = YELLOW)
    @SpreadSheet.ContentStyle(fillForegroundColor = MAROON, fillPattern = FillPatternType.LEAST_DOTS, alignment = LEFT)
    @SpreadSheet.ContentFont(italic = true, color = PINK)
    @TypeDouble.cellValue
    @Accessor
    private Double double3;

    @Position(4)
    @SpreadSheet.Header(name = "float4", position = 4, autoSizeColumn = true, styleProvider = CustomStyle.class)
    @SpreadSheet.CustomStyle(styleProvider = CustomStyle.class)
    @TypeFloat.cellValue
    @Accessor
    private Float float4;

    @Position(5)
    @SpreadSheet.Header(name = "localdate5", position = 5, width = 3000, fillForegroundColor = BLACK, fillPattern = FillPatternType.BRICKS, alignment = CENTER, fontColor = YELLOW)
    @SpreadSheet.ContentStyle(fillForegroundColor = DARK_RED, fillPattern = FillPatternType.SQUARES, alignment = LEFT)
    @SpreadSheet.ContentFont(bold = true, italic = true, strikeout = true, underline = FontUnderline.DOUBLE_ACCOUNTING, color = DARK_BLUE)
    @SpreadSheet.ContentFormat(value = "m.d.yy h:mm")
    @TypeLocalDate.cellValue
    @Accessor
    private LocalDate localdate5;

    @Position(6)
    @SpreadSheet.Header(name = "date6", position = 6, width = 6000, fillForegroundColor = VIOLET, fillPattern = FillPatternType.BRICKS, alignment = LEFT, fontColor = YELLOW)
    @SpreadSheet.ContentStyle(fillForegroundColor = DARK_RED, fillPattern = FillPatternType.SQUARES, alignment = LEFT)
    @SpreadSheet.ContentFont(bold = true, italic = true, underline = FontUnderline.DOUBLE_ACCOUNTING, color = DARK_BLUE)
    @SpreadSheet.ContentFormat(value = "m.d.yy h:mm")
    @TypeDate.cellValue
    @Accessor
    private Date date6;

    @Position(7)
    @SpreadSheet.Header(name = "list7", position = 7, fillForegroundColor = VIOLET, fillPattern = FillPatternType.BRICKS, alignment = LEFT, fontColor = YELLOW)
    @SpreadSheet.ContentStyle(fillForegroundColor = GREEN, fillPattern = FillPatternType.DIAMONDS, alignment = RIGHT)
    @SpreadSheet.ContentFont(bold = true, italic = true, underline = FontUnderline.DOUBLE_ACCOUNTING, color = DARK_BLUE)
    @TypeString.cellValue
    @TypeString.List(separator = ",")
    @Accessor
    private List<String> list7;
}
