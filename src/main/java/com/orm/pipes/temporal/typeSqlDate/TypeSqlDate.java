package com.orm.pipes.temporal.typeSqlDate;

import com.orm.annotations.Extends;
import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import com.orm.pipes.base.format.TypeFormat;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;
import java.sql.Date;

import static com.orm.annotations.Annotations.DEFAULT;

@Repeatable(TypeSqlDates.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeSqlDate {
    StrongType strongType = StrongType.of(Date.class);

    @Repeatable(TypeSqlDates.News.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, TypeFormat.class})
    @interface New {
        /**
         * DateTime pattern
         * See https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
         * <p>
         * If left blank the language tag will be considered
         */
        String format() default ""; //

        /**
         * IETF BCP 47 language tag string
         * See https://en.wikipedia.org/wiki/IETF_language_tag
         * See https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#forLanguageTag-java.lang.String-
         * <p>
         * If left blank the default parse() or respectively toString method of the current type will be used.
         */
        String languageTag() default "";

        /**
         * A time-zone ID.
         * See https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html#of-java.lang.String-
         * If left blank it will resolve to System default.
         *
         * @return
         */
        String zoneId() default "";

        /**
         * If enabled the parse(String input) and respectively toString() methods of the current type will be used.
         * The value and languageTag will be ignored.
         * <p>
         * Usage is not general recommended but in special cases can improve performance.
         *
         * @return
         */
        boolean simple() default false;

        /**
         * If set to true it will return upon parsing/formatting null for null input values.
         *
         * @return
         */
        boolean nullSafe() default true;

        /**
         * If set to true it will :
         * - for blank input values on parsing will return null
         * - for null values on formatting will return blank.
         * <p>
         * This setting overrides the nullSafe settings, if enabled.
         *
         * @return
         */
        boolean blankSafe() default true;

        /**
         * Process the input without throwing an exception,
         * in case the parsing was unsuccessful
         * it will return null on parsing.
         *
         * <p>
         * The setting has no effect on Formatting
         *
         * @return
         */
        boolean noException() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<New, String, Date>> read() default SqlDatePipes.ParseSqlDate.class;

        Class<? extends AnnotablePipe<New, Date, String>> write() default SqlDatePipes.SqlDateToString.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeSqlDates.nows.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface now {
        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeSqlDate.now, Object, Date>> read() default SqlDatePipes.Now.class;

        Class<? extends AnnotablePipe<TypeSqlDate.now, Object, Date>> write() default SqlDatePipes.Now.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeSqlDates.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeSqlDate.cellValue, Cell, Date>> read() default SqlDatePipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeSqlDate.cellValue, Date, ThrowingConsumer<Cell>>> write() default SqlDatePipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
