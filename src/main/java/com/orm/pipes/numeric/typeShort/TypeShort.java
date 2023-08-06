package com.orm.pipes.numeric.typeShort;

import com.orm.annotations.Extends;
import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import com.orm.pipes.base.format.TypeFormat;
import com.orm.pipes.numeric.typeBigDecimal.BigDecimalPipes;
import com.orm.pipes.numeric.typeBigInteger.BigIntegerPipes;
import com.orm.pipes.numeric.typeByte.BytePipes;
import com.orm.pipes.numeric.typeDouble.DoublePipes;
import com.orm.pipes.numeric.typeFloat.FloatPipes;
import com.orm.pipes.numeric.typeInteger.IntegerPipes;
import com.orm.pipes.numeric.typeLong.LongPipes;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.orm.annotations.Annotations.DEFAULT;

@Repeatable(TypeShorts.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeShort {
    StrongType strongType = StrongType.of(Short.class);

    @Repeatable(TypeShorts.News.class)
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

        Class<? extends AnnotablePipe<New, String, Short>> read() default ShortPipes.ParseShort.class;

        Class<? extends AnnotablePipe<New, Short, String>> write() default ShortPipes.ShortToString.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

        short value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeShort.value, Object, Short>> read() default ShortPipes.Value.class;

        Class<? extends AnnotablePipe<TypeShort.value, Object, Short>> write() default ShortPipes.Value.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.intValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface intValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Short, Integer>> read() default ShortPipes.IntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, Integer, Short>> write() default IntegerPipes.ShortValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.unsignedIntValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface unsignedIntValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Short, Integer>> read() default ShortPipes.UnsignedIntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, Integer, Short>> write() default IntegerPipes.ShortValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.longValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface longValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Short, Long>> read() default ShortPipes.LongValue.class;

        Class<? extends AnnotablePipe<Annotation, Long, Short>> write() default LongPipes.ShortValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.unsignedLongValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface unsignedLongValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Short, Long>> read() default ShortPipes.UnsignedLongValue.class;

        Class<? extends AnnotablePipe<Annotation, Long, Short>> write() default LongPipes.ShortValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.doubleValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface doubleValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Short, Double>> read() default ShortPipes.DoubleValue.class;

        Class<? extends AnnotablePipe<Annotation, Double, Short>> write() default DoublePipes.ShortValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.floatValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface floatValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Short, Float>> read() default ShortPipes.FloatValue.class;

        Class<? extends AnnotablePipe<Annotation, Float, Short>> write() default FloatPipes.ShortValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.bigIntegerValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigIntegerValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Short, BigInteger>> read() default ShortPipes.BigIntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, BigInteger, Short>> write() default BigIntegerPipes.ShortValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.bigDecimalValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigDecimalValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Short, BigDecimal>> read() default ShortPipes.BigDecimalValue.class;

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Short>> write() default BigDecimalPipes.ShortValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.byteValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface byteValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Short, Byte>> read() default ShortPipes.ByteValue.class;

        Class<? extends AnnotablePipe<Annotation, Byte, Short>> write() default BytePipes.ShortValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeShorts.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeShort.cellValue, Cell, Short>> read() default ShortPipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeShort.cellValue, Short, ThrowingConsumer<Cell>>> write() default ShortPipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
