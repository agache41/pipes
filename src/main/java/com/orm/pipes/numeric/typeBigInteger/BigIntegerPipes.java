package com.orm.pipes.numeric.typeBigInteger;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.format.AbstractFormat;
import com.orm.pipes.base.format.AbstractNullSafe;
import com.orm.pipes.base.format.AbstractParse;
import com.orm.pipes.numeric.typeBigDecimal.TypeBigDecimal;
import com.orm.pipes.numeric.typeByte.TypeByte;
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

public class BigIntegerPipes {
    public static class BigIntegerToString extends AbstractFormat<TypeBigInteger.New, BigInteger> implements AnnotablePipe<TypeBigInteger.New, BigInteger, String> {

        @Override
        public StrongType getInputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(TypeBigInteger.New cfg) {
            super.configure(cfg);
            this.function = BigInteger::toString;
        }
    }

    public static class ParseBigInteger extends AbstractParse<TypeBigInteger.New, BigInteger> implements AnnotablePipe<TypeBigInteger.New, String, BigInteger> {

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(TypeBigInteger.New cfg) {
            super.configure(cfg);
            this.function = BigInteger::new;
        }
    }

    public static class Value implements AnnotablePipe<TypeBigInteger.value, Object, BigInteger> {

        private BigInteger value;

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(TypeBigInteger.value cfg) {
            this.value = BigInteger.valueOf(cfg.value());
        }

        @Override
        public ThrowingFunction<Object, BigInteger> function() {
            return object -> this.value;
        }
    }


    public static class ShortValue extends AbstractNullSafe<BigInteger, Short> implements AnnotablePipe<Annotation, BigInteger, Short> {

        @Override
        public StrongType getInputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigInteger::shortValue;
            super.configure(cfg);
        }
    }

    public static class IntegerValue extends AbstractNullSafe<BigInteger, Integer> implements AnnotablePipe<Annotation, BigInteger, Integer> {

        @Override
        public StrongType getInputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigInteger::intValue;
            super.configure(cfg);
        }
    }

    public static class LongValue extends AbstractNullSafe<BigInteger, Long> implements AnnotablePipe<Annotation, BigInteger, Long> {

        @Override
        public StrongType getInputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigInteger::longValue;
            super.configure(cfg);
        }
    }

    public static class FloatValue extends AbstractNullSafe<BigInteger, Float> implements AnnotablePipe<Annotation, BigInteger, Float> {

        @Override
        public StrongType getInputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigInteger::floatValue;
            super.configure(cfg);
        }
    }

    public static class DoubleValue extends AbstractNullSafe<BigInteger, Double> implements AnnotablePipe<Annotation, BigInteger, Double> {

        @Override
        public StrongType getInputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigInteger::doubleValue;
            super.configure(cfg);
        }
    }


    public static class BigDecimalValue extends AbstractNullSafe<BigInteger, BigDecimal> implements AnnotablePipe<Annotation, BigInteger, BigDecimal> {

        @Override
        public StrongType getInputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigDecimal::new;
            super.configure(cfg);
        }
    }

    public static class ByteValue extends AbstractNullSafe<BigInteger, Byte> implements AnnotablePipe<Annotation, BigInteger, Byte> {

        @Override
        public StrongType getInputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigInteger::byteValue;
            super.configure(cfg);
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeBigInteger.cellValue, Cell, BigInteger> {
        private ThrowingFunction<Cell, BigInteger> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(TypeBigInteger.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return BigDecimal.valueOf(cell.getNumericCellValue())
                                 .toBigInteger();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, BigInteger> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeBigInteger.cellValue, BigInteger, ThrowingConsumer<Cell>> {
        private ThrowingFunction<BigInteger, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(TypeBigInteger.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = bigInteger -> bigInteger == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(bigInteger.doubleValue());
            else
                this.function = bigInteger -> cell -> cell.setCellValue(bigInteger.doubleValue());
        }

        @Override
        public ThrowingFunction<BigInteger, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
