package io.github.agache41.ormpipes.pipes.temporal.typeLocalTime;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.format.TypeFormat;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;
import java.time.LocalTime;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;

/**
 * <pre>
 * The interface Type local time.
 * </pre>
 */
@Repeatable(TypeLocalTimes.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeLocalTime {
    /**
     * <pre>
     * The constant strongType.
     * </pre>
     */
    StrongType strongType = StrongType.of(LocalTime.class);

    /**
     * <pre>
     * The interface New.
     * </pre>
     */
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
         *
         * @return the string
         */
        String format() default "yyyy-MM-dd"; //

        /**
         * IETF BCP 47 language tag string
         * See https://en.wikipedia.org/wiki/IETF_language_tag
         * See https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#forLanguageTag-java.lang.String-
         * <p>
         * If left blank the default toString or respectively parse() method of the current type will be used.
         *
         * @return the string
         */
        String languageTag() default "";

        /**
         * If enabled the parse(String input) and respectively toString() methods of the current type will be used.
         * The value and languageTag will be ignored.
         * <p>
         * Usage is not general recommended but in special cases can improve performance.
         *
         * @return boolean
         */
        boolean simple() default false;

        /**
         * A time-zone ID.
         * See https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html#of-java.lang.String-
         * If left blank it will resolve to System default.
         *
         * @return string
         */
        String zoneId() default "";

        /**
         * If set to true it will return upon parsing/formatting null for null input values.
         *
         * @return boolean
         */
        boolean nullSafe() default true;

        /**
         * If set to true it will :
         * - for blank input values on parsing will return null
         * - for null values on formatting will return blank.
         * <p>
         * This setting overrides the nullSafe settings, if enabled.
         *
         * @return boolean
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
         * @return boolean
         */
        boolean noException() default true;

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
        Class<? extends AnnotablePipe<New, String, LocalTime>> read() default LocalTimePipes.ParseLocalTime.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<New, LocalTime, String>> write() default LocalTimePipes.LocalTimeToString.class;

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
     * The interface Now.
     * </pre>
     */
    @Repeatable(TypeLocalTimes.nows.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface now {
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
        Class<? extends AnnotablePipe<TypeLocalTime.now, Object, LocalTime>> read() default LocalTimePipes.Now.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeLocalTime.now, Object, LocalTime>> write() default LocalTimePipes.Now.class;

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
     * The interface Cell value.
     * </pre>
     */
    @Repeatable(TypeLocalTimes.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeLocalTime.cellValue, Cell, LocalTime>> read() default LocalTimePipes.ReadCellValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeLocalTime.cellValue, LocalTime, ThrowingConsumer<Cell>>> write() default LocalTimePipes.WriteCellValue.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;

    }
}
