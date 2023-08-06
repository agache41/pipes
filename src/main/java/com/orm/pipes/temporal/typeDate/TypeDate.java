package com.orm.pipes.temporal.typeDate;

import com.orm.annotations.Extends;
import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import com.orm.pipes.base.format.TypeFormat;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;
import java.util.Date;

import static com.orm.annotations.Annotations.DEFAULT;

@Repeatable(TypeDates.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeDate {
    StrongType strongType = StrongType.of(Date.class);

    @Repeatable(TypeDates.News.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, TypeFormat.class})
    @interface New {
        /**
         * DateTime pattern
         * See https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
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

        Class<? extends AnnotablePipe<New, String, Date>> read() default DatePipes.ParseDate.class;

        Class<? extends AnnotablePipe<New, Date, String>> write() default DatePipes.DateToString.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDates.nows.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface now {
        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeDate.now, Object, Date>> read() default DatePipes.Now.class;

        Class<? extends AnnotablePipe<TypeDate.now, Object, Date>> write() default DatePipes.Now.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDates.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeDate.cellValue, Cell, Date>> read() default DatePipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeDate.cellValue, Date, ThrowingConsumer<Cell>>> write() default DatePipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
