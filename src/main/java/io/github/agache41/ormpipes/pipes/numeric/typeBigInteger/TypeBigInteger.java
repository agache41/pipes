package io.github.agache41.ormpipes.pipes.numeric.typeBigInteger;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.format.TypeFormat;
import io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal.BigDecimalPipes;
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

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;

@Repeatable(TypeBigIntegers.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeBigInteger {
    StrongType strongType = StrongType.of(BigInteger.class);

    @Repeatable(TypeBigIntegers.News.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, TypeFormat.class})
    @interface New {
        /**
         * The new(String input) and respectively toString() methods of the current type will always be used.
         * <p>
         */
        boolean simple() default true;

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

        Class<? extends AnnotablePipe<New, String, BigInteger>> read() default BigIntegerPipes.ParseBigInteger.class;

        Class<? extends AnnotablePipe<New, BigInteger, String>> write() default BigIntegerPipes.BigIntegerToString.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigIntegers.valueOfs.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

        long value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeBigInteger.value, Object, BigInteger>> read() default BigIntegerPipes.Value.class;

        Class<? extends AnnotablePipe<TypeBigInteger.value, Object, BigInteger>> write() default BigIntegerPipes.Value.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigIntegers.shortValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface shortValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigInteger, Short>> read() default BigIntegerPipes.ShortValue.class;

        Class<? extends AnnotablePipe<Annotation, Short, BigInteger>> write() default ShortPipes.BigIntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigIntegers.intValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface intValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigInteger, Integer>> read() default BigIntegerPipes.IntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, Integer, BigInteger>> write() default IntegerPipes.BigIntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigIntegers.longValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface longValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigInteger, Long>> read() default BigIntegerPipes.LongValue.class;

        Class<? extends AnnotablePipe<Annotation, Long, BigInteger>> write() default LongPipes.BigIntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigIntegers.doubleValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface doubleValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigInteger, Double>> read() default BigIntegerPipes.DoubleValue.class;

        Class<? extends AnnotablePipe<Annotation, Double, BigInteger>> write() default DoublePipes.BigIntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigIntegers.floatValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface floatValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigInteger, Float>> read() default BigIntegerPipes.FloatValue.class;

        Class<? extends AnnotablePipe<Annotation, Float, BigInteger>> write() default FloatPipes.BigIntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigIntegers.bigDecimalValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigDecimalValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigInteger, BigDecimal>> read() default BigIntegerPipes.BigDecimalValue.class;

        Class<? extends AnnotablePipe<Annotation, BigDecimal, BigInteger>> write() default BigDecimalPipes.BigIntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigIntegers.byteValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface byteValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, BigInteger, Byte>> read() default BigIntegerPipes.ByteValue.class;

        Class<? extends AnnotablePipe<Annotation, Byte, BigInteger>> write() default BytePipes.BigIntegerValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBigIntegers.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeBigInteger.cellValue, Cell, BigInteger>> read() default BigIntegerPipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeBigInteger.cellValue, BigInteger, ThrowingConsumer<Cell>>> write() default BigIntegerPipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
