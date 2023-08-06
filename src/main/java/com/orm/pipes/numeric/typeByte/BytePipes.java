package com.orm.pipes.numeric.typeByte;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.format.AbstractNullSafe;
import com.orm.pipes.numeric.typeBigDecimal.TypeBigDecimal;
import com.orm.pipes.numeric.typeBigInteger.TypeBigInteger;
import com.orm.pipes.numeric.typeDouble.TypeDouble;
import com.orm.pipes.numeric.typeFloat.TypeFloat;
import com.orm.pipes.numeric.typeInteger.TypeInteger;
import com.orm.pipes.numeric.typeLong.TypeLong;
import com.orm.pipes.numeric.typeShort.TypeShort;
import com.orm.pipes.typeObject.TypeObject;
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
