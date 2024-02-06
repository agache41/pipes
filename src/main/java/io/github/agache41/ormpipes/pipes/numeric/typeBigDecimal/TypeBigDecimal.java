package io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.format.TypeFormat;
import io.github.agache41.ormpipes.pipes.numeric.typeBigInteger.BigIntegerPipes;
import io.github.agache41.ormpipes.pipes.numeric.typeByte.BytePipes;
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.DoublePipes;
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.FloatPipes;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.IntegerPipes;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.LongPipes;
import io.github.agache41.ormpipes.pipes.numeric.typeShort.ShortPipes;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;

/**
 * <pre>
 * The interface Type big decimal.
 * </pre>
 */
@Repeatable(TypeBigDecimals.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeBigDecimal {
    /**
     * <pre>
     * The constant strongType.
     * </pre>
     */
    StrongType strongType = StrongType.of(BigDecimal.class);

    /**
     * <pre>
     * The interface New.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.News.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, TypeFormat.class})
    @interface New {
        /**
         * DecimalFormat pattern to be used.
         * See <a href="https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html">DecimalFormat</a>
         * <p>
         * In the Format the Decimal Symbols will be configured using @{languageTag}, if provided.
         * If left blank the global configured value using Configuration will be used,
         * if provided or defaults to the configured Locale values using @{languageTag}.
         * The @Settings.DEFAULT value defaults to the configured Locale values using @{languageTag}.
         *
         * @return the string
         */
        String format() default ""; // example "#,###.##"

        /**
         * IETF BCP 47 language tag string to be used for identifying desired Locale Decimal Format Symbols.
         * See https://en.wikipedia.org/wiki/IETF_language_tag
         * See https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#forLanguageTag-java.lang.String-
         * <p>
         * If left blank the global configured value using Configuration will be used,
         * if provided or defaults to the configured Locale values using @{languageTag}.
         * The @Settings.DEFAULT value defaults to the default machine Locale.
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
        Class<? extends AnnotablePipe<New, String, BigDecimal>> read() default BigDecimalPipes.ParseBigDecimal.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeBigDecimal.New, BigDecimal, String>> write() default BigDecimalPipes.BigDecimalToString.class;

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
     * The interface Value.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

        /**
         * <pre>
         * Value long.
         * </pre>
         *
         * @return the long
         */
        long value();

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
        Class<? extends AnnotablePipe<TypeBigDecimal.value, Object, BigDecimal>> read() default BigDecimalPipes.Value.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeBigDecimal.value, Object, BigDecimal>> write() default BigDecimalPipes.Value.class;

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
     * The interface Short value.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.shortValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface shortValue {

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
        Class<? extends AnnotablePipe<Annotation, BigDecimal, Short>> read() default BigDecimalPipes.ShortValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Short, BigDecimal>> write() default ShortPipes.BigDecimalValue.class;

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
     * The interface Int value.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.intValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface intValue {

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
        Class<? extends AnnotablePipe<Annotation, BigDecimal, Integer>> read() default BigDecimalPipes.IntegerValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Integer, BigDecimal>> write() default IntegerPipes.BigDecimalValue.class;

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
     * The interface Long value.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.longValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface longValue {

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
        Class<? extends AnnotablePipe<Annotation, BigDecimal, Long>> read() default BigDecimalPipes.LongValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Long, BigDecimal>> write() default LongPipes.BigDecimalValue.class;

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
     * The interface Double value.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.doubleValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface doubleValue {

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
        Class<? extends AnnotablePipe<Annotation, BigDecimal, Double>> read() default BigDecimalPipes.DoubleValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Double, BigDecimal>> write() default DoublePipes.BigDecimalValue.class;

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
     * The interface Float value.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.floatValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface floatValue {

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
        Class<? extends AnnotablePipe<Annotation, BigDecimal, Float>> read() default BigDecimalPipes.FloatValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Float, BigDecimal>> write() default FloatPipes.BigDecimalValue.class;

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
     * The interface Big integer value.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.bigIntegerValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigIntegerValue {

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
        Class<? extends AnnotablePipe<Annotation, BigDecimal, BigInteger>> read() default BigDecimalPipes.BigIntegerValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, BigInteger, BigDecimal>> write() default BigIntegerPipes.BigDecimalValue.class;

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
     * The interface Big integer exact value.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.bigIntegerExactValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigIntegerExactValue {

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
        Class<? extends AnnotablePipe<Annotation, BigDecimal, BigInteger>> read() default BigDecimalPipes.BigIntegerExactValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, BigInteger, BigDecimal>> write() default BigIntegerPipes.BigDecimalValue.class;

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
     * The interface Byte value.
     * </pre>
     */
    @Repeatable(TypeBigDecimals.byteValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface byteValue {

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
        Class<? extends AnnotablePipe<Annotation, BigDecimal, Byte>> read() default BigDecimalPipes.ByteValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Byte, BigDecimal>> write() default BytePipes.BigDecimalValue.class;

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
    @Repeatable(TypeBigDecimals.CellValues.class)
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
        Class<? extends AnnotablePipe<TypeBigDecimal.cellValue, Cell, BigDecimal>> read() default BigDecimalPipes.ReadCellValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeBigDecimal.cellValue, BigDecimal, ThrowingConsumer<Cell>>> write() default BigDecimalPipes.WriteCellValue.class;

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
