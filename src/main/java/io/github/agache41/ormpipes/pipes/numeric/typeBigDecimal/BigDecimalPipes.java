package io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractNullSafe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.numeric.typeBigInteger.TypeBigInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeByte.TypeByte;
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
import java.text.DecimalFormat;

public class BigDecimalPipes {

    public static class BigDecimalToString extends AbstractFormat<TypeBigDecimal.New, BigDecimal> implements AnnotablePipe<TypeBigDecimal.New, BigDecimal, String> {

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(TypeBigDecimal.New cfg) {
            super.configure(cfg);
            if (cfg.simple()) return;
            this.function = this.getBigDecimalNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    public static class ParseBigDecimal extends AbstractParse<TypeBigDecimal.New, BigDecimal> implements AnnotablePipe<TypeBigDecimal.New, String, BigDecimal> {

        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(TypeBigDecimal.New cfg) {
            super.configure(cfg);
            if (cfg.simple()) {
                this.function = BigDecimal::new;
                return;
            }
            final DecimalFormat bigDecimalNumberFormat = this.getBigDecimalNumberFormat(cfg.format(), cfg.languageTag());
            this.function = string -> (BigDecimal) bigDecimalNumberFormat.parse(string);
        }
    }

    public static class Value implements AnnotablePipe<TypeBigDecimal.value, Object, BigDecimal> {

        private BigDecimal value;

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(TypeBigDecimal.value cfg) {
            this.value = BigDecimal.valueOf(cfg.value());
        }

        @Override
        public ThrowingFunction<Object, BigDecimal> function() {
            return object -> this.value;
        }
    }

    public static class ShortValue extends AbstractNullSafe<BigDecimal, Short> implements AnnotablePipe<Annotation, BigDecimal, Short> {

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigDecimal::shortValue;
            super.configure(cfg);
        }
    }

    public static class IntegerValue extends AbstractNullSafe<BigDecimal, Integer> implements AnnotablePipe<Annotation, BigDecimal, Integer> {

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigDecimal::intValue;
            super.configure(cfg);
        }
    }

    public static class LongValue extends AbstractNullSafe<BigDecimal, Long> implements AnnotablePipe<Annotation, BigDecimal, Long> {

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigDecimal::longValue;
            super.configure(cfg);
        }
    }

    public static class FloatValue extends AbstractNullSafe<BigDecimal, Float> implements AnnotablePipe<Annotation, BigDecimal, Float> {

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigDecimal::floatValue;
            super.configure(cfg);
        }
    }

    public static class DoubleValue extends AbstractNullSafe<BigDecimal, Double> implements AnnotablePipe<Annotation, BigDecimal, Double> {

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigDecimal::doubleValue;
            super.configure(cfg);
        }
    }

    public static class BigIntegerValue extends AbstractNullSafe<BigDecimal, BigInteger> implements AnnotablePipe<Annotation, BigDecimal, BigInteger> {

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigDecimal::toBigInteger;
            super.configure(cfg);
        }
    }

    public static class BigIntegerExactValue extends AbstractNullSafe<BigDecimal, BigInteger> implements AnnotablePipe<Annotation, BigDecimal, BigInteger> {

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigDecimal::toBigIntegerExact;
            super.configure(cfg);
        }
    }

    public static class ByteValue extends AbstractNullSafe<BigDecimal, Byte> implements AnnotablePipe<Annotation, BigDecimal, Byte> {

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = BigDecimal::byteValue;
            super.configure(cfg);
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeBigDecimal.cellValue, Cell, BigDecimal> {
        private ThrowingFunction<Cell, BigDecimal> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(TypeBigDecimal.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return BigDecimal.valueOf(cell.getNumericCellValue());
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, BigDecimal> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeBigDecimal.cellValue, BigDecimal, ThrowingConsumer<Cell>> {
        private ThrowingFunction<BigDecimal, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(TypeBigDecimal.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = bigDecimal -> bigDecimal == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(bigDecimal.doubleValue());
            else
                this.function = bigDecimal -> cell -> cell.setCellValue(bigDecimal.doubleValue());
        }

        @Override
        public ThrowingFunction<BigDecimal, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
