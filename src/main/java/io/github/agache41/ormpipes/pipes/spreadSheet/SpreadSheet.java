/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.agache41.ormpipes.pipes.spreadSheet;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.Orthogonal;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalFile;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.base.parser.base.Parser;
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
import static io.github.agache41.ormpipes.config.Constants.DEFAULT;


/**
 * <pre>
 * The interface Spread sheet.
 * </pre>
 */
@Repeatable(SpreadSheets.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Extends(DualPipe.class)
public @interface SpreadSheet {

    /**
     * <pre>
     * The interface Xlsx.
     * </pre>
     */
    @Repeatable(SpreadSheets.xlsxes.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface xlsx {
        /**
         * <pre>
         * The constant extension.
         * </pre>
         */
        String extension = ".xlsx";

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, InputStream, Workbook>> read() default SpreadSheetReader.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<SpreadSheet.xlsx, ThrowingConsumer<XSSFWorkbook>, ThrowingConsumer<OutputStream>>> write() default XLSXWriter.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;

        /**
         * <pre>
         * The type String stream parser.
         * </pre>
         *
         * @param <T> the type parameter
         */
        class StringStreamParser<T> extends Parser<T, String, Stream<T>> {
            /**
             * <pre>
             * Instantiates a new String stream parser.
             * </pre>
             *
             * @param clazz the clazz
             */
            public StringStreamParser(Class<T> clazz) {
                super(clazz, String.class);
            }

            /**
             * <pre>
             * Instantiates a new String stream parser.
             * </pre>
             *
             * @param clazz the clazz
             * @param view  the view
             */
            public StringStreamParser(Class<T> clazz,
                                      String view) {
                super(clazz, String.class, view);
            }

            /**
             * <pre>
             * Of class spread sheet . xlsx . string stream parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @return the spread sheet . xlsx . string stream parser
             */
            public static <S> SpreadSheet.xlsx.StringStreamParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.xlsx.StringStreamParser<>(clazz);
            }

            /**
             * <pre>
             * Of class spread sheet . xlsx . string stream parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @param view  the view
             * @return the spread sheet . xlsx . string stream parser
             */
            public static <S> SpreadSheet.xlsx.StringStreamParser<S> ofClass(Class<S> clazz,
                                                                             String view) {
                return new SpreadSheet.xlsx.StringStreamParser<>(clazz, view);
            }
        }

        /**
         * <pre>
         * The type String object parser.
         * </pre>
         *
         * @param <T> the type parameter
         */
        class StringObjectParser<T> extends Parser<T, String, T> {
            /**
             * <pre>
             * Instantiates a new String object parser.
             * </pre>
             *
             * @param clazz the clazz
             */
            public StringObjectParser(Class<T> clazz) {
                super(clazz, String.class);
            }

            /**
             * <pre>
             * Instantiates a new String object parser.
             * </pre>
             *
             * @param clazz the clazz
             * @param view  the view
             */
            public StringObjectParser(Class<T> clazz,
                                      String view) {
                super(clazz, String.class, view);
            }

            /**
             * <pre>
             * Of class spread sheet . xlsx . string object parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @return the spread sheet . xlsx . string object parser
             */
            public static <S> SpreadSheet.xlsx.StringObjectParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.xlsx.StringObjectParser<>(clazz);
            }

            /**
             * <pre>
             * Of class spread sheet . xlsx . string object parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @param view  the view
             * @return the spread sheet . xlsx . string object parser
             */
            public static <S> SpreadSheet.xlsx.StringObjectParser<S> ofClass(Class<S> clazz,
                                                                             String view) {
                return new SpreadSheet.xlsx.StringObjectParser<>(clazz, view);
            }
        }

    }

    /**
     * <pre>
     * The interface Xls.
     * </pre>
     */
    @Repeatable(SpreadSheets.xlses.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface xls {
        /**
         * <pre>
         * The constant extension.
         * </pre>
         */
        String extension = ".xls";

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, InputStream, Workbook>> read() default SpreadSheetReader.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<SpreadSheet.xls, ThrowingConsumer<HSSFWorkbook>, ThrowingConsumer<OutputStream>>> write() default XLSWriter.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;

        /**
         * <pre>
         * The type String stream parser.
         * </pre>
         *
         * @param <T> the type parameter
         */
        class StringStreamParser<T> extends Parser<T, String, Stream<T>> {
            /**
             * <pre>
             * Instantiates a new String stream parser.
             * </pre>
             *
             * @param clazz the clazz
             */
            public StringStreamParser(Class<T> clazz) {
                super(clazz, String.class);
            }

            /**
             * <pre>
             * Instantiates a new String stream parser.
             * </pre>
             *
             * @param clazz the clazz
             * @param view  the view
             */
            public StringStreamParser(Class<T> clazz,
                                      String view) {
                super(clazz, String.class, view);
            }

            /**
             * <pre>
             * Of class spread sheet . xls . string stream parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @return the spread sheet . xls . string stream parser
             */
            public static <S> SpreadSheet.xls.StringStreamParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.xls.StringStreamParser<>(clazz);
            }

            /**
             * <pre>
             * Of class spread sheet . xls . string stream parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @param view  the view
             * @return the spread sheet . xls . string stream parser
             */
            public static <S> SpreadSheet.xls.StringStreamParser<S> ofClass(Class<S> clazz,
                                                                            String view) {
                return new SpreadSheet.xls.StringStreamParser<>(clazz, view);
            }
        }

        /**
         * <pre>
         * The type String object parser.
         * </pre>
         *
         * @param <T> the type parameter
         */
        class StringObjectParser<T> extends Parser<T, String, T> {
            /**
             * <pre>
             * Instantiates a new String object parser.
             * </pre>
             *
             * @param clazz the clazz
             */
            public StringObjectParser(Class<T> clazz) {
                super(clazz, String.class);
            }

            /**
             * <pre>
             * Instantiates a new String object parser.
             * </pre>
             *
             * @param clazz the clazz
             * @param view  the view
             */
            public StringObjectParser(Class<T> clazz,
                                      String view) {
                super(clazz, String.class, view);
            }

            /**
             * <pre>
             * Of class spread sheet . xls . string object parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @return the spread sheet . xls . string object parser
             */
            public static <S> SpreadSheet.xls.StringObjectParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.xls.StringObjectParser<>(clazz);
            }

            /**
             * <pre>
             * Of class spread sheet . xls . string object parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @param view  the view
             * @return the spread sheet . xls . string object parser
             */
            public static <S> SpreadSheet.xls.StringObjectParser<S> ofClass(Class<S> clazz,
                                                                            String view) {
                return new SpreadSheet.xls.StringObjectParser<>(clazz, view);
            }
        }

    }

    /**
     * <pre>
     * The interface File.
     * </pre>
     */
    @Repeatable(SpreadSheets.files.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface file {
        /**
         * This designates the file given as parameter as template,
         * if set to true the file must exist and its contents will be overwritten.
         * If set to false a new file will be created, and the old file overwritten, if exits.
         *
         * @return the boolean
         */
        boolean template() default false;

        /**
         * Determines the type of file (xsl or xslx) to use or to autodetect based on the extension.
         *
         * @return the type
         */
        Type type() default Type.AUTO;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<SpreadSheet.file, File, Workbook>> read() default SpreadSheetFileReader.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<SpreadSheet.file, ThrowingConsumer<Workbook>, ThrowingConsumer<File>>> write() default SpreadSheetFileWriter.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;

        /**
         * <pre>
         * The type File stream parser.
         * </pre>
         *
         * @param <T> the type parameter
         */
        class FileStreamParser<T> extends Parser<T, File, Stream<T>> {
            /**
             * <pre>
             * Instantiates a new File stream parser.
             * </pre>
             *
             * @param clazz the clazz
             */
            public FileStreamParser(Class<T> clazz) {
                super(clazz, File.class);
            }

            /**
             * <pre>
             * Instantiates a new File stream parser.
             * </pre>
             *
             * @param clazz the clazz
             * @param view  the view
             */
            public FileStreamParser(Class<T> clazz,
                                    String view) {
                super(clazz, File.class, view);
            }

            /**
             * <pre>
             * Of class spread sheet . file . file stream parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @return the spread sheet . file . file stream parser
             */
            public static <S> SpreadSheet.file.FileStreamParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.file.FileStreamParser<>(clazz);
            }

            /**
             * <pre>
             * Of class spread sheet . file . file stream parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @param view  the view
             * @return the spread sheet . file . file stream parser
             */
            public static <S> SpreadSheet.file.FileStreamParser<S> ofClass(Class<S> clazz,
                                                                           String view) {
                return new SpreadSheet.file.FileStreamParser<>(clazz, view);
            }
        }

        /**
         * <pre>
         * The type File object parser.
         * </pre>
         *
         * @param <T> the type parameter
         */
        class FileObjectParser<T> extends Parser<T, File, T> {
            /**
             * <pre>
             * Instantiates a new File object parser.
             * </pre>
             *
             * @param clazz the clazz
             */
            public FileObjectParser(Class<T> clazz) {
                super(clazz, File.class);
            }

            /**
             * <pre>
             * Instantiates a new File object parser.
             * </pre>
             *
             * @param clazz the clazz
             * @param view  the view
             */
            public FileObjectParser(Class<T> clazz,
                                    String view) {
                super(clazz, File.class, view);
            }

            /**
             * <pre>
             * Of class spread sheet . file . file object parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @return the spread sheet . file . file object parser
             */
            public static <S> SpreadSheet.file.FileObjectParser<S> ofClass(Class<S> clazz) {
                return new SpreadSheet.file.FileObjectParser<>(clazz);
            }

            /**
             * <pre>
             * Of class spread sheet . file . file object parser.
             * </pre>
             *
             * @param <S>   the type parameter
             * @param clazz the clazz
             * @param view  the view
             * @return the spread sheet . file . file object parser
             */
            public static <S> SpreadSheet.file.FileObjectParser<S> ofClass(Class<S> clazz,
                                                                           String view) {
                return new SpreadSheet.file.FileObjectParser<>(clazz, view);
            }
        }
    }


    /**
     * <pre>
     * The interface Select.
     * </pre>
     */
    @Repeatable(SpreadSheets.selects.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface select {
        /**
         * <pre>
         * Sheet name string.
         * </pre>
         *
         * @return the string
         */
        String sheetName() default "";

        /**
         * <pre>
         * Sheet index int.
         * </pre>
         *
         * @return the int
         */
        int sheetIndex() default 0;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<select, Workbook, Sheet>> read() default ReadSheetSelector.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<select, Workbook, Sheet>> write() default WriteSheetSelector.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;
    }

    /**
     * <pre>
     * The interface Sheet.
     * </pre>
     */
    @Repeatable(SpreadSheets.sheets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, OrthogonalFile.class})
    @interface sheet {

        /**
         * <pre>
         * Header string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
//Data File Settings
        String[] header() default {};

        /**
         * <pre>
         * Use first line as header boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean useFirstLineAsHeader() default true;

        /**
         * <pre>
         * Skip first n lines int.
         * </pre>
         *
         * @return the int
         */
        int skipFirstNLines() default 0;

        /**
         * <pre>
         * Naming method naming method.
         * </pre>
         *
         * @return the naming method
         */
        NamingMethod namingMethod() default NamingMethod.AccessorNames;

        /**
         * <pre>
         * Position method position method.
         * </pre>
         *
         * @return the position method
         */
        PositionMethod positionMethod() default PositionMethod.CSVFields;

        /**
         * <pre>
         * Model model.
         * </pre>
         *
         * @return the model
         */
        Model model() default Model.VariablePositions;

        /**
         * <pre>
         * Append boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean append() default false;

        /**
         * Offset on the X Scala (from left to right) for the active zone where data will be read or written.
         *
         * @return the int
         */
        int offsetX() default 0;

        /**
         * Offset on the Y Scala (from top to bottom) for the active zone where data will be read or written.
         *
         * @return the int
         */
        int offsetY() default 0;

        /**
         * Set the default column width for the sheet (if the columns do not define their own width) in characters
         * See {@link org.apache.poi.ss.usermodel.Sheet#setDefaultColumnWidth(int) Sheet.setDefaultColumnWidth()}
         *
         * @return the int
         */
        int defaultColumnWidth() default -1;

        /**
         * Set the default row height for the sheet (if the rows do not define their own height) in twips (1/20 of a point)
         * See {@link org.apache.poi.ss.usermodel.Sheet#setDefaultRowHeight(short) Sheet.setDefaultRowHeight()}
         *
         * @return the short
         */
        short defaultRowHeight() default -1;

        /**
         * Set the default row height for the sheet (if the rows do not define their own height) in points
         * See {@link org.apache.poi.ss.usermodel.Sheet#setDefaultRowHeightInPoints(float) Sheet.setDefaultRowHeightInPoints()}
         *
         * @return the int
         */
        int defaultRowHeightInPoints() default -1;

        /**
         * Disable checking for available Data on line.
         *
         * @return the boolean
         */
        boolean disableLineValidation() default false;

        /**
         * Applies auto-resizing on all columns.
         *
         * @return the boolean
         */
        boolean autoResizeAllColumns() default false;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<SpreadSheet.sheet, Sheet, Stream<?>>> read() default SheetReader.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<SpreadSheet.sheet, Stream<?>, ThrowingConsumer<Sheet>>> write() default SheetWriter.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;

    }

    /**
     * <pre>
     * The interface Header.
     * </pre>
     */
    @Repeatable(SpreadSheets.CellAccessors.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, Orthogonal.class})
    @interface Header {
        /**
         * <pre>
         * Name string.
         * </pre>
         *
         * @return the string
         */
        String name() default "";

        /**
         * <pre>
         * Position int.
         * </pre>
         *
         * @return the int
         */
        int position() default NO_POSITION;

        /**
         * <pre>
         * Required boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean required() default false;

        /**
         * Adjusts the column width to fit the contents.
         * This process can be relatively slow on large sheets, so this should normally only be called once per column, at the end of your processing.
         * <p>
         * You can specify whether the content of merged cells should be considered or ignored. Default is to ignore merged cells.
         * See {@link org.apache.poi.ss.usermodel.Sheet#autoSizeColumn(int) Sheet.autoSizeColumn(()}
         *
         * @return the boolean
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
         *
         * @return the short
         */
        short width() default -1;

        /**
         * Set the type of horizontal alignment for the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setAlignment(HorizontalAlignment)} CellStyle.setAlignment()}
         *
         * @return the horizontal alignment
         */
        HorizontalAlignment alignment() default HorizontalAlignment.DISTRIBUTED;

        /**
         * Set the type of border to use for the bottom border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         *
         * @return the border style
         */
        BorderStyle borderBottom() default BorderStyle.NONE;

        /**
         * Set the type of border to use for the left border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         *
         * @return the border style
         */
        BorderStyle borderLeft() default BorderStyle.NONE;


        /**
         * Set the type of border to use for the right border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle)} CellStyle.setBorderBottom()}
         *
         * @return the border style
         */
        BorderStyle borderRight() default BorderStyle.NONE;

        /**
         * Set the type of border to use for the top border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         *
         * @return the border style
         */
        BorderStyle borderTop() default BorderStyle.NONE;

        /**
         * Set the color to use for the bottom border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBottomBorderColor(short) CellStyle.setBottomBorderColor()}
         *
         * @return the indexed colors
         */
        IndexedColors bottomBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the background fill color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillBackgroundColor(short) CellStyle.setFillBackgroundColor()}
         *
         * @return the indexed colors
         */
        IndexedColors fillBackgroundColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the foreground fill color Note: Ensure Foreground color is set prior to background color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillForegroundColor(short) CellStyle.setFillForegroundColor()}
         *
         * @return the indexed colors
         */
        IndexedColors fillForegroundColor() default IndexedColors.AUTOMATIC;

        /**
         * Setting to one fills the cell with the foreground color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillPattern(FillPatternType) CellStyle.setFillPattern()}
         *
         * @return the fill pattern type
         */
        FillPatternType fillPattern() default FillPatternType.NO_FILL;

        /**
         * Set the cell's using this style to be hidden.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setHidden(boolean) CellStyle.setHidden()}
         *
         * @return the boolean
         */
        boolean hidden() default false;

        /**
         * Set the number of spaces to indent the text in the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setIndention(short) CellStyle.setIndention()}
         *
         * @return the short
         */
        short indention() default -1;

        /**
         * Set the color to use for the left border
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setLeftBorderColor(short) CellStyle.setLeftBorderColor()}
         *
         * @return the indexed colors
         */
        IndexedColors leftBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the cell's using this style to be locked.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setLocked(boolean) CellStyle.setLocked()}
         *
         * @return the boolean
         */
        boolean locked() default false;

        /**
         * Turn on or off "Quote Prefix" or "123 Prefix" for the style, which is used to tell Excel that the thing which looks like a number or a formula shouldn't be treated as on..
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setQuotePrefixed(boolean) CellStyle.setQuotePrefixed()}
         *
         * @return the boolean
         */
        boolean quotePrefixed() default false;

        /**
         * Set the color to use for the right border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setRightBorderColor(short) CellStyle.setRightBorderColor()}
         *
         * @return the indexed colors
         */
        IndexedColors rightBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the degree of rotation for the text in the cell..
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setRotation(short) CellStyle.setRotation()}
         *
         * @return the short
         */
        short rotation() default -1;

        /**
         * Controls if the Cell should be auto-sized to shrink to fit if the text is too long.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setShrinkToFit(boolean) CellStyle.setShrinkToFit()}
         *
         * @return the boolean
         */
        boolean shrinkToFit() default false;

        /**
         * Set the color to use for the top border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setTopBorderColor(short) CellStyle.setTopBorderColor()}
         *
         * @return the indexed colors
         */
        IndexedColors topBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the type of vertical alignment for the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setVerticalAlignment(VerticalAlignment) CellStyle.setVerticalAlignment()}
         *
         * @return the vertical alignment
         */
        VerticalAlignment verticalAlignment() default VerticalAlignment.DISTRIBUTED;

        /**
         * Set whether the text should be wrapped.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setWrapText(boolean) CellStyle.setWrapText()}
         *
         * @return the boolean
         */
        boolean wrapText() default false;

        /**
         * Set use of bold.
         * See {@link org.apache.poi.ss.usermodel.Font#setBold(boolean) Font.setBold()}
         *
         * @return the boolean
         */
        boolean bold() default false;

        /**
         * Set character-set to use.
         * See {@link org.apache.poi.ss.usermodel.Font#setCharSet(int) Font.setCharSet()}
         *
         * @return the font charset
         */
        FontCharset charSet() default FontCharset.DEFAULT;

        /**
         * Set the font color.
         * See {@link org.apache.poi.ss.usermodel.Font#setColor(short) Font.setColor()}
         *
         * @return the indexed colors
         */
        IndexedColors fontColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the font height in unit's of 1/20th of a point.
         * See {@link org.apache.poi.ss.usermodel.Font#setFontHeight(short) Font.setFontHeight()}
         *
         * @return the short
         */
        short fontHeight() default -1;

        /**
         * Set the font height.
         * See {@link org.apache.poi.ss.usermodel.Font#setFontHeightInPoints(short) Font.setFontHeightInPoints()}
         *
         * @return the short
         */
        short fontHeightInPoints() default -1;

        /**
         * Set the name for the font (i.e. Arial)
         * See {@link org.apache.poi.ss.usermodel.Font#setFontName(String) Font.setFontName()}
         *
         * @return the string
         */
        String fontName() default "";

        /**
         * Set whether to use italics or not
         * See {@link org.apache.poi.ss.usermodel.Font#setItalic(boolean)  Font.setItalic()}
         *
         * @return the boolean
         */
        boolean italic() default false;

        /**
         * Set whether to use a strikeout horizontal line through the text or not
         * See {@link org.apache.poi.ss.usermodel.Font#setStrikeout(boolean) Font.setStrikeout()}
         *
         * @return the boolean
         */
        boolean strikeout() default false;

        /**
         * Set normal,super or subscript.
         * See {@link org.apache.poi.ss.usermodel.Font#setTypeOffset(short)} Font.setTypeOffset()}
         *
         * @return the short
         */
        short typeOffset() default -1;

        /**
         * Set type of text underlining to use
         * see {@link org.apache.poi.ss.usermodel.Font#setUnderline(byte)} Font.setUnderline()}
         *
         * @return the font underline
         */
        FontUnderline underline() default FontUnderline.NONE;

        /**
         * The Style Provider to use for complex Styles.
         * If un-assigned it uses the configured values in the annotation
         *
         * @return the class
         */
        Class<? extends BiFunction<CellStyle, Workbook, CellStyle>> styleProvider() default EmptyStyleProvider.class;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Cell, Cell>> write() default IdentityCell.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }

    /**
     * <pre>
     * The interface Content style.
     * </pre>
     */
    @Repeatable(SpreadSheets.Styles.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, LineSelector.class})
    @interface ContentStyle {

        /**
         * Set the type of horizontal alignment for the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setAlignment(HorizontalAlignment)} CellStyle.setAlignment()}
         *
         * @return the horizontal alignment
         */
        HorizontalAlignment alignment() default HorizontalAlignment.DISTRIBUTED;

        /**
         * Set the type of border to use for the bottom border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         *
         * @return the border style
         */
        BorderStyle borderBottom() default BorderStyle.NONE;

        /**
         * Set the type of border to use for the left border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         *
         * @return the border style
         */
        BorderStyle borderLeft() default BorderStyle.NONE;


        /**
         * Set the type of border to use for the right border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle)} CellStyle.setBorderBottom()}
         *
         * @return the border style
         */
        BorderStyle borderRight() default BorderStyle.NONE;

        /**
         * Set the type of border to use for the top border of the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBorderBottom(BorderStyle) CellStyle.setBorderBottom()}
         *
         * @return the border style
         */
        BorderStyle borderTop() default BorderStyle.NONE;

        /**
         * Set the color to use for the bottom border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setBottomBorderColor(short) CellStyle.setBottomBorderColor()}
         *
         * @return the indexed colors
         */
        IndexedColors bottomBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the background fill color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillBackgroundColor(short) CellStyle.setFillBackgroundColor()}
         *
         * @return the indexed colors
         */
        IndexedColors fillBackgroundColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the foreground fill color Note: Ensure Foreground color is set prior to background color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillForegroundColor(short) CellStyle.setFillForegroundColor()}
         *
         * @return the indexed colors
         */
        IndexedColors fillForegroundColor() default IndexedColors.AUTOMATIC;

        /**
         * Setting to one fills the cell with the foreground color.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setFillPattern(FillPatternType) CellStyle.setFillPattern()}
         *
         * @return the fill pattern type
         */
        FillPatternType fillPattern() default FillPatternType.NO_FILL;

        /**
         * Set the cell's using this style to be hidden.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setHidden(boolean) CellStyle.setHidden()}
         *
         * @return the boolean
         */
        boolean hidden() default false;

        /**
         * Set the number of spaces to indent the text in the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setIndention(short) CellStyle.setIndention()}
         *
         * @return the short
         */
        short indention() default -1;

        /**
         * Set the color to use for the left border
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setLeftBorderColor(short) CellStyle.setLeftBorderColor()}
         *
         * @return the indexed colors
         */
        IndexedColors leftBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the cell's using this style to be locked.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setLocked(boolean) CellStyle.setLocked()}
         *
         * @return the boolean
         */
        boolean locked() default false;

        /**
         * Turn on or off "Quote Prefix" or "123 Prefix" for the style, which is used to tell Excel that the thing which looks like a number or a formula shouldn't be treated as on..
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setQuotePrefixed(boolean) CellStyle.setQuotePrefixed()}
         *
         * @return the boolean
         */
        boolean quotePrefixed() default false;

        /**
         * Set the color to use for the right border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setRightBorderColor(short) CellStyle.setRightBorderColor()}
         *
         * @return the indexed colors
         */
        IndexedColors rightBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the degree of rotation for the text in the cell..
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setRotation(short) CellStyle.setRotation()}
         *
         * @return the short
         */
        short rotation() default -1;

        /**
         * Controls if the Cell should be auto-sized to shrink to fit if the text is too long.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setShrinkToFit(boolean) CellStyle.setShrinkToFit()}
         *
         * @return the boolean
         */
        boolean shrinkToFit() default false;

        /**
         * Set the color to use for the top border.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setTopBorderColor(short) CellStyle.setTopBorderColor()}
         *
         * @return the indexed colors
         */
        IndexedColors topBorderColor() default IndexedColors.AUTOMATIC;

        /**
         * Set the type of vertical alignment for the cell.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setVerticalAlignment(VerticalAlignment) CellStyle.setVerticalAlignment()}
         *
         * @return the vertical alignment
         */
        VerticalAlignment verticalAlignment() default VerticalAlignment.DISTRIBUTED;

        /**
         * Set whether the text should be wrapped.
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setWrapText(boolean) CellStyle.setWrapText()}
         *
         * @return the boolean
         */
        boolean wrapText() default false;

        /**
         * Tells on which lines to use.
         *
         * @return the line
         */
        Line useOn() default Line.ALL_LINES;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<ContentStyle, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>>> write() default StyleStyler.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }

    /**
     * <pre>
     * The interface Content font.
     * </pre>
     */
    @Repeatable(SpreadSheets.Fonts.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, LineSelector.class})
    @interface ContentFont {

        /**
         * Set use of bold.
         * See {@link org.apache.poi.ss.usermodel.Font#setBold(boolean) Font.setBold()}
         *
         * @return the boolean
         */
        boolean bold() default false;

        /**
         * Set character-set to use.
         * See {@link org.apache.poi.ss.usermodel.Font#setCharSet(int) Font.setCharSet()}
         *
         * @return the font charset
         */
        FontCharset charSet() default FontCharset.DEFAULT;

        /**
         * Set the font color.
         * See {@link org.apache.poi.ss.usermodel.Font#setColor(short) Font.setColor()}
         *
         * @return the indexed colors
         */
        IndexedColors color() default IndexedColors.AUTOMATIC;

        /**
         * Set the font height in unit's of 1/20th of a point.
         * See {@link org.apache.poi.ss.usermodel.Font#setFontHeight(short) Font.setFontHeight()}
         *
         * @return the short
         */
        short fontHeight() default -1;

        /**
         * Set the font height.
         * See {@link org.apache.poi.ss.usermodel.Font#setFontHeightInPoints(short) Font.setFontHeightInPoints()}
         *
         * @return the short
         */
        short fontHeightInPoints() default -1;

        /**
         * Set the name for the font (i.e. Arial)
         * See {@link org.apache.poi.ss.usermodel.Font#setFontName(String) Font.setFontName()}
         *
         * @return the string
         */
        String fontName() default "";

        /**
         * Set whether to use italics or not
         * See {@link org.apache.poi.ss.usermodel.Font#setItalic(boolean)  Font.setItalic()}
         *
         * @return the boolean
         */
        boolean italic() default false;

        /**
         * Set whether to use a strikeout horizontal line through the text or not
         * See {@link org.apache.poi.ss.usermodel.Font#setStrikeout(boolean) Font.setStrikeout()}
         *
         * @return the boolean
         */
        boolean strikeout() default false;

        /**
         * Set normal,super or subscript.
         * See {@link org.apache.poi.ss.usermodel.Font#setTypeOffset(short)} Font.setTypeOffset()}
         *
         * @return the short
         */
        short typeOffset() default -1;

        /**
         * Set type of text underlining to use
         * see {@link org.apache.poi.ss.usermodel.Font#setUnderline(byte)} Font.setUnderline()}
         *
         * @return the font underline
         */
        FontUnderline underline() default FontUnderline.NONE;

        /**
         * Tells on which lines to use.
         *
         * @return the line
         */
        Line useOn() default Line.ALL_LINES;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<ContentFont, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>>> write() default FontStyler.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }


    /**
     * <pre>
     * The interface Content format.
     * </pre>
     */
    @Repeatable(SpreadSheets.Formats.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, LineSelector.class})
    @interface ContentFormat {

        /**
         * The format to use for data format.
         * The Code uses the {@link org.apache.poi.ss.usermodel.CreationHelper CreationHelper} for creating the given data Format.
         * If left blank will be ignored.
         *
         * @return the string
         */
        String value() default "";

        /**
         * Set the data format (must be a valid format).
         * Built in formats are defined in {@link org.apache.poi.ss.usermodel.BuiltinFormats BuiltinFormats}
         * See {@link org.apache.poi.ss.usermodel.CellStyle#setDataFormat(short) CellStyle.setDataFormat()}
         * The setting, if provided, overrides one provided by value.
         *
         * @return the short
         */
        short dataFormat() default -1;

        /**
         * Tells on which lines to use.
         *
         * @return the line
         */
        Line useOn() default Line.ALL_LINES;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<ContentFormat, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>>> write() default FormatStyler.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }

    /**
     * <pre>
     * The interface Custom style.
     * </pre>
     */
    @Repeatable(SpreadSheets.CustomStyles.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, LineSelector.class})
    @interface CustomStyle {

        /**
         * The Style Provider to use for complex Styles.
         *
         * @return the class
         */
        Class<? extends BiFunction<CellStyle, Workbook, CellStyle>> styleProvider();

        /**
         * Tells on which lines to use.
         *
         * @return the line
         */
        Line useOn() default Line.ALL_LINES;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Cell, Cell>> read() default IdentityCell.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<CustomStyle, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>>> write() default CustomStyler.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }
}
