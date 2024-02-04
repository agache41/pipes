package io.github.agache41.ormpipes.pipes.numeric.typeShort;

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
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.TypeFloat;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.TypeLong;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

public class ShortPipes {
    public static class ShortToString extends AbstractFormat<TypeShort.New, Short> implements AnnotablePipe<TypeShort.New, Short, String> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(TypeShort.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            this.function = this.getIntegerNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    public static class ParseShort extends AbstractParse<TypeShort.New, Short> implements AnnotablePipe<TypeShort.New, String, Short> {

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(TypeShort.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Short::parseShort;
            } else {
                final NumberFormat integerInstance = this.getIntegerNumberFormat(cfg.format(),
                        cfg.languageTag());
                this.function = string -> integerInstance.parse(string)
                                                         .shortValue();
            }
        }
    }

    public static class Value implements AnnotablePipe<TypeShort.value, Object, Short> {

        private Short value;

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(TypeShort.value cfg) {
            this.value = cfg.value();
        }

        @Override
        public ThrowingFunction<Object, Short> function() {
            return object -> this.value;
        }
    }

    public static class IntegerValue extends AbstractNullSafe<Short, Integer> implements AnnotablePipe<Annotation, Short, Integer> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Short::intValue;
            super.configure(cfg);
        }
    }

    public static class UnsignedIntegerValue extends AbstractNullSafe<Short, Integer> implements AnnotablePipe<Annotation, Short, Integer> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Short::toUnsignedInt;
            super.configure(cfg);
        }
    }

    public static class LongValue extends AbstractNullSafe<Short, Long> implements AnnotablePipe<Annotation, Short, Long> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Short::longValue;
            super.configure(cfg);
        }
    }

    public static class UnsignedLongValue extends AbstractNullSafe<Short, Long> implements AnnotablePipe<Annotation, Short, Long> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Short::toUnsignedLong;
            super.configure(cfg);
        }
    }

    public static class DoubleValue extends AbstractNullSafe<Short, Double> implements AnnotablePipe<Annotation, Short, Double> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Short::doubleValue;
            super.configure(cfg);
        }
    }

    public static class FloatValue extends AbstractNullSafe<Short, Float> implements AnnotablePipe<Annotation, Short, Float> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Short::floatValue;
            super.configure(cfg);
        }
    }

    public static class BigIntegerValue extends AbstractNullSafe<Short, BigInteger> implements AnnotablePipe<Annotation, Short, BigInteger> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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

    public static class BigDecimalValue extends AbstractNullSafe<Short, BigDecimal> implements AnnotablePipe<Annotation, Short, BigDecimal> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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

    public static class ByteValue extends AbstractNullSafe<Short, Byte> implements AnnotablePipe<Annotation, Short, Byte> {

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Short::byteValue;
            super.configure(cfg);
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeShort.cellValue, Cell, Short> {
        private ThrowingFunction<Cell, Short> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(TypeShort.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return (short) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, Short> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeShort.cellValue, Short, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Short, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(TypeShort.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = shorter -> shorter == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(shorter);
            else
                this.function = shorter -> cell -> cell.setCellValue(shorter);
        }

        @Override
        public ThrowingFunction<Short, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
