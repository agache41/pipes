package io.github.agache41.ormpipes.pipes.numeric.typeByte;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal.BigDecimalPipes;
import io.github.agache41.ormpipes.pipes.numeric.typeBigInteger.BigIntegerPipes;
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
 * The interface Type byte.
 * </pre>
 */
@Repeatable(TypeBytes.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeByte {
    /**
     * <pre>
     * The constant strongType.
     * </pre>
     */
    StrongType strongType = StrongType.of(Byte.class);

    /**
     * <pre>
     * The interface Value.
     * </pre>
     */
    @Repeatable(TypeBytes.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

        /**
         * <pre>
         * Value byte.
         * </pre>
         *
         * @return the byte
         */
        byte value();

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
        Class<? extends AnnotablePipe<value, Object, Byte>> read() default BytePipes.Value.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<value, Object, Byte>> write() default BytePipes.Value.class;

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
    @Repeatable(TypeBytes.shortValues.class)
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
        Class<? extends AnnotablePipe<Annotation, Byte, Short>> read() default BytePipes.ShortValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Short, Byte>> write() default ShortPipes.ByteValue.class;

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
    @Repeatable(TypeBytes.intValues.class)
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
        Class<? extends AnnotablePipe<Annotation, Byte, Integer>> read() default BytePipes.IntegerValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Integer, Byte>> write() default IntegerPipes.ByteValue.class;

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
    @Repeatable(TypeBytes.longValues.class)
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
        Class<? extends AnnotablePipe<Annotation, Byte, Long>> read() default BytePipes.LongValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Long, Byte>> write() default LongPipes.ByteValue.class;

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
    @Repeatable(TypeBytes.doubleValues.class)
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
        Class<? extends AnnotablePipe<Annotation, Byte, Double>> read() default BytePipes.DoubleValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Double, Byte>> write() default DoublePipes.ByteValue.class;

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
    @Repeatable(TypeBytes.floatValues.class)
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
        Class<? extends AnnotablePipe<Annotation, Byte, Float>> read() default BytePipes.FloatValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, Float, Byte>> write() default FloatPipes.ByteValue.class;

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
    @Repeatable(TypeBytes.bigIntegerValues.class)
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
        Class<? extends AnnotablePipe<Annotation, Byte, BigInteger>> read() default BytePipes.BigIntegerValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, BigInteger, Byte>> write() default BigIntegerPipes.ByteValue.class;

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
     * The interface Big decimal value.
     * </pre>
     */
    @Repeatable(TypeBytes.bigDecimalValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigDecimalValue {

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
        Class<? extends AnnotablePipe<Annotation, Byte, BigDecimal>> read() default BytePipes.BigDecimalValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Annotation, BigDecimal, Byte>> write() default BigDecimalPipes.ByteValue.class;

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
    @Repeatable(TypeBytes.CellValues.class)
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
        Class<? extends AnnotablePipe<TypeByte.cellValue, Cell, Byte>> read() default BytePipes.ReadCellValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeByte.cellValue, Byte, ThrowingConsumer<Cell>>> write() default BytePipes.WriteCellValue.class;

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
