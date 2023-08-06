package com.orm.pipes.numeric.typeBigDecimal;

import com.orm.annotations.Extends;
import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import com.orm.pipes.base.format.TypeFormat;
import com.orm.pipes.numeric.typeBigInteger.BigIntegerPipes;
import com.orm.pipes.numeric.typeByte.BytePipes;
import com.orm.pipes.numeric.typeDouble.DoublePipes;
import com.orm.pipes.numeric.typeFloat.FloatPipes;
import com.orm.pipes.numeric.typeInteger.IntegerPipes;
import com.orm.pipes.numeric.typeLong.LongPipes;
import com.orm.pipes.numeric.typeShort.ShortPipes;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.orm.annotations.Annotations.DEFAULT;

@Repeatable(TypeBigDecimals.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeBigDecimal {
    StrongType strongType = StrongType.of(BigDecimal.class);

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

        Class<? extends AnnotablePipe<New, String, BigDecimal>> read() default BigDecimalPipes.ParseBigDecimal.class;

        Class<? extends AnnotablePipe<TypeBigDecimal.New, BigDecimal, String>> write() default BigDecimalPipes.BigDecimalToString.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

        long value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeBigDecimal.value, Object, BigDecimal>> read() default BigDecimalPipes.Value.class;

        Class<? extends AnnotablePipe<TypeBigDecimal.value, Object, BigDecimal>> write() default BigDecimalPipes.Value.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.shortValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface shortValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Short>> read() default BigDecimalPipes.ShortValue.class;

        Class<? extends AnnotablePipe<Annotation, Short, BigDecimal>> write() default ShortPipes.BigDecimalValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.intValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface intValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Integer>> read() default BigDecimalPipes.IntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, Integer, BigDecimal>> write() default IntegerPipes.BigDecimalValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.longValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface longValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Long>> read() default BigDecimalPipes.LongValue.class;

        Class<? extends AnnotablePipe<Annotation, Long, BigDecimal>> write() default LongPipes.BigDecimalValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.doubleValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface doubleValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Double>> read() default BigDecimalPipes.DoubleValue.class;

        Class<? extends AnnotablePipe<Annotation, Double, BigDecimal>> write() default DoublePipes.BigDecimalValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.floatValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface floatValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Float>> read() default BigDecimalPipes.FloatValue.class;

        Class<? extends AnnotablePipe<Annotation, Float, BigDecimal>> write() default FloatPipes.BigDecimalValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.bigIntegerValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigIntegerValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigDecimal, BigInteger>> read() default BigDecimalPipes.BigIntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, BigInteger, BigDecimal>> write() default BigIntegerPipes.BigDecimalValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.bigIntegerExactValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigIntegerExactValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigDecimal, BigInteger>> read() default BigDecimalPipes.BigIntegerExactValue.class;

        Class<? extends AnnotablePipe<Annotation, BigInteger, BigDecimal>> write() default BigIntegerPipes.BigDecimalValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.byteValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface byteValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Byte>> read() default BigDecimalPipes.ByteValue.class;

        Class<? extends AnnotablePipe<Annotation, Byte, BigDecimal>> write() default BytePipes.BigDecimalValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigDecimals.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeBigDecimal.cellValue, Cell, BigDecimal>> read() default BigDecimalPipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeBigDecimal.cellValue, BigDecimal, ThrowingConsumer<Cell>>> write() default BigDecimalPipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
