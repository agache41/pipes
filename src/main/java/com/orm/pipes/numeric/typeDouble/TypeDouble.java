package com.orm.pipes.numeric.typeDouble;

import com.orm.annotations.Extends;
import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import com.orm.pipes.base.format.TypeFormat;
import com.orm.pipes.numeric.typeBigDecimal.BigDecimalPipes;
import com.orm.pipes.numeric.typeBigInteger.BigIntegerPipes;
import com.orm.pipes.numeric.typeByte.BytePipes;
import com.orm.pipes.numeric.typeFloat.FloatPipes;
import com.orm.pipes.numeric.typeInteger.IntegerPipes;
import com.orm.pipes.numeric.typeLong.LongPipes;
import com.orm.pipes.numeric.typeShort.ShortPipes;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.orm.annotations.Annotations.DEFAULT;

@Repeatable(TypeDoubles.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeDouble {
    StrongType strongType = StrongType.of(Double.class);

    @Repeatable(TypeDoubles.News.class)
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

        Class<? extends AnnotablePipe<New, String, Double>> read() default DoublePipes.ParseDouble.class;

        Class<? extends AnnotablePipe<TypeDouble.New, Double, String>> write() default DoublePipes.DoubleToString.class;

        String view() default DEFAULT;

    }

    @Repeatable(TypeDoubles.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

        double value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeDouble.value, Object, Double>> read() default DoublePipes.Value.class;

        Class<? extends AnnotablePipe<TypeDouble.value, Object, Double>> write() default DoublePipes.Value.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDoubles.shortValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface shortValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Double, Short>> read() default DoublePipes.ShortValue.class;

        Class<? extends AnnotablePipe<Annotation, Short, Double>> write() default ShortPipes.DoubleValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDoubles.intValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface intValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Double, Integer>> read() default DoublePipes.IntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, Integer, Double>> write() default IntegerPipes.DoubleValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDoubles.longValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface longValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Double, Long>> read() default DoublePipes.LongValue.class;

        Class<? extends AnnotablePipe<Annotation, Long, Double>> write() default LongPipes.DoubleValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDoubles.floatValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface floatValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Double, Float>> read() default DoublePipes.FloatValue.class;

        Class<? extends AnnotablePipe<Annotation, Float, Double>> write() default FloatPipes.DoubleValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDoubles.bigIntegerValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigIntegerValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Double, BigInteger>> read() default DoublePipes.BigIntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, BigInteger, Double>> write() default BigIntegerPipes.DoubleValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDoubles.bigDecimalValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigDecimalValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Double, BigDecimal>> read() default DoublePipes.BigDecimalValue.class;

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Double>> write() default BigDecimalPipes.DoubleValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDoubles.byteValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface byteValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Double, Byte>> read() default DoublePipes.ByteValue.class;

        Class<? extends AnnotablePipe<Annotation, Byte, Double>> write() default BytePipes.DoubleValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeDoubles.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeDouble.cellValue, Cell, Double>> read() default DoublePipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeDouble.cellValue, Double, ThrowingConsumer<Cell>>> write() default DoublePipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
