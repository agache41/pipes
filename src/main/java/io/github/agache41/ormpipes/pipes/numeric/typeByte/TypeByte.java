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

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;

@Repeatable(TypeBytes.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeByte {
    StrongType strongType = StrongType.of(Byte.class);

    @Repeatable(TypeBytes.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

        byte value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<value, Object, Byte>> read() default BytePipes.Value.class;

        Class<? extends AnnotablePipe<value, Object, Byte>> write() default BytePipes.Value.class;

        String view() default DEFAULT;
    }


    @Repeatable(TypeBytes.shortValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface shortValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Byte, Short>> read() default BytePipes.ShortValue.class;

        Class<? extends AnnotablePipe<Annotation, Short, Byte>> write() default ShortPipes.ByteValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBytes.intValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface intValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Byte, Integer>> read() default BytePipes.IntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, Integer, Byte>> write() default IntegerPipes.ByteValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBytes.longValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface longValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Byte, Long>> read() default BytePipes.LongValue.class;

        Class<? extends AnnotablePipe<Annotation, Long, Byte>> write() default LongPipes.ByteValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBytes.doubleValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface doubleValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Byte, Double>> read() default BytePipes.DoubleValue.class;

        Class<? extends AnnotablePipe<Annotation, Double, Byte>> write() default DoublePipes.ByteValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBytes.floatValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface floatValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Byte, Float>> read() default BytePipes.FloatValue.class;

        Class<? extends AnnotablePipe<Annotation, Float, Byte>> write() default FloatPipes.ByteValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBytes.bigIntegerValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigIntegerValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Byte, BigInteger>> read() default BytePipes.BigIntegerValue.class;

        Class<? extends AnnotablePipe<Annotation, BigInteger, Byte>> write() default BigIntegerPipes.ByteValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBytes.bigDecimalValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface bigDecimalValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Byte, BigDecimal>> read() default BytePipes.BigDecimalValue.class;

        Class<? extends AnnotablePipe<Annotation, BigDecimal, Byte>> write() default BigDecimalPipes.ByteValue.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBytes.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeByte.cellValue, Cell, Byte>> read() default BytePipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeByte.cellValue, Byte, ThrowingConsumer<Cell>>> write() default BytePipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
