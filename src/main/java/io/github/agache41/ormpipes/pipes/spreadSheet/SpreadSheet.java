package io.github.agache41.ormpipes.pipes.spreadSheet;

import io.github.agache41.ormpipes.config.Annotations;
import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.Orthogonal;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalFile;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.base.parser.Parser;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.EmptyStyleProvider;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.IdentityCell;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.Line;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.LineSelector;
import io.github.agache41.ormpipes.pipes.spreadSheet.cellFormat.FormatStyler;
import io.github.agache41.ormpipes.pipes.spreadSheet.cellStyle.StyleStyler;
import io.github.agache41.ormpipes.pipes.spreadSheet.customStyler.CustomStyler;
import io.github.agache41.ormpipes.pipes.spreadSheet.file.SpreadSheetFileReader;
import io.github.agache41.ormpipes.pipes.spreadSheet.file.SpreadSheetFileWriter;
import io.github.agache41.ormpipes.pipes.spreadSheet.file.Type;
import io.github.agache41.ormpipes.pipes.spreadSheet.font.FontStyler;
import io.github.agache41.ormpipes.pipes.spreadSheet.selector.ReadSheetSelector;
import io.github.agache41.ormpipes.pipes.spreadSheet.selector.WriteSheetSelector;
import io.github.agache41.ormpipes.pipes.spreadSheet.sheet.SheetReader;
import io.github.agache41.ormpipes.pipes.spreadSheet.sheet.SheetWriter;
import io.github.agache41.ormpipes.pipes.spreadSheet.xls.SpreadSheetReader;
import io.github.agache41.ormpipes.pipes.spreadSheet.xls.XLSWriter;
import io.github.agache41.ormpipes.pipes.spreadSheet.xlsx.XLSXWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static io.github.agache41.annotator.accessor.Accessor.NO_POSITION;
import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;


