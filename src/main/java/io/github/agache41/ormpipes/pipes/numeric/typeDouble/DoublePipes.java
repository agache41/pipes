package io.github.agache41.ormpipes.pipes.numeric.typeDouble;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractNullSafe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal.TypeBigDecimal;
import io.github.agache41.ormpipes.pipes.numeric.typeBigInteger.TypeBigInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeByte.TypeByte;
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
import java.text.NumberFormat;

public class DoublePipes {
    public static class DoubleToString extends AbstractFormat<TypeDouble.New, Double> implements AnnotablePipe<TypeDouble.New, Double, String> {

        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(TypeDouble.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            this.function = this.getNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    public static class ParseDouble extends AbstractParse<TypeDouble.New, Double> implements AnnotablePipe<TypeDouble.New, String, Double> {

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(TypeDouble.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Double::parseDouble;
                return;
            }
            final NumberFormat numberInstance = this.getNumberFormat(cfg.format(),
                    cfg.languageTag());
            this.function = string -> numberInstance.parse(string)
                                                    .doubleValue();
        }
    }

    public static class Value implements AnnotablePipe<TypeDouble.value, Object, Double> {

        private Double value;

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(TypeDouble.value cfg) {
            this.value = cfg.value();
        }

        @Override
        public ThrowingFunction<Object, Double> function() {
            return object -> this.value;
        }
    }

    public static class ShortValue extends AbstractNullSafe<Double, Short> implements AnnotablePipe<Annotation, Double, Short> {

        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Double::shortValue;
            super.configure(cfg);
        }
    }

    public static class IntegerValue extends AbstractNullSafe<Double, Integer> implements AnnotablePipe<Annotation, Double, Integer> {

        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Double::intValue;
            super.configure(cfg);
        }
    }

    public static class LongValue extends AbstractNullSafe<Double, Long> implements AnnotablePipe<Annotation, Double, Long> {

        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Double::longValue;
            super.configure(cfg);
        }
    }

    public static class FloatValue extends AbstractNullSafe<Double, Float> implements AnnotablePipe<Annotation, Double, Float> {

        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Double::floatValue;
            super.configure(cfg);
        }
    }

    public static class BigIntegerValue extends AbstractNullSafe<Double, BigInteger> implements AnnotablePipe<Annotation, Double, BigInteger> {

        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = doubleValue -> BigDecimal.valueOf(doubleValue)
                                                     .toBigInteger();
            super.configure(cfg);
        }
    }

    public static class BigDecimalValue extends AbstractNullSafe<Double, BigDecimal> implements AnnotablePipe<Annotation, Double, BigDecimal> {

        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = doubleValue -> BigDecimal.valueOf(doubleValue);
            super.configure(cfg);
        }
    }

    public static class ByteValue extends AbstractNullSafe<Double, Byte> implements AnnotablePipe<Annotation, Double, Byte> {

        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Double::byteValue;
            super.configure(cfg);
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeDouble.cellValue, Cell, Double> {
        private ThrowingFunction<Cell, Double> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(TypeDouble.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, Double> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeDouble.cellValue, Double, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Double, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(TypeDouble.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = doubler -> doubler == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(doubler);
            else
                this.function = doubler -> cell -> cell.setCellValue(doubler);
        }

        @Override
        public ThrowingFunction<Double, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
