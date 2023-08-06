package com.orm.pipes.numeric.typeInteger;

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
import com.orm.pipes.numeric.typeLong.LongPipes;
import com.orm.pipes.numeric.typeShort.ShortPipes;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.orm.annotations.Annotations.DEFAULT;

@Repeatable(TypeIntegers.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeInteger {
    StrongType strongType = StrongType.of(Integer.class);

    @Repeatable(TypeIntegers.News.class)
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

        Class<? extends AnnotablePipe<New, String, Integer>> read() default IntegerPipes.ParseInteger.class;

        Class<? extends AnnotablePipe<New, Integer, String>> write() default IntegerPipes.IntegerToString.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeIntegers.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

        int value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeInteger.value, Object, Integer>> read() default IntegerPipes.Value.class;

        Class<? extends AnnotablePipe<TypeInteger.value, Object, Integer>> write() default IntegerPipes.Value.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeIntegers.shortValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface shortValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Integer, Short>> read() default IntegerPipes.ShortValue.class;

        Class<? extends AnnotablePipe<Annotation, Short, Integer>> write() default ShortPipes.IntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeIntegers.longValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface longValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Integer, Long>> read() default IntegerPipes.LongValue.class;

        Class<? extends AnnotablePipe<Annotation, Long, Integer>> write() default LongPipes.IntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeIntegers.doubleValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface doubleValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Integer, Double>> read() default IntegerPipes.DoubleValue.class;

        Class<? extends AnnotablePipe<Annotation, Double, Integer>> write() default DoublePipes.IntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeIntegers.floatValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface floatValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Integer, Float>> read() default IntegerPipes.FloatValue.class;

        Class<? extends AnnotablePipe<Annotation, Float, Integer>> write() default FloatPipes.IntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeIntegers.bigIntegerValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigIntegerValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Integer, BigInteger>> read() default IntegerPipes.BigIntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, BigInteger, Integer>> write() default BigIntegerPipes.IntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeIntegers.bigDecimalValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigDecimalValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Integer, BigDecimal>> read() default IntegerPipes.BigDecimalValue.class;

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Integer>> write() default BigDecimalPipes.IntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeIntegers.byteValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface byteValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Integer, Byte>> read() default IntegerPipes.ByteValue.class;

        Class<? extends AnnotablePipe<Annotation, Byte, Integer>> write() default BytePipes.IntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeIntegers.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeInteger.cellValue, Cell, Integer>> read() default IntegerPipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeInteger.cellValue, Integer, ThrowingConsumer<Cell>>> write() default IntegerPipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
