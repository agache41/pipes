package com.orm.pipes.temporal.typeLocalTime;

import com.orm.annotations.Extends;
import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import com.orm.pipes.base.format.TypeFormat;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;
import java.time.LocalTime;

import static com.orm.annotations.Annotations.DEFAULT;

@Repeatable(TypeLocalTimes.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeLocalTime {
    StrongType strongType = StrongType.of(LocalTime.class);

    @Repeatable(TypeLocalTimes.News.class)
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
        String format() default "yyyy-MM-dd"; //

        /**
         * IETF BCP 47 language tag string
         * See https://en.wikipedia.org/wiki/IETF_language_tag
         * See https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#forLanguageTag-java.lang.String-
         * <p>
         * If left blank the default toString or respectively parse() method of the current type will be used.
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
         * A time-zone ID.
         * See https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html#of-java.lang.String-
         * If left blank it will resolve to System default.
         *
         * @return
         */
        String zoneId() default "";

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

        Class<? extends AnnotablePipe<New, String, LocalTime>> read() default LocalTimePipes.ParseLocalTime.class;

        Class<? extends AnnotablePipe<New, LocalTime, String>> write() default LocalTimePipes.LocalTimeToString.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeLocalTimes.nows.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface now {
        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeLocalTime.now, Object, LocalTime>> read() default LocalTimePipes.Now.class;

        Class<? extends AnnotablePipe<TypeLocalTime.now, Object, LocalTime>> write() default LocalTimePipes.Now.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeLocalTimes.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeLocalTime.cellValue, Cell, LocalTime>> read() default LocalTimePipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeLocalTime.cellValue, LocalTime, ThrowingConsumer<Cell>>> write() default LocalTimePipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
