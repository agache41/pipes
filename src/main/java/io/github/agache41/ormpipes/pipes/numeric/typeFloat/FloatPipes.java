package io.github.agache41.ormpipes.pipes.numeric.typeFloat;

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
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.TypeDouble;
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

public class FloatPipes {
    public static class FloatToString extends AbstractFormat<TypeFloat.New, Float> implements AnnotablePipe<TypeFloat.New, Float, String> {

        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(TypeFloat.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            this.function = this.getNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    public static class ParseFloat extends AbstractParse<TypeFloat.New, Float> implements AnnotablePipe<TypeFloat.New, String, Float> {

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(TypeFloat.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Float::parseFloat;
                return;
            }
            final NumberFormat numberInstance = this.getNumberFormat(cfg.format(),
                    cfg.languageTag());
            this.function = string -> numberInstance.parse(string)
                                                    .floatValue();

        }
    }

    public static class Value implements AnnotablePipe<TypeFloat.value, Object, Float> {

        private Float value;

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(TypeFloat.value cfg) {
            this.value = cfg.value();
        }

        @Override
        public ThrowingFunction<Object, Float> function() {
            return object -> this.value;
        }
    }

    public static class ShortValue extends AbstractNullSafe<Float, Short> implements AnnotablePipe<Annotation, Float, Short> {

        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Float::shortValue;
            super.configure(cfg);
        }
    }

    public static class IntegerValue extends AbstractNullSafe<Float, Integer> implements AnnotablePipe<Annotation, Float, Integer> {

        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Float::intValue;
            super.configure(cfg);
        }
    }

    public static class LongValue extends AbstractNullSafe<Float, Long> implements AnnotablePipe<Annotation, Float, Long> {

        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Float::longValue;
            super.configure(cfg);
        }
    }

    public static class DoubleValue extends AbstractNullSafe<Float, Double> implements AnnotablePipe<Annotation, Float, Double> {

        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Float::doubleValue;
            super.configure(cfg);
        }
    }

    public static class BigIntegerValue extends AbstractNullSafe<Float, BigInteger> implements AnnotablePipe<Annotation, Float, BigInteger> {

        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = floatValue -> BigDecimal.valueOf(floatValue)
                                                    .toBigInteger();
            super.configure(cfg);
        }
    }

    public static class BigDecimalValue extends AbstractNullSafe<Float, BigDecimal> implements AnnotablePipe<Annotation, Float, BigDecimal> {

        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = floatValue -> BigDecimal.valueOf(floatValue);
            super.configure(cfg);
        }
    }

    public static class ByteValue extends AbstractNullSafe<Float, Byte> implements AnnotablePipe<Annotation, Float, Byte> {

        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Float::byteValue;
            super.configure(cfg);
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeFloat.cellValue, Cell, Float> {
        private ThrowingFunction<Cell, Float> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(TypeFloat.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return (float) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, Float> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeFloat.cellValue, Float, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Float, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(TypeFloat.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = floater -> floater == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(floater);
            else
                this.function = floater -> cell -> cell.setCellValue(floater.doubleValue());
        }

        @Override
        public ThrowingFunction<Float, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
