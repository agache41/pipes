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

/**
 * <pre>
 * The type Long pipes.
 * </pre>
 */
public class LongPipes {
    /**
     * <pre>
     * The type Long to string.
     * </pre>
     */
    public static class LongToString extends AbstractFormat<TypeLong.New, Long> implements AnnotablePipe<TypeLong.New, Long, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLong.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            this.function = this.getIntegerNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    /**
     * <pre>
     * The type Parse long.
     * </pre>
     */
    public static class ParseLong extends AbstractParse<TypeLong.New, Long> implements AnnotablePipe<TypeLong.New, String, Long> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
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

    /**
     * <pre>
     * The type Value.
     * </pre>
     */
    public static class Value implements AnnotablePipe<TypeLong.value, Object, Long> {

        private Long value;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLong.value cfg) {
            this.value = cfg.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Long> function() {
            return object -> this.value;
        }
    }

    /**
     * <pre>
     * The type Short value.
     * </pre>
     */
    public static class ShortValue extends AbstractNullSafe<Long, Short> implements AnnotablePipe<Annotation, Long, Short> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Long::shortValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Integer value.
     * </pre>
     */
    public static class IntegerValue extends AbstractNullSafe<Long, Integer> implements AnnotablePipe<Annotation, Long, Integer> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Long::intValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Double value.
     * </pre>
     */
    public static class DoubleValue extends AbstractNullSafe<Long, Double> implements AnnotablePipe<Annotation, Long, Double> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Long::doubleValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Float value.
     * </pre>
     */
    public static class FloatValue extends AbstractNullSafe<Long, Float> implements AnnotablePipe<Annotation, Long, Float> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Long::floatValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big integer value.
     * </pre>
     */
    public static class BigIntegerValue extends AbstractNullSafe<Long, BigInteger> implements AnnotablePipe<Annotation, Long, BigInteger> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = longValue -> BigInteger.valueOf(longValue);
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big decimal value.
     * </pre>
     */
    public static class BigDecimalValue extends AbstractNullSafe<Long, BigDecimal> implements AnnotablePipe<Annotation, Long, BigDecimal> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = longValue -> BigDecimal.valueOf(longValue);
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Byte value.
     * </pre>
     */
    public static class ByteValue extends AbstractNullSafe<Long, Byte> implements AnnotablePipe<Annotation, Long, Byte> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Long::byteValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeLong.cellValue, Cell, Long> {
        private ThrowingFunction<Cell, Long> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLong.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return (long) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, Long> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeLong.cellValue, Long, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Long, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLong.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = longer -> longer == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(longer);
            } else {
                this.function = longer -> cell -> cell.setCellValue(longer);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Long, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
