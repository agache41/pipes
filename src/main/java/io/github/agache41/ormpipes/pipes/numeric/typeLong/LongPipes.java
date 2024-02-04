package io.github.agache41.ormpipes.pipes.numeric.typeLong;

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
import io.github.agache41.ormpipes.pipes.numeric.typeShort.TypeShort;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

public class LongPipes {
    public static class LongToString extends AbstractFormat<TypeLong.New, Long> implements AnnotablePipe<TypeLong.New, Long, String> {

        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(TypeLong.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            this.function = this.getIntegerNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    public static class ParseLong extends AbstractParse<TypeLong.New, Long> implements AnnotablePipe<TypeLong.New, String, Long> {

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(TypeLong.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Long::parseLong;
                return;
            }
            final NumberFormat integerInstance = this.getIntegerNumberFormat(cfg.format(), cfg.languageTag());
            this.function = string -> integerInstance.parse(string)
                                                     .longValue();
        }
    }

    public static class Value implements AnnotablePipe<TypeLong.value, Object, Long> {

        private Long value;

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(TypeLong.value cfg) {
            this.value = cfg.value();
        }

        @Override
        public ThrowingFunction<Object, Long> function() {
            return object -> this.value;
        }
    }

    public static class ShortValue extends AbstractNullSafe<Long, Short> implements AnnotablePipe<Annotation, Long, Short> {

        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Long::shortValue;
            super.configure(cfg);
        }
    }

    public static class IntegerValue extends AbstractNullSafe<Long, Integer> implements AnnotablePipe<Annotation, Long, Integer> {

        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Long::intValue;
            super.configure(cfg);
        }
    }

    public static class DoubleValue extends AbstractNullSafe<Long, Double> implements AnnotablePipe<Annotation, Long, Double> {

        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Long::doubleValue;
            super.configure(cfg);
        }
    }

    public static class FloatValue extends AbstractNullSafe<Long, Float> implements AnnotablePipe<Annotation, Long, Float> {

        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Long::floatValue;
            super.configure(cfg);
        }
    }

    public static class BigIntegerValue extends AbstractNullSafe<Long, BigInteger> implements AnnotablePipe<Annotation, Long, BigInteger> {

        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = longValue -> BigInteger.valueOf(longValue);
            super.configure(cfg);
        }
    }

    public static class BigDecimalValue extends AbstractNullSafe<Long, BigDecimal> implements AnnotablePipe<Annotation, Long, BigDecimal> {

        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = longValue -> BigDecimal.valueOf(longValue);
            super.configure(cfg);
        }
    }

    public static class ByteValue extends AbstractNullSafe<Long, Byte> implements AnnotablePipe<Annotation, Long, Byte> {

        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        @Override
        public void configure(Annotation cfg) {
            this.function = Long::byteValue;
            super.configure(cfg);
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeLong.cellValue, Cell, Long> {
        private ThrowingFunction<Cell, Long> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(TypeLong.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return (long) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, Long> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeLong.cellValue, Long, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Long, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        @Override
        public void configure(TypeLong.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = longer -> longer == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(longer);
            else
                this.function = longer -> cell -> cell.setCellValue(longer);
        }

        @Override
        public ThrowingFunction<Long, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