@Repeatable(SpreadSheets.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Extends(DualPipe.class)
public @interface SpreadSheet {

    @Repeatable(SpreadSheets.xlsxes.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface xlsx {
        String extension = ".xlsx";

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<?, InputStream, Workbook>> read() default SpreadSheetReader.class;

        Class<? extends AnnotablePipe<SpreadSheet.xlsx, ThrowingConsumer<XSSFWorkbook>, ThrowingConsumer<OutputStream>>> write() default XLSXWriter.class;

        String view() default DEFAULT;

        class StringStreamParser<T> extends Parser<T, String, Stream<T>> {
            public StringStreamParser(Class<T> clazz) {
                super(clazz, String.class);
            }

            public StringStreamParser(Class<T> clazz, String view) {
                super(clazz, String.class, view);
            }

            public static <S> SpreadSheet.xlsx.StringStreamParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.xlsx.StringStreamParser<>(clazz);
            }

            public static <S> SpreadSheet.xlsx.StringStreamParser<S> ofClass(Class<S> clazz, String view) {
                return new SpreadSheet.xlsx.StringStreamParser<>(clazz, view);
            }
        }

        class StringObjectParser<T> extends Parser<T, String, T> {
            public StringObjectParser(Class<T> clazz) {
                super(clazz, String.class);
            }

            public StringObjectParser(Class<T> clazz, String view) {
                super(clazz, String.class, view);
            }

            public static <S> SpreadSheet.xlsx.StringObjectParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.xlsx.StringObjectParser<>(clazz);
            }

            public static <S> SpreadSheet.xlsx.StringObjectParser<S> ofClass(Class<S> clazz, String view) {
                return new SpreadSheet.xlsx.StringObjectParser<>(clazz, view);
            }
        }

    }

    @Repeatable(SpreadSheets.xlses.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface xls {
        String extension = ".xls";

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<?, InputStream, Workbook>> read() default SpreadSheetReader.class;

        Class<? extends AnnotablePipe<SpreadSheet.xls, ThrowingConsumer<HSSFWorkbook>, ThrowingConsumer<OutputStream>>> write() default XLSWriter.class;

        String view() default DEFAULT;

        class StringStreamParser<T> extends Parser<T, String, Stream<T>> {
            public StringStreamParser(Class<T> clazz) {
                super(clazz, String.class);
            }

            public StringStreamParser(Class<T> clazz, String view) {
                super(clazz, String.class, view);
            }

            public static <S> SpreadSheet.xls.StringStreamParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.xls.StringStreamParser<>(clazz);
            }

            public static <S> SpreadSheet.xls.StringStreamParser<S> ofClass(Class<S> clazz, String view) {
                return new SpreadSheet.xls.StringStreamParser<>(clazz, view);
            }
        }

        class StringObjectParser<T> extends Parser<T, String, T> {
            public StringObjectParser(Class<T> clazz) {
                super(clazz, String.class);
            }

            public StringObjectParser(Class<T> clazz, String view) {
                super(clazz, String.class, view);
            }

            public static <S> SpreadSheet.xls.StringObjectParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.xls.StringObjectParser<>(clazz);
            }

            public static <S> SpreadSheet.xls.StringObjectParser<S> ofClass(Class<S> clazz, String view) {
                return new SpreadSheet.xls.StringObjectParser<>(clazz, view);
            }
        }

    }

    @Repeatable(SpreadSheets.files.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface file {
        /**
         * This designates the file given as parameter as template,
         * if set to true the file must exist and its contents will be overwritten.
         * If set to false a new file will be created, and the old file overwritten, if exits.
         */
        boolean template() default false;

        /**
         * Determines the type of file (xsl or xslx) to use or to autodetect based on the extension.
         */
        Type type() default Type.AUTO;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<SpreadSheet.file, File, Workbook>> read() default SpreadSheetFileReader.class;

        Class<? extends AnnotablePipe<SpreadSheet.file, ThrowingConsumer<Workbook>, ThrowingConsumer<File>>> write() default SpreadSheetFileWriter.class;

        String view() default DEFAULT;

        class FileStreamParser<T> extends Parser<T, File, Stream<T>> {
            public FileStreamParser(Class<T> clazz) {
                super(clazz, File.class);
            }

            public FileStreamParser(Class<T> clazz, String view) {
                super(clazz, File.class, view);
            }

            public static <S> SpreadSheet.file.FileStreamParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.file.FileStreamParser<>(clazz);
            }

            public static <S> SpreadSheet.file.FileStreamParser<S> ofClass(Class<S> clazz, String view) {
                return new SpreadSheet.file.FileStreamParser<>(clazz, view);
            }
        }

        class FileObjectParser<T> extends Parser<T, File, T> {
            public FileObjectParser(Class<T> clazz) {
                super(clazz, File.class);
            }

            public FileObjectParser(Class<T> clazz, String view) {
                super(clazz, File.class, view);
            }

            public static <S> SpreadSheet.file.FileObjectParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.file.FileObjectParser<>(clazz);
            }

            public static <S> SpreadSheet.file.FileObjectParser<S> ofClass(Class<S> clazz, String view) {
                return new SpreadSheet.file.FileObjectParser<>(clazz, view);
            }
        }
    }


    @Repeatable(SpreadSheets.selects.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface select {
        String sheetName() default "";

        int sheetIndex() default 0;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<select, Workbook, Sheet>> read() default ReadSheetSelector.class;

        Class<? extends AnnotablePipe<select, Workbook, Sheet>> write() default WriteSheetSelector.class;

        String view() default DEFAULT;
    }

    @Repeatable(SpreadSheets.sheets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, OrthogonalFile.class})
    @interface sheet {

        //Data File Settings
        String[] header() default {};

        boolean useFirstLineAsHeader() default true;

        int skipFirstNLines() default 0;

        NamingMethod namingMethod() default NamingMethod.AccessorNames;

        PositionMethod positionMethod() default PositionMethod.CSVFields;

        Model model() default Model.VariablePositions;

        boolean append() default false;

        /**
         * Offset on the X Scala (from left to right) for the active zone where data will be read or written.
         */
        int offsetX() default 0;

        /**
         * Offset on the Y Scala (from top to bottom) for the active zone where data will be read or written.
         */
        int offsetY() default 0;

        /**
         * Set the default column width for the sheet (if the columns do not define their own width) in characters
         * See {@link org.apache.poi.ss.usermodel.Sheet#setDefaultColumnWidth(int) Sheet.setDefaultColumnWidth()}
         */
        int defaultColumnWidth() default -1;

        /**
         * Set the default row height for the sheet (if the rows do not define their own height) in twips (1/20 of a point)
         * See {@link org.apache.poi.ss.usermodel.Sheet#setDefaultRowHeight(short) Sheet.setDefaultRowHeight()}
         */
        short defaultRowHeight() default -1;

        /**
         * Set the default row height for the sheet (if the rows do not define their own height) in points
         * See {@link org.apache.poi.ss.usermodel.Sheet#setDefaultRowHeightInPoints(float) Sheet.setDefaultRowHeightInPoints()}
         */
        int defaultRowHeightInPoints() default -1;

        /**
         * Disable checking for available Data on line.
         */
        boolean disableLineValidation() default false;

        /**
         * Applies auto-resizing on all columns.
         */
        boolean autoResizeAllColumns() default false;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<SpreadSheet.sheet, Sheet, Stream<?>>> read() default SheetReader.class;

        Class<? extends AnnotablePipe<SpreadSheet.sheet, Stream<?>, ThrowingConsumer<Sheet>>> write() default SheetWriter.class;

        String view() default DEFAULT;

    }

    @Repeatable(SpreadSheets.CellAccessors.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, Orthogonal.class})
    @interface Header {
        String name() default "";

        int position() default NO_POSITION;

        boolean required() default false;

        /**
         * Adjusts the column width to fit the contents.
         * This process can be relatively slow on large sheets, so this should normally only be called once per column, at the end of your processing.
         * <p>
         * You can specify whether the content of merged cells should be considered or ignored. Default is to ignore merged cells.
         * See {@link org.apache.poi.ss.usermodel.Sheet#autoSizeColumn(int) Sheet.autoSizeColumn(()}
         */
        boolean autoSizeColumn() default false;

        /**
         * Set the width (in units of 1/256th of a character width)
         * The maximum column width for an individual cell is 255 characters. This value represents the number of characters that can be displayed in a cell that is formatted with the standard font (first font in the workbook).
         * <p>
         * Character width is defined as the maximum digit width of the numbers 0, 1, 2, ... 9 as rendered using the default font (first font in the workbook).
         * <p>
         * Unless you are using a very special font, the default character is '0' (zero), this is true for Arial (default font font in HSSF) and Calibri (default font in XSSF)
         * <p>
         * Please note, that the width set by this method includes 4 pixels of margin padding (two on each side), plus 1 pixel padding for the gridlines (Section 3.3.1.12 of the OOXML spec). This results is a slightly less value of visible characters than passed to this method (approx. 1/2 of a character).
         * <p>
         * To compute the actual number of visible characters, Excel uses the following formula (Section 3.3.1.12 of the OOXML spec):
         * <p>
         * width = Truncate([{Number of Visible Characters} * {Maximum Digit Width} + {5 pixel padding}]/{Maximum Digit Width}*256)/256 Using the Calibri font as an example, the maximum digit width of 11 point font size is 7 pixels (at 96 dpi). If you set a column width to be eight characters wide, e.g. setColumnWidth(columnIndex, 8*256), then the actual value of visible characters (the value shown in Excel) is derived from the following equation: Truncate([numChars*7+5]/7*256)/256 = 8; which gives 7.29.
         * See {@link org.apache.poi.ss.usermodel.Sheet#setColumnWidth(int, int) Sheet.setColumnWidth(()}
         */
        short width() default -1;

        /**
         * Set the type of horizontal alignment for the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setAlignment(HorizontalAlignment)} CellStyle.setAlignment()}
         */
        HorizontalAlignment alignment() default HorizontalAlignment.DISTRIBUTED;

        /**
         * Set the type of border to use for the bottom border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         */
        BorderStyle borderBottom() default BorderStyle.NONE;

        /**
         * Set the type of border to use for the left border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         */
        BorderStyle borderLeft() default BorderStyle.NONE;


        /**
         * Set the type of border to use for the right border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle)} CellStyle.setBorderBottom()}
         */
        BorderStyle borderRight() default BorderStyle.NONE;

        /**
         * Set the type of border to use for the top border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         */
        BorderStyle borderTop() default BorderStyle.NONE;

        /**
         * Set the color to use for the bottom border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBottomBorderColor(short) CellStyle.setBottomBorderColor()}
         */
        IndexedColors bottomBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the background fill color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillBackgroundColor(short) CellStyle.setFillBackgroundColor()}
         */
        IndexedColors fillBackgroundColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the foreground fill color Note: Ensure Foreground color is set prior to background color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillForegroundColor(short) CellStyle.setFillForegroundColor()}
         */
        IndexedColors fillForegroundColor() default IndexedColors.AUTOMATIC;

        /**
         * Setting to one fills the cell with the foreground color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillPattern(FillPatternType) CellStyle.setFillPattern()}
         */
        FillPatternType fillPattern() default FillPatternType.NO_FILL;

        /**
         * Set the cell's using this style to be hidden.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setHidden(boolean) CellStyle.setHidden()}
         */
        boolean hidden() default false;

        /**
         * Set the number of spaces to indent the text in the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setIndention(short) CellStyle.setIndention()}
         */
        short indention() default -1;

        /**
         * Set the color to use for the left border
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setLeftBorderColor(short) CellStyle.setLeftBorderColor()}
         */
        IndexedColors leftBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the cell's using this style to be locked.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setLocked(boolean) CellStyle.setLocked()}
         */
        boolean locked() default false;

        /**
         * Turn on or off "Quote Prefix" or "123 Prefix" for the style, which is used to tell Excel that the thing which looks like a number or a formula shouldn't be treated as on..
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setQuotePrefixed(boolean) CellStyle.setQuotePrefixed()}
         */
        boolean quotePrefixed() default false;

        /**
         * Set the color to use for the right border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setRightBorderColor(short) CellStyle.setRightBorderColor()}
         */
        IndexedColors rightBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the degree of rotation for the text in the cell..
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setRotation(short) CellStyle.setRotation()}
         */
        short rotation() default -1;

        /**
         * Controls if the Cell should be auto-sized to shrink to fit if the text is too long.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setShrinkToFit(boolean) CellStyle.setShrinkToFit()}
         */
        boolean shrinkToFit() default false;

        /**
         * Set the color to use for the top border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setTopBorderColor(short) CellStyle.setTopBorderColor()}
         */
        IndexedColors topBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the type of vertical alignment for the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setVerticalAlignment(VerticalAlignment) CellStyle.setVerticalAlignment()}
         */
        VerticalAlignment verticalAlignment() default VerticalAlignment.DISTRIBUTED;

        /**
         * Set whether the text should be wrapped.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setWrapText(boolean) CellStyle.setWrapText()}
         */
        boolean wrapText() default false;

        /**
         * Set use of bold.
         * See {@link org.apache.poi.ss.usermodel.Font#setBold(boolean) Font.setBold()}
         */
        boolean bold() default false;

        /**
         * Set character-set to use.
         * See {@link org.apache.poi.ss.usermodel.Font#setCharSet(int) Font.setCharSet()}
         */
        FontCharset charSet() default FontCharset.DEFAULT;

        /**
         * Set the font color.
         * See {@link org.apache.poi.ss.usermodel.Font#setColor(short) Font.setColor()}
         */
        IndexedColors fontColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the font height in unit's of 1/20th of a point.
         * See {@link org.apache.poi.ss.usermodel.Font#setFontHeight(short) Font.setFontHeight()}
         */
        short fontHeight() default -1;

        /**
         * Set the font height.
         * See {@link org.apache.poi.ss.usermodel.Font#setFontHeightInPoints(short) Font.setFontHeightInPoints()}
         */
        short fontHeightInPoints() default -1;

        /**
         * Set the name for the font (i.e. Arial)
         * See {@link org.apache.poi.ss.usermodel.Font#setFontName(String) Font.setFontName()}
         */
        String fontName() default "";

        /**
         * Set whether to use italics or not
         * See {@link org.apache.poi.ss.usermodel.Font#setItalic(boolean)  Font.setItalic()}
         */
        boolean italic() default false;

        /**
         * Set whether to use a strikeout horizontal line through the text or not
         * See {@link org.apache.poi.ss.usermodel.Font#setStrikeout(boolean) Font.setStrikeout()}
         */
        boolean strikeout() default false;

        /**
         * Set normal,super or subscript.
         * See {@link org.apache.poi.ss.usermodel.Font#setTypeOffset(short)} Font.setTypeOffset()}
         */
        short typeOffset() default -1;

        /**
         * Set type of text underlining to use
         * see {@link org.apache.poi.ss.usermodel.Font#setUnderline(byte)} Font.setUnderline()}
         */
        FontUnderline underline() default FontUnderline.NONE;

        /**
         * The Style Provider to use for complex Styles.
         * If un-assigned it uses the configured values in the annotation
         */
        Class<? extends BiFunction<CellStyle, Workbook, CellStyle>> styleProvider() default EmptyStyleProvider.class;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        Class<? extends AnnotablePipe<?, Cell, Cell>> write() default IdentityCell.class;

        String view() default Annotations.DEFAULT;
    }

    @Repeatable(SpreadSheets.Styles.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, LineSelector.class})
    @interface ContentStyle {

        /**
         * Set the type of horizontal alignment for the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setAlignment(HorizontalAlignment)} CellStyle.setAlignment()}
         */
        HorizontalAlignment alignment() default HorizontalAlignment.DISTRIBUTED;

        /**
         * Set the type of border to use for the bottom border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         */
        BorderStyle borderBottom() default BorderStyle.NONE;

        /**
         * Set the type of border to use for the left border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         */
        BorderStyle borderLeft() default BorderStyle.NONE;


        /**
         * Set the type of border to use for the right border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle)} CellStyle.setBorderBottom()}
         */
        BorderStyle borderRight() default BorderStyle.NONE;

        /**
         * Set the type of border to use for the top border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         */
        BorderStyle borderTop() default BorderStyle.NONE;

        /**
         * Set the color to use for the bottom border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBottomBorderColor(short) CellStyle.setBottomBorderColor()}
         */
        IndexedColors bottomBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the background fill color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillBackgroundColor(short) CellStyle.setFillBackgroundColor()}
         */
        IndexedColors fillBackgroundColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the foreground fill color Note: Ensure Foreground color is set prior to background color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillForegroundColor(short) CellStyle.setFillForegroundColor()}
         */
        IndexedColors fillForegroundColor() default IndexedColors.AUTOMATIC;

        /**
         * Setting to one fills the cell with the foreground color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillPattern(FillPatternType) CellStyle.setFillPattern()}
         */
        FillPatternType fillPattern() default FillPatternType.NO_FILL;

        /**
         * Set the cell's using this style to be hidden.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setHidden(boolean) CellStyle.setHidden()}
         */
        boolean hidden() default false;

        /**
         * Set the number of spaces to indent the text in the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setIndention(short) CellStyle.setIndention()}
         */
        short indention() default -1;

        /**
         * Set the color to use for the left border
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setLeftBorderColor(short) CellStyle.setLeftBorderColor()}
         */
        IndexedColors leftBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the cell's using this style to be locked.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setLocked(boolean) CellStyle.setLocked()}
         */
        boolean locked() default false;

        /**
         * Turn on or off "Quote Prefix" or "123 Prefix" for the style, which is used to tell Excel that the thing which looks like a number or a formula shouldn't be treated as on..
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setQuotePrefixed(boolean) CellStyle.setQuotePrefixed()}
         */
        boolean quotePrefixed() default false;

        /**
         * Set the color to use for the right border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setRightBorderColor(short) CellStyle.setRightBorderColor()}
         */
        IndexedColors rightBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the degree of rotation for the text in the cell..
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setRotation(short) CellStyle.setRotation()}
         */
        short rotation() default -1;

        /**
         * Controls if the Cell should be auto-sized to shrink to fit if the text is too long.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setShrinkToFit(boolean) CellStyle.setShrinkToFit()}
         */
        boolean shrinkToFit() default false;

        /**
         * Set the color to use for the top border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setTopBorderColor(short) CellStyle.setTopBorderColor()}
         */
        IndexedColors topBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the type of vertical alignment for the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setVerticalAlignment(VerticalAlignment) CellStyle.setVerticalAlignment()}
         */
        VerticalAlignment verticalAlignment() default VerticalAlignment.DISTRIBUTED;

        /**
         * Set whether the text should be wrapped.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setWrapText(boolean) CellStyle.setWrapText()}
         */
        boolean wrapText() default false;

        /**
         * Tells on which lines to use.
         */
        Line useOn() default Line.ALL_LINES;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        Class<? extends AnnotablePipe<ContentStyle, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>>> write() default StyleStyler.class;

        String view() default Annotations.DEFAULT;
    }

    @Repeatable(SpreadSheets.Fonts.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, LineSelector.class})
    @interface ContentFont {

        /**
         * Set use of bold.
         * See {@link org.apache.poi.ss.usermodel.Font#setBold(boolean) Font.setBold()}
         */
        boolean bold() default false;

        /**
         * Set character-set to use.
         * See {@link org.apache.poi.ss.usermodel.Font#setCharSet(int) Font.setCharSet()}
         */
        FontCharset charSet() default FontCharset.DEFAULT;

        /**
         * Set the font color.
         * See {@link org.apache.poi.ss.usermodel.Font#setColor(short) Font.setColor()}
         */
        IndexedColors color() default IndexedColors.AUTOMATIC;

        /**
         * Set the font height in unit's of 1/20th of a point.
         * See {@link org.apache.poi.ss.usermodel.Font#setFontHeight(short) Font.setFontHeight()}
         */
        short fontHeight() default -1;

        /**
         * Set the font height.
         * See {@link org.apache.poi.ss.usermodel.Font#setFontHeightInPoints(short) Font.setFontHeightInPoints()}
         */
        short fontHeightInPoints() default -1;

        /**
         * Set the name for the font (i.e. Arial)
         * See {@link org.apache.poi.ss.usermodel.Font#setFontName(String) Font.setFontName()}
         */
        String fontName() default "";

        /**
         * Set whether to use italics or not
         * See {@link org.apache.poi.ss.usermodel.Font#setItalic(boolean)  Font.setItalic()}
         */
        boolean italic() default false;

        /**
         * Set whether to use a strikeout horizontal line through the text or not
         * See {@link org.apache.poi.ss.usermodel.Font#setStrikeout(boolean) Font.setStrikeout()}
         */
        boolean strikeout() default false;

        /**
         * Set normal,super or subscript.
         * See {@link org.apache.poi.ss.usermodel.Font#setTypeOffset(short)} Font.setTypeOffset()}
         */
        short typeOffset() default -1;

        /**
         * Set type of text underlining to use
         * see {@link org.apache.poi.ss.usermodel.Font#setUnderline(byte)} Font.setUnderline()}
         */
        FontUnderline underline() default FontUnderline.NONE;

        /**
         * Tells on which lines to use.
         */
        Line useOn() default Line.ALL_LINES;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        Class<? extends AnnotablePipe<ContentFont, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>>> write() default FontStyler.class;

        String view() default Annotations.DEFAULT;
    }


    @Repeatable(SpreadSheets.Formats.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, LineSelector.class})
    @interface ContentFormat {

        /**
         * The format to use for data format.
         * The Code uses the {@link org.apache.poi.ss.usermodel.CreationHelper CreationHelper} for creating the given data Format.
         * If left blank will be ignored.
         */
        String value() default "";

        /**
         * Set the data format (must be a valid format).
         * Built in formats are defined in {@link org.apache.poi.ss.usermodel.BuiltinFormats BuiltinFormats}
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setDataFormat(short) CellStyle.setDataFormat()}
         * The setting, if provided, overrides one provided by value.
         */
        short dataFormat() default -1;

        /**
         * Tells on which lines to use.
         */
        Line useOn() default Line.ALL_LINES;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        Class<? extends AnnotablePipe<ContentFormat, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>>> write() default FormatStyler.class;

        String view() default Annotations.DEFAULT;
    }

    @Repeatable(SpreadSheets.CustomStyles.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, LineSelector.class})
    @interface CustomStyle {

        /**
         * The Style Provider to use for complex Styles.
         */
        Class<? extends BiFunction<CellStyle, Workbook, CellStyle>> styleProvider();

        /**
         * Tells on which lines to use.
         */
        Line useOn() default Line.ALL_LINES;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        Class<? extends AnnotablePipe<CustomStyle, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>>> write() default CustomStyler.class;

        String view() default Annotations.DEFAULT;
    }
}
