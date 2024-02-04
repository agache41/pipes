package io.github.agache41.ormpipes.pipes.numeric.typeByte;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractNullSafe;
import io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal.TypeBigDecimal;
import io.github.agache41.ormpipes.pipes.numeric.typeBigInteger.TypeBigInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.TypeDouble;
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.TypeFloat;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.TypeLong;
import io.github.agache41.ormpipes.pipes.numeric.typeShort.TypeShort;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BytePipes {

    public static class Value implements AnnotablePipe<TypeByte.value, Object, Byte> {

        private Byte value;

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(TypeByte.value cfg) {
            this.value = cfg.value();
        }

        @Override
        public ThrowingFunction<Object, Byte> function() {
            return object -> this.value;
        }
    }

    public static class ShortValue extends AbstractNullSafe<Byte, Short> implements AnnotablePipe<Annotation, Byte, Short> {

        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Byte::shortValue;
            super.configure(cfg);
        }
    }

    public static class IntegerValue extends AbstractNullSafe<Byte, Integer> implements AnnotablePipe<Annotation, Byte, Integer> {

        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Byte::intValue;
            super.configure(cfg);
        }
    }

    public static class LongValue extends AbstractNullSafe<Byte, Long> implements AnnotablePipe<Annotation, Byte, Long> {

        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Byte::longValue;
            super.configure(cfg);
        }
    }

    public static class DoubleValue extends AbstractNullSafe<Byte, Double> implements AnnotablePipe<Annotation, Byte, Double> {

        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Byte::doubleValue;
            super.configure(cfg);
        }
    }

    public static class FloatValue extends AbstractNullSafe<Byte, Float> implements AnnotablePipe<Annotation, Byte, Float> {

        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Byte::floatValue;
            super.configure(cfg);
        }
    }

    public static class BigIntegerValue extends AbstractNullSafe<Byte, BigInteger> implements AnnotablePipe<Annotation, Byte, BigInteger> {

        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = shortValue -> BigInteger.valueOf(shortValue.longValue());
            super.configure(cfg);
        }
    }

    public static class BigDecimalValue extends AbstractNullSafe<Byte, BigDecimal> implements AnnotablePipe<Annotation, Byte, BigDecimal> {

        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = shortValue -> BigDecimal.valueOf(shortValue.longValue());
            super.configure(cfg);
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeByte.cellValue, Cell, Byte> {
        private ThrowingFunction<Cell, Byte> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(TypeByte.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return (byte) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, Byte> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeByte.cellValue, Byte, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Byte, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(TypeByte.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = byter -> byter == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(byter);
            else
                this.function = byter -> cell -> cell.setCellValue(byter);
        }

        @Override
        public ThrowingFunction<Byte, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
