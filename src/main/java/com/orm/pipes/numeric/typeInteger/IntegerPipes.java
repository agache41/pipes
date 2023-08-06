package com.orm.pipes.numeric.typeInteger;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.format.AbstractFormat;
import com.orm.pipes.base.format.AbstractNullSafe;
import com.orm.pipes.base.format.AbstractParse;
import com.orm.pipes.numeric.typeBigDecimal.TypeBigDecimal;
import com.orm.pipes.numeric.typeBigInteger.TypeBigInteger;
import com.orm.pipes.numeric.typeByte.TypeByte;
import com.orm.pipes.numeric.typeDouble.TypeDouble;
import com.orm.pipes.numeric.typeFloat.TypeFloat;
import com.orm.pipes.numeric.typeLong.TypeLong;
import com.orm.pipes.numeric.typeShort.TypeShort;
import com.orm.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

public class IntegerPipes {
    public static class IntegerToString extends AbstractFormat<TypeInteger.New, Integer> implements AnnotablePipe<TypeInteger.New, Integer, String> {

        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(TypeInteger.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            this.function = this.getIntegerNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    public static class ParseInteger extends AbstractParse<TypeInteger.New, Integer> implements AnnotablePipe<TypeInteger.New, String, Integer> {

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(TypeInteger.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Integer::parseInt;
                return;
            }
            final NumberFormat integerInstance = this.getIntegerNumberFormat(cfg.format(),
                    cfg.languageTag());
            this.function = string -> integerInstance.parse(string)
                                                     .intValue();
        }
    }

    public static class Value implements AnnotablePipe<TypeInteger.value, Object, Integer> {

        private Integer value;

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(TypeInteger.value cfg) {
            this.value = cfg.value();
        }

        @Override
        public ThrowingFunction<Object, Integer> function() {
            return object -> this.value;
        }
    }

    public static class ShortValue extends AbstractNullSafe<Integer, Short> implements AnnotablePipe<Annotation, Integer, Short> {

        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::shortValue;
            super.configure(cfg);
        }
    }

    public static class LongValue extends AbstractNullSafe<Integer, Long> implements AnnotablePipe<Annotation, Integer, Long> {

        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::longValue;
            super.configure(cfg);
        }
    }

    public static class DoubleValue extends AbstractNullSafe<Integer, Double> implements AnnotablePipe<Annotation, Integer, Double> {

        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::doubleValue;
            super.configure(cfg);
        }
    }

    public static class FloatValue extends AbstractNullSafe<Integer, Float> implements AnnotablePipe<Annotation, Integer, Float> {

        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::floatValue;
            super.configure(cfg);
        }
    }

    public static class BigIntegerValue extends AbstractNullSafe<Integer, BigInteger> implements AnnotablePipe<Annotation, Integer, BigInteger> {

        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = integerValue -> BigInteger.valueOf(integerValue.longValue());
            super.configure(cfg);
        }
    }

    public static class BigDecimalValue extends AbstractNullSafe<Integer, BigDecimal> implements AnnotablePipe<Annotation, Integer, BigDecimal> {

        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = integerValue -> BigDecimal.valueOf(integerValue.longValue());
            super.configure(cfg);
        }
    }

    public static class ByteValue extends AbstractNullSafe<Integer, Byte> implements AnnotablePipe<Annotation, Integer, Byte> {

        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::byteValue;
            super.configure(cfg);
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeInteger.cellValue, Cell, Integer> {
        private ThrowingFunction<Cell, Integer> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(TypeInteger.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return (int) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, Integer> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeInteger.cellValue, Integer, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Integer, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(TypeInteger.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = integer -> integer == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(integer);
            else
                this.function = integer -> cell -> cell.setCellValue(integer);
        }

        @Override
        public ThrowingFunction<Integer, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
